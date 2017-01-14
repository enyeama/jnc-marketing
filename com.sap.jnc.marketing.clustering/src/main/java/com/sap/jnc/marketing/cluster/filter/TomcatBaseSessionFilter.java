package com.sap.jnc.marketing.cluster.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

import com.sap.jnc.marketing.cluster.session.manager.RedisSessionManager;
import com.sap.jnc.marketing.cluster.util.Expression;

public class TomcatBaseSessionFilter extends BaseSessionFilter{

	/* (non-Javadoc)    
	 * @see com.xuwei.session.filter.BaseSessionFilter#setSessionListener(javax.servlet.FilterConfig, com.xuwei.session.manager.RedisSessionManager)    
	 */
	@Override
	protected void setSessionListener(FilterConfig filterConfig,
			RedisSessionManager sessionManager) {
		ServletContext servletContext = filterConfig.getServletContext();
		//在tomcat中ServletContext，实现类为ApplicationContextFacade
		//该类对ApplicationContext进行了装饰，相应字段名称为context
		Object appContext = Expression.eval(servletContext, ".context");
		//ApplicationContext类中StandardContext属性，相应字段为context
		Object standardContext = Expression.eval(appContext, ".context");
		ArrayList<HttpSessionListener> sessionListeners = new ArrayList<HttpSessionListener>();
		
		ArrayList<HttpSessionAttributeListener> sessionAttributeListeners = new ArrayList<HttpSessionAttributeListener>();
		
		setListener(standardContext, sessionListeners, sessionAttributeListeners);
		
		if(sessionAttributeListeners.size()>0){
			sessionManager.setSessionAttributeListeners(sessionAttributeListeners.toArray(new HttpSessionAttributeListener[sessionAttributeListeners.size()]));
		}
		if(sessionListeners.size()>0){
			sessionManager.setSessionListeners(sessionListeners.toArray(new HttpSessionListener[sessionListeners.size()]));
		}
	}
	
	private void setListener(Object standardContext,
			List<HttpSessionListener> sessionListeners,
			List<HttpSessionAttributeListener> sessionAttributeListener){
		Object[] applicationEventListeners = (Object[])Expression.eval(standardContext, ".getApplicationEventListeners()");
		Object[] applicationLifecycleListeners = (Object[]) Expression.eval(standardContext, ".getApplicationLifecycleListeners()");
		addToList(applicationEventListeners, sessionListeners, sessionAttributeListener);
		addToList(applicationLifecycleListeners, sessionListeners, sessionAttributeListener);
	}
	
	private void addToList(Object[] objects,
			List<HttpSessionListener> sessionListeners,
			List<HttpSessionAttributeListener> sessionAttributeListener){
		if(objects==null||objects.length==0){
			return;
		}
		for(Object object:objects){
			if(object instanceof HttpSessionListener){
				sessionListeners.add((HttpSessionListener)object);
			}
			if(object instanceof HttpSessionAttributeListener){
				sessionAttributeListener.add((HttpSessionAttributeListener)object);
			}
		}
	}

}
