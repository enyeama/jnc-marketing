package com.sap.jnc.marketing.persistence.config;

import com.sap.jnc.marketing.infrastructure.ExcludeForTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Test configuration for unit tests. This explicitly excludes classes annotated with {@link ExcludeForTest}.
 *
 * @author I071053 Diouf Du
 */
@Configuration
@PropertySource("classpath:db-h2.properties")
public class PersistenceH2TestConfig extends PersistenceResourceBasesdConfig {
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		entityManagerFactoryBean.setPackagesToScan(PersistenceResourceBasesdConfig.ENTITY_PACKAGES);
		return entityManagerFactoryBean;
	}
}
