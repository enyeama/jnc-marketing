package com.sap.jnc.marketing.service.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author I071053 Diouf Du
 */
@Configuration
@ComponentScan(basePackages = ServiceConfig.PACKAGE_NAMESPACE)
public class ServiceConfig {

	public static final String PACKAGE_NAMESPACE = "com.sap.jnc.marketing.service";

	@Bean
	public Mapper mapper() {
		return new DozerBeanMapper();
	}

}
