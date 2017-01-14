package com.sap.jnc.marketing.api.job.web.config;

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

import com.sap.jnc.marketing.api.job.web.interceptor.CustomizedHandlerInterceptor;

@Configuration
@ComponentScan(basePackages = WebConfig.PACKAGE_NAMESPACE)
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	public static final String PACKAGE_NAMESPACE = "com.sap.jnc.marketing.api.admin";

	private static final int MAX_UPLOAD_SIZE = 4 * 1024 * 1024;

	@Autowired(required = false)
	protected List<CustomizedHandlerInterceptor> customizedHandlerInterceptors;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/job/*").allowedOrigins("*");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (this.customizedHandlerInterceptors != null) {
			for (final CustomizedHandlerInterceptor customizedHandlerInterceptor : this.customizedHandlerInterceptors) {
				registry.addInterceptor(customizedHandlerInterceptor);
			}
		}
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		final CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
		return commonsMultipartResolver;
	}
}
