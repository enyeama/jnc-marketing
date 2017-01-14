package com.sap.jnc.marketing.cluster.session.manager;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class DefaultSessionEventManager implements SessionEventManager{

	

	/**
	 * 
	 */
	private HttpSessionListener[] sessionListeners;
	
	/**
	 * 
	 */
	private HttpSessionAttributeListener[] sessionAttributeListeners;
	
	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#sessionCreatedEvent(javax.servlet.http.HttpSession)    
	 */
	@Override
	public void sessionCreatedEvent(HttpSession session) {
		if (sessionListeners != null) {
			HttpSessionEvent e = new HttpSessionEvent(session);
			for (HttpSessionListener l : this.sessionListeners) {
				if (l == null)
					continue;
				l.sessionCreated(e);
			}
		}
	}

	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#sessionDestroyedEvent(javax.servlet.http.HttpSession)    
	 */
	@Override
	public void sessionDestroyedEvent(HttpSession paramHttpSession) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#unbindAttribute(javax.servlet.http.HttpSession, java.lang.String, java.lang.Object)    
	 */
	@Override
	public void unbindAttribute(HttpSession paramHttpSession,
			String paramString, Object paramObject) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#bindAttribute(javax.servlet.http.HttpSession, java.lang.String, java.lang.Object)    
	 */
	@Override
	public void bindAttribute(HttpSession paramHttpSession, String paramString,
			Object paramObject) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#setAttribute(javax.servlet.http.HttpSession, java.lang.String, java.lang.Object)    
	 */
	@Override
	public void setAttribute(HttpSession paramHttpSession, String paramString,
			Object paramObject) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#removeAttribute(javax.servlet.http.HttpSession, java.lang.String, java.lang.Object)    
	 */
	@Override
	public void removeAttribute(HttpSession paramHttpSession,
			String paramString, Object paramObject) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)    
	 * @see com.xuwei.session.manager.SessionEventManager#replaceAttribute(javax.servlet.http.HttpSession, java.lang.String, java.lang.Object, java.lang.Object)    
	 */
	@Override
	public void replaceAttribute(HttpSession paramHttpSession,
			String paramString, Object paramObject1, Object paramObject2) {
		// TODO Auto-generated method stub
		
	}

	
	public HttpSessionListener[] getSessionListeners() {
		return sessionListeners;
	}

	/**    
	 * @param sessionListeners the sessionListeners to set    
	 */
	public void setSessionListeners(HttpSessionListener[] sessionListeners) {
		this.sessionListeners = sessionListeners;
	}

	
	public HttpSessionAttributeListener[] getSessionAttributeListeners() {
		return sessionAttributeListeners;
	}

	/**    
	 * @param sessionAttributeListeners the sessionAttributeListeners to set    
	 */
	public void setSessionAttributeListeners(
			HttpSessionAttributeListener[] sessionAttributeListeners) {
		this.sessionAttributeListeners = sessionAttributeListeners;
	}

	
}
