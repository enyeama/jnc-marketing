package com.sap.jnc.marketing.api.bonus.web;

import com.sap.jnc.marketing.api.bonus.web.config.SecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

/**
 * @author I071053 Diouf Du
 */
@Configuration
@ComponentScan(basePackages = ApplicationBoot.PACKAGE_NAMESPACE)
public class ApplicationBoot extends AbstractAnnotationConfigDispatcherServletInitializer {

	public static final String PACKAGE_NAMESPACE = "com.sap.jnc.marketing";

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { ApplicationBoot.class, SecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/*" };
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.addListener(RequestContextListener.class);
		super.onStartup(servletContext);
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setInitParameter("dispatchOptionsRequest", "true");
	}
}