package com.sap.jnc.marketing.api.integration.web.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sap.jnc.marketing.api.integration.web.interceptor.CustomizedHandlerInterceptor;
import com.sap.jnc.marketing.api.integration.web.interceptor.JfIntegrationAuthorizationInterceptor;

@Configuration
@ComponentScan(basePackages = WebConfig.PACKAGE_NAMESPACE)
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	public static final String PACKAGE_NAMESPACE = "com.sap.jnc.marketing.api.integration";

	private static final int MAX_UPLOAD_SIZE = 4 * 1024 * 1024;

	@Autowired(required = false)
	protected List<CustomizedHandlerInterceptor> customizedHandlerInterceptors;
	@Autowired(required = true)
	private JfIntegrationAuthorizationInterceptor jfIntegrationAuthorizationInterceptor;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/integration/*").allowedOrigins("*");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (this.customizedHandlerInterceptors != null) {
			for (CustomizedHandlerInterceptor customizedHandlerInterceptor : this.customizedHandlerInterceptors) {
				registry.addInterceptor(customizedHandlerInterceptor);
			}
		}
		if (this.jfIntegrationAuthorizationInterceptor != null) {
			registry.addInterceptor(jfIntegrationAuthorizationInterceptor);
		}
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
		return commonsMultipartResolver;
	}
}
