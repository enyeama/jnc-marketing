package com.sap.jnc.marketing.persistence.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.sap.jnc.marketing.infrastructure.ExcludeForTest;

/**
 * Test configuration for unit tests. This explicitly excludes classes annotated with {@link ExcludeForTest}.
 *
 * @author I071053 Diouf Du
 */
@Configuration
@PropertySource("classpath:db-hana.properties")
public class PersistenceHanaTestConfig extends PersistenceResourceBasesdConfig {

}
