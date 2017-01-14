package com.sap.jnc.marketing.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.sap.jnc.marketing.infrastructure.ExcludeForTest;

@Configuration
@ExcludeForTest
public class RestConfig {

	private final static Logger LOGGER = LoggerFactory.getLogger(RestConfig.class);

	private RestTemplate restTemplate;

	@Bean
	public synchronized RestTemplate restTemplate() {
		if (this.restTemplate == null) {
			try {
				this.restTemplate = new RestTemplate();
			} catch (final Exception ex) {
				LOGGER.error("error occurred while initializing RestTemplate", ex);
			}
		}

		return this.restTemplate;
	}

}
