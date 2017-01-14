package com.sap.jnc.marketing.api.wechat.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

public class SessionListener implements ServletContextListener, HttpSessionAttributeListener, HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		// Date now = new Date();
		// DateTime dateTime = new DateTime(now.getTime());
		// int currentMonth = dateTime.getMonthOfYear();
		// int currentYear = dateTime.getYear();
		//
		// VisitorCount visitorCount = visitorCountService.findVisitorCountByMonthAndYer(currentMonth, currentYear);
		// if (visitorCount == null) {
		// visitorCount = new VisitorCount();
		// visitorCount.setCount(1);
		// visitorCount.setMonth(currentMonth);
		// visitorCount.setYear(currentYear);
		// visitorCount.setCreatedAt(now);
		// visitorCount.setUpdatedAt(now);
		//
		// visitorCountService.create(visitorCount);
		// } else {
		// visitorCount.setCount(visitorCount.getCount() + 1);
		// visitorCount.setMonth(currentMonth);
		// visitorCount.setYear(currentYear);
		// visitorCount.setCreatedAt(now);
		// visitorCount.setUpdatedAt(now);
		//
		// visitorCountService.update(visitorCount);
		// }
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {

	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}