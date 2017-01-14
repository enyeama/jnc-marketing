
package com.sap.jnc.marketing.cluster.session.manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

public interface RedisSessionManager {
	
	void init();

	HttpSession getOrCreateSession(String sessionId, HttpServletRequest request, HttpServletResponse response,
								   boolean create);
	
	void setSessionTimeoutSeconds(int sessionTimeoutSeconds);
	
	HttpSessionListener[] getSessionListeners() ;

	void setSessionListeners(HttpSessionListener[] sessionListeners) ;


	HttpSessionAttributeListener[] getSessionAttributeListeners() ;

	void setSessionAttributeListeners(HttpSessionAttributeListener[] sessionAttributeListeners);
}
