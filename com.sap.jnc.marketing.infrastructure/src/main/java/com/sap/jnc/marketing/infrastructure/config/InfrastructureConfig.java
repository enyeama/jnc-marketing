package com.sap.jnc.marketing.infrastructure.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;

@Configuration
@ComponentScan(basePackages = InfrastructureConfig.PACKAGE_NAMESPACE)
@EnableCaching
public class InfrastructureConfig {

	public static final String PACKAGE_NAMESPACE = "com.sap.jnc.marketing.infrastructure";

	@Bean
	public XStream xStream() {
		return new XStream();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public CacheManager inMemoryCacheManager() {
		return new ConcurrentMapCacheManager();
	}
}
