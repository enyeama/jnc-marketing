package com.sap.jnc.marketing.cluster.request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sap.jnc.marketing.cluster.session.manager.RedisSessionManager;

public class RedisServletRequest extends HttpServletRequestWrapper{

	/**
	 * 
	 */
	private HttpSession httpSession;
	
	/**
	 * session管理器
	 */
	private RedisSessionManager sessionManager;
	
	/**
	 * 
	 */
	private final HttpServletResponse response;
	
	/**
	 * 
	 */
	private String sessionId;
	
	/**
	 * session的cookie名称
	 */
	private String sessionCookieName = "JSESSIONID";
	

	public RedisServletRequest(HttpServletRequest request,
			RedisSessionManager sessionManager, HttpServletResponse response) {
		super(request);
		this.sessionManager = sessionManager;
		this.response = response;
		this.sessionId = findSessionID(request);
	}

    private String findSessionID(HttpServletRequest request) {
        String sessionId = findSessionFormCookie(request);
        if (sessionId == null) {
            sessionId = findSessionFormURL(request);
        }
        return sessionId;
    }


	/* (non-Javadoc)    
	 * @see javax.servlet.http.HttpServletRequestWrapper#getSession(boolean)    
	 */
	@Override
	public HttpSession getSession(boolean create) {
		if(httpSession!=null){
			return httpSession;
		}
		httpSession = sessionManager.getOrCreateSession(sessionId, this, response, create);
		return httpSession;
	}

	/* (non-Javadoc)    
	 * @see javax.servlet.http.HttpServletRequestWrapper#getSession()    
	 */
	@Override
	public HttpSession getSession() {
		return getSession(true);
	}
	

	private String findSessionFormCookie(HttpServletRequest request){
		String rv = null;

	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	      for (Cookie cookie : cookies) {
	        if (this.sessionCookieName.equals(cookie.getName())) {
	          rv = cookie.getValue();
	        }
	      }
	    }
	    return rv;
	}
	
	private String findSessionFormURL(HttpServletRequest request){
		if(request.isRequestedSessionIdFromURL()){
			return request.getRequestedSessionId();
		}
		String rv = null;
		
		return rv;
	}

}
