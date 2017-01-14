package com.sap.jnc.marketing.api.admin.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author I071053 Diouf Du
 */
@Configuration
@ComponentScan(basePackages = WebConfig.PACKAGE_NAMESPACE)
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	public static final String PACKAGE_NAMESPACE = "com.sap.jnc.marketing.api.admin";

	private static final int MAX_UPLOAD_SIZE = 4 * 1024 * 1024;

	@Autowired(required = false)
	private ApiOriginHandlerInterceptor apiOriginHandlerInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (this.apiOriginHandlerInterceptor != null) {
			registry.addInterceptor(this.apiOriginHandlerInterceptor);
		}
		super.addInterceptors(registry);
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		final CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
		return commonsMultipartResolver;
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource getMessageSource() {
		final ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
		resource.setBasenames("classpath:i18n/application-messages");
		resource.setDefaultEncoding("UTF-8");
		return resource;
	}
}
