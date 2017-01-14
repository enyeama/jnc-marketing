package com.sap.jnc.marketing.api.wechat.web.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.sap.jnc.marketing.api.wechat.web.interceptor.CustomizedHandlerInterceptor;

@Configuration
@ComponentScan(basePackages = WebConfig.PACKAGE_NAMESPACE)
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	public static final String PACKAGE_NAMESPACE = "com.sap.jnc.marketing.api.wechat";

	private static final int MAX_UPLOAD_SIZE = 4 * 1024 * 1024;

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Autowired(required = false)
	protected List<CustomizedHandlerInterceptor> customizedHandlerInterceptors;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*");
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

	@Bean
	public ViewResolver viewResolver() {
		final ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(this.templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}

	@Bean
	public TemplateEngine templateEngine() {
		final SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(this.templateResolver());
		return engine;
	}

	private ITemplateResolver templateResolver() {
		final SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(this.applicationContext);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setSuffix(".html");
		resolver.setCacheable(false);
		return resolver;
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource getMessageSource() {
		final ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
		resource.setBasenames("classpath:i18n/application-messages");
		resource.setDefaultEncoding("UTF-8");
		return resource;
	}
}
