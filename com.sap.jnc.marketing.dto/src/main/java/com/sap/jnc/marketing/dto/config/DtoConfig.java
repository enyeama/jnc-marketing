package com.sap.jnc.marketing.dto.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = DtoConfig.PACKAGE_NAMESPACE)
public class DtoConfig {

	public static final String PACKAGE_NAMESPACE = "com.sap.jnc.marketing.dto";

}
