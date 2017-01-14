package com.sap.jnc.marketing.cluster.session;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionContext;

import com.sap.jnc.marketing.cluster.redis.RedisAccess;
import com.sap.jnc.marketing.cluster.redis.RedisAccessFactory;
import com.sap.jnc.marketing.cluster.redis.SerializeUtils;
import com.sap.jnc.marketing.cluster.request.RedisServletRequest;
import com.sap.jnc.marketing.cluster.session.manager.RedisSessionManager;

public class RedisSessionInternalImpl implements RedisSessionInternal {

	/**
	 * 保存Redis Servlet Request
	 */
	private final RedisServletRequest request;

	/**
	 * 是否是新建的session
	 */
	private final boolean isNew;
	/**
	 * session管理器
	 */
	private final RedisSessionManager sessionManager;

	/**
	 * SessionId
	 */
	private String sessionId;

	/**
	 *
	 */
	private BaseSessionData baseSessionData;

	/**
	 *
	 */
	private Set<String> attributeNameSet;

	/**
	 *
	 */
	private final RedisAccess redisUtil = RedisAccessFactory.getRedisAccessInstance();

	/**
	 * redis session属性名称集合对应后缀名
	 */
	private final String SESSION_ATTR_NAMES = "ATTR_NAMES";
	/**
	 * redis session基础属性名称对应后缀名
	 */
	private final String SESSION_BASEDATA = "BASESESSIONDATA";

	private int sessionTimeOut;

	public RedisSessionInternalImpl(String sessionId, RedisServletRequest request, boolean isNew, RedisSessionManager sessionManager) {
		this.sessionId = sessionId;
		this.request = request;
		this.isNew = isNew;
		this.sessionManager = sessionManager;
	}

	@Override
	public boolean isValid() {
		this.baseSessionData = (BaseSessionData) this.redisUtil.hget(this.sessionId.getBytes(), this.SESSION_BASEDATA.getBytes());
		final long currentTime = System.currentTimeMillis();
		if (currentTime > ((this.baseSessionData.getMaxInactiveIntervalSeconds() * 1000) + this.baseSessionData.getLastAccessTimeMillis())) {
			// session过时
			this.invalidate();
			return true;
		}
		return false;
	}

	@Override
	public long getCreationTime() {
		return this.baseSessionData.getCreationTimeMillis();
	}

	@Override
	public String getId() {
		return this.sessionId;
	}

	@Override
	public long getLastAccessedTime() {
		return this.baseSessionData.getLastAccessTimeMillis();
	}

	@Override
	public ServletContext getServletContext() {
		return null;
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		this.baseSessionData.setMaxInactiveIntervalSeconds(interval);
	}

	@Override
	public int getMaxInactiveInterval() {
		return this.baseSessionData.getMaxInactiveIntervalSeconds();
	}

	@Override
	public Object getAttribute(String name) {
		return this.redisUtil.hget(this.sessionId.getBytes(), name.getBytes(), this.sessionTimeOut);
	}

	@Override
	public Object getValue(String name) {
		return this.getAttribute(name);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getAttributeNames() {
		return Collections.enumeration(this.attributeNameSet);
	}

	@Override
	public String[] getValueNames() {
		return (String[]) this.attributeNameSet.toArray();
	}

	@Override
	public void setAttribute(String name, Object value) {
		if (value == null) {
			this.redisUtil.hdel(this.sessionId.getBytes(), name.getBytes());
			// 同步更新redis中的attrNames属性
			this.attributeNameSet.remove(name);
			this.redisUtil.hset(this.sessionId.getBytes(), this.SESSION_ATTR_NAMES.getBytes(), SerializeUtils.serializeObject(this.attributeNameSet),
				this.sessionTimeOut);
			return;
		}
		final byte[] bytes = SerializeUtils.serializeObject(value);
		this.redisUtil.hset(this.sessionId.getBytes(), name.getBytes(), bytes, this.sessionTimeOut);
	}

	@Override
	public void putValue(String name, Object value) {
		this.setAttribute(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		this.redisUtil.hdel(this.sessionId.getBytes(), name.getBytes());
		// 同步更新redis中的attrNames属性
		this.attributeNameSet.remove(name);
		this.redisUtil.hset(this.sessionId.getBytes(), this.SESSION_ATTR_NAMES.getBytes(), SerializeUtils.serializeObject(this.attributeNameSet),
			this.sessionTimeOut);
	}

	@Override
	public void removeValue(String name) {
		this.removeAttribute(name);
	}

	@Override
	public void invalidate() {
		this.redisUtil.del(this.sessionId.getBytes());
	}

	@Override
	public boolean isNew() {
		return this.isNew;
	}


	@Override
	public BaseSessionData getBaseSessionData() {
		return this.baseSessionData;
	}

	@Override
	public Set<String> getAttributeNameSet() {
		return this.attributeNameSet;
	}


	/**
	 * @param attributeNameSet
	 *            the attributeNameSet to set
	 */
	public void setAttributeNameSet(Set<String> attributeNameSet) {
		this.attributeNameSet = attributeNameSet;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @param baseSessionData
	 *            the baseSessionData to set
	 */
	public void setBaseSessionData(BaseSessionData baseSessionData) {
		this.sessionTimeOut = baseSessionData.getMaxInactiveIntervalSeconds();
		this.baseSessionData = baseSessionData;
	}

	@Override
	public void finishRequest() {

	}

	@Override
	public void setAttributeQuiet(String paramString, Object paramObject) {

	}

	@Override
	public void invalidateQuiet() {

	}

	@Override
	public HttpSessionContext getSessionContext() {
		return null;
	}

	@Override
	public void setTransientAttribute(String paramString, Object paramObject) {

	}

	@Override
	public void removeTransientAttribute(String paramString) {

	}

	@Override
	public Object getTransientAttribute(String paramString) {
		return null;
	}

	@Override
	public Set<String> getTransientAttributeNames() {
		return null;
	}
}
