package com.sap.jnc.marketing.cluster.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = ClusterConfig.PACKAGE_NAMESPACE)
public class ClusterConfig {

	public static final String PACKAGE_NAMESPACE = "com.sap.jnc.marketing.cluster";

}
