package com.sap.jnc.marketing.persistence.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sap.jnc.marketing.infrastructure.ExcludeForTest;
import com.sap.jnc.marketing.infrastructure.config.InfrastructureConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Test configuration for unit tests. This explicitly excludes classes annotated with {@link ExcludeForTest}.
 *
 * @author I071053 Diouf Du
 */
@EnableJpaRepositories(basePackages = "com.sap.jnc.marketing.persistence.repository")
@ComponentScan(basePackages = PersistenceConfig.PACKAGE_NAMESPACE, excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {
	ExcludeForTest.class, Configuration.class }))
@Import({ InfrastructureConfig.class })
@EnableTransactionManagement
public class PersistenceResourceBasesdConfig {
	protected static final String PROPERTY_NAME_DATABASE_DRIVER = "spring.datasource.driverClassName";
	protected static final String PROPERTY_NAME_DATABASE_PASSWORD = "spring.datasource.password";
	protected static final String PROPERTY_NAME_DATABASE_URL = "spring.datasource.url";
	protected static final String PROPERTY_NAME_DATABASE_USERNAME = "spring.datasource.username";

	public static final String[] ENTITY_PACKAGES = { "com.sap.jnc.marketing.persistence.model" };

	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		final HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setDriverClassName(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSourceConfig.setJdbcUrl(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSourceConfig.setUsername(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSourceConfig.setPassword(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		return new HikariDataSource(dataSourceConfig);

	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		entityManagerFactoryBean.setPackagesToScan(ENTITY_PACKAGES);
		return entityManagerFactoryBean;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() throws SQLException {
		return new JdbcTemplate(this.dataSource());
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() throws SQLException {
		return new NamedParameterJdbcTemplate(this.dataSource());
	}
}
