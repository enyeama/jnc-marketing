package com.sap.jnc.marketing.cluster.session.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;

import com.sap.jnc.marketing.cluster.config.SessionConfig;
import com.sap.jnc.marketing.cluster.redis.RedisAccess;
import com.sap.jnc.marketing.cluster.redis.RedisAccessFactory;
import com.sap.jnc.marketing.cluster.redis.SerializeUtils;
import com.sap.jnc.marketing.cluster.request.RedisServletRequest;
import com.sap.jnc.marketing.cluster.session.BaseSessionData;
import com.sap.jnc.marketing.cluster.session.RedisSessionInternal;
import com.sap.jnc.marketing.cluster.session.RedisSessionInternalImpl;

public class DefaultSessionManager implements RedisSessionManager {

	/**
	 * session过期时间
	 */
	private int sessionTimeoutSeconds;

	/**
	 *
	 */
	private String cookieDomain;

	/**
	 *
	 */
	private String cookiePath;

	/**
	 *
	 */
	private int cookieMaxAge;


	/**
	 * redis session属性名称集合对应后缀名
	 */
	private final String SESSION_ATTR_NAMES = "ATTR_NAMES";
	/**
	 * redis session基础属性名称对应后缀名
	 */
	private final String SESSION_BASEDATA = "BASESESSIONDATA";

	/**
	 *
	 */
	private HttpSessionListener[] sessionListeners;

	/**
	 *
	 */
	private HttpSessionAttributeListener[] sessionAttributeListeners;

	/**
	 * seesion监听管理器
	 */
	private SessionEventManager eventManager;

	private final RedisAccess redisAccess = RedisAccessFactory.getRedisAccessInstance();

	private final WebApplicationContext webApplicationContext;

	public DefaultSessionManager(WebApplicationContext applicationContext) {
		this.webApplicationContext = applicationContext;
	}

	private RedisSessionInternal createNewSession(HttpServletRequest request,
		HttpServletResponse response) {
		if (!(request instanceof RedisServletRequest)) {
			throw new IllegalStateException("serlvet类型不匹配");
		}
		final RedisServletRequest redisServlet = (RedisServletRequest) request;
		final BaseSessionData base = new BaseSessionData(System.currentTimeMillis(),
			this.sessionTimeoutSeconds);
		final Set<String> attrNames = new HashSet<String>();
		return this.createNewSession(redisServlet, response, base, attrNames);
	}

	private RedisSessionInternal createNewSession(RedisServletRequest request,
		HttpServletResponse response, BaseSessionData base,
		Set<String> attrNames) {
		final String sessionId = this.createSessionId();
		final RedisSessionInternalImpl session = new RedisSessionInternalImpl(sessionId, request, true, this);
		session.setAttributeNameSet(attrNames);
		session.setBaseSessionData(base);
		final Map<byte[], byte[]> data = new HashMap<byte[], byte[]>();
		data.put(this.SESSION_ATTR_NAMES.getBytes(), SerializeUtils.serializeObject(attrNames));
		data.put(this.SESSION_BASEDATA.getBytes(), SerializeUtils.serializeObject(base));
		this.redisAccess.hmset(sessionId.getBytes(), data, this.sessionTimeoutSeconds);
		//写入cookie
		Cookie cookie = new Cookie("JSESSIONID", sessionId);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
		cookie = new Cookie("JSESSIONID", sessionId);
		cookie.setDomain(this.cookieDomain);
		cookie.setPath(this.cookiePath);
		cookie.setMaxAge(this.cookieMaxAge);
		response.addCookie(cookie);
		//触发session创建事件
		this.eventManager.sessionCreatedEvent(session);
		return session;
	}

	/* (non-Javadoc)
	 * @see com.xuwei.session.manager.RedisSessionManager#getOrCreateSession(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HttpSession getOrCreateSession(String sessionId,
		HttpServletRequest request, HttpServletResponse response,
		boolean create) {
		if (!(request instanceof RedisServletRequest)) {
			throw new IllegalStateException("serlvet类型不匹配");
		}
		final RedisServletRequest redisRequest = (RedisServletRequest) request;
		//sessionid为空，初始化session
		if (sessionId == null) {
			return create ? this.createNewSession(request, response) : null;
		}
		//sessionid不为空，判断redis中是否存在该session的相关信息
		final Boolean exist = this.redisAccess.exists(sessionId.getBytes());
		if (exist) {
			final List<Object> sessionData = this.redisAccess.hmget(sessionId.getBytes(),
				this.sessionTimeoutSeconds, this.SESSION_ATTR_NAMES.getBytes(),
				this.SESSION_BASEDATA.getBytes());
			final Set<String> attrNames = (Set<String>) sessionData.get(0);
			final BaseSessionData baseSessionData = (BaseSessionData) sessionData.get(1);
			baseSessionData.setLastAccessTimeMillis(System.currentTimeMillis());
			//初始化session
			final RedisSessionInternalImpl session = new RedisSessionInternalImpl(sessionId, redisRequest, false, this);
			session.setAttributeNameSet(attrNames);
			session.setSessionId(sessionId);
			session.setBaseSessionData(baseSessionData);
			return session;
		}
		//sessionid not existed in redis
		return create ? this.createNewSession(request, response) : null;
	}

	public int getSessionTimeoutSeconds() {
		return this.sessionTimeoutSeconds;
	}

	@Override
	public void setSessionTimeoutSeconds(int sessionTimeoutSeconds) {
		this.sessionTimeoutSeconds = sessionTimeoutSeconds;
	}

	private String createSessionId() {
		return UUID.randomUUID().toString() + Math.random();
	}


	@Override
	public HttpSessionListener[] getSessionListeners() {
		return this.sessionListeners;
	}

	/**
	 * @param sessionListeners the sessionListeners to set
	 */
	@Override
	public void setSessionListeners(HttpSessionListener[] sessionListeners) {
		this.sessionListeners = sessionListeners;
	}

	@Override
	public HttpSessionAttributeListener[] getSessionAttributeListeners() {
		return this.sessionAttributeListeners;
	}

	/**
	 * @param sessionAttributeListeners the sessionAttributeListeners to set
	 */
	@Override
	public void setSessionAttributeListeners(
		HttpSessionAttributeListener[] sessionAttributeListeners) {
		this.sessionAttributeListeners = sessionAttributeListeners;
	}

	/* (non-Javadoc)
	 * @see com.xuwei.session.manager.RedisSessionManager#init()
	 */
	@Override
	public void init() {
		this.eventManager = new DefaultSessionEventManager();
		this.eventManager.setSessionAttributeListeners(this.sessionAttributeListeners);
		this.eventManager.setSessionListeners(this.sessionListeners);
		this.sessionTimeoutSeconds = Integer.valueOf(SessionConfig.getValue("sessiontimeout"));
		this.cookieDomain = SessionConfig.getValue("cookie.domain");
		this.cookiePath = SessionConfig.getValue("cookie.path");
		this.cookieMaxAge = Integer.valueOf(SessionConfig.getValue("cookie.maxAge"));
	}
}
