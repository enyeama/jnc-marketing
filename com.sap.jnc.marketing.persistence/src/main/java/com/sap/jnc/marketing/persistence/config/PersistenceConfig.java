package com.sap.jnc.marketing.persistence.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.sap.jnc.marketing.persistence.exception.CommonPersistenceException;

@Configuration
@ComponentScan(basePackages = PersistenceConfig.PACKAGE_NAMESPACE)
@EnableTransactionManagement
@EnableLoadTimeWeaving
@EnableJpaRepositories("com.sap.jnc.marketing.persistence.repository")
@PropertySource(value = { "classpath:META-INF/db.properties" })
public class PersistenceConfig implements AutoCloseable {

	public static final String PACKAGE_NAMESPACE = "com.sap.jnc.marketing.persistence";

	// The persistence schema name would determined by deployment environment
	// Local : [JNC]
	// Development : [JNC]
	// Test : [JNC]
	// Productive : [JNC]
	public static final String ENTITY_SCHEMA_NAME = "MMP";

	private static final String[] ENTITY_PACKAGES = { "com.sap.jnc.marketing.persistence.model" };

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "spring.datasource.driverClassName";
	private static final String PROPERTY_NAME_DATABASE_URL = "spring.datasource.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "spring.datasource.username";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "spring.datasource.password";

	private static final String PROPERTY_NAME_ECLIPSELINK_LOG = "eclipselink.logging.level.sql";
	private static final String PROPERTY_NAME_ECLIPSELINK_BATCH = "eclipselink.jdbc.batch-writing";
	private static final String PROPERTY_NAME_ECLIPSELINK_DDL = "eclipselink.ddl-generation";
	private static final String PROPERTY_NAME_ECLIPSELINK_TARGET_DATABASE = "eclipselink.target-database";
	private static final String PROPERTY_NAME_ECLIPSELINK_APPLICATION_LOCATION = "eclipselink.application-location";
	private static final String PROPERTY_NAME_ECLIPSELINK_UPPERCASE_COLUMN_NAMES = "eclipselink.jpa.uppercase-column-names";

	private static final String PROPERTY_ECLIPSELINK_WEAVING = "eclipselink.weaving";
	private static final String PROPERTY_ECLIPSELINK_SHARED_CACHE_MODE = "shared-cache-mode";

	@Autowired
	private Environment environment;

	private DataSource preferentialDataSource;

	protected LocalContainerEntityManagerFactoryBean entityManagerFactory;

	private EntityManager entityManager;

	private PlatformTransactionManager annotationDrivenTransactionManager;

	@Bean
	public synchronized LocalContainerEntityManagerFactoryBean entityManagerFactory() throws CommonPersistenceException {
		if (this.entityManagerFactory == null) {
			try {
				final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
				factory.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
				factory.setDataSource(this.preferentialDataSource());
				final Properties jpaProperties = new Properties();
				jpaProperties.put(PROPERTY_NAME_ECLIPSELINK_LOG, this.environment.getRequiredProperty(PROPERTY_NAME_ECLIPSELINK_LOG));
				jpaProperties.put(PROPERTY_NAME_ECLIPSELINK_BATCH, this.environment.getRequiredProperty(PROPERTY_NAME_ECLIPSELINK_BATCH));
				jpaProperties.put(PROPERTY_NAME_ECLIPSELINK_DDL, this.environment.getRequiredProperty(PROPERTY_NAME_ECLIPSELINK_DDL));
				jpaProperties.put(PROPERTY_NAME_ECLIPSELINK_TARGET_DATABASE, this.environment.getProperty(PROPERTY_NAME_ECLIPSELINK_TARGET_DATABASE,
					"Auto"));
				final String applicationLocation = this.environment.getProperty(PROPERTY_NAME_ECLIPSELINK_APPLICATION_LOCATION);
				if (StringUtils.isNotBlank(applicationLocation)) {
					jpaProperties.put(PROPERTY_NAME_ECLIPSELINK_APPLICATION_LOCATION, applicationLocation);
				}
				jpaProperties.put(PROPERTY_NAME_ECLIPSELINK_UPPERCASE_COLUMN_NAMES, this.environment.getProperty(
					PROPERTY_NAME_ECLIPSELINK_UPPERCASE_COLUMN_NAMES));
				jpaProperties.put(PROPERTY_ECLIPSELINK_WEAVING, "false");
				jpaProperties.put(PROPERTY_ECLIPSELINK_SHARED_CACHE_MODE, "NONE");
				factory.setJpaProperties(jpaProperties);
				factory.setPackagesToScan(ENTITY_PACKAGES);
				this.entityManagerFactory = factory;
				return factory;
			}
			catch (final Exception ex) {
				throw new CommonPersistenceException("Other unexcepted exception raised on creating entity manager factory.", ex);
			}
		}
		return this.entityManagerFactory;
	}

	@Bean(name = "transactionManager")
	public synchronized PlatformTransactionManager annotationDrivenTransactionManager() {
		if (this.annotationDrivenTransactionManager == null) {
			final JpaTransactionManager jpaTxManager = new JpaTransactionManager();
			jpaTxManager.setEntityManagerFactory(this.entityManagerFactory.getNativeEntityManagerFactory());
			jpaTxManager.setJpaDialect(new EclipseLinkJpaDialect());
			this.annotationDrivenTransactionManager = jpaTxManager;
		}
		return this.annotationDrivenTransactionManager;
	}

	@Bean
	@Primary
	public synchronized EntityManager entityManager() {
		if (this.entityManager == null) {
			this.entityManager = SharedEntityManagerCreator.createSharedEntityManager(this.entityManagerFactory.getNativeEntityManagerFactory());
		}
		return this.entityManager;
	}

	@Bean(name = "dataSource", destroyMethod = "close")
	public synchronized DataSource preferentialDataSource() {
		if (this.preferentialDataSource == null) {
			final DruidDataSource druidDataSource = new DruidDataSource();
			druidDataSource.setDriverClassName(this.environment.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
			druidDataSource.setUrl(this.environment.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
			druidDataSource.setUsername(this.environment.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
			druidDataSource.setPassword(this.environment.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

			druidDataSource.setInitialSize(1);
			druidDataSource.setMinIdle(1);
			druidDataSource.setMaxActive(10);
			druidDataSource.setMaxWait(60000);
			druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
			druidDataSource.setMinEvictableIdleTimeMillis(300000);
			druidDataSource.setValidationQuery("SELECT * FROM DUMMY");
			druidDataSource.setTestWhileIdle(true);
			druidDataSource.setTestOnBorrow(false);
			druidDataSource.setTestOnReturn(false);
			druidDataSource.setPoolPreparedStatements(true);
			druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
			druidDataSource.setRemoveAbandoned(true);
			druidDataSource.setRemoveAbandonedTimeout(1800);
			druidDataSource.setLogAbandoned(true);
			druidDataSource.setUseGlobalDataSourceStat(true);
			druidDataSource.setConnectionProperties("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");
			try {
				druidDataSource.setFilters("stat");
			}
			catch (final SQLException e1) {
				throw new RuntimeException(e1);
			}
			try {
				druidDataSource.init();
			}
			catch (final SQLException e) {
				throw new RuntimeException(e);
			}
			this.preferentialDataSource = druidDataSource;
		}
		return this.preferentialDataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() throws SQLException {
		return new JdbcTemplate(this.preferentialDataSource());
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() throws SQLException {
		return new NamedParameterJdbcTemplate(this.preferentialDataSource());
	}

	@Override
	public void close() throws Exception {
		if ((this.entityManager != null) && this.entityManager.isOpen()) {
			this.entityManager.close();
		}
		if (this.entityManagerFactory != null) {
			this.entityManagerFactory.destroy();
		}
		if ((this.preferentialDataSource != null) && (this.preferentialDataSource instanceof DruidDataSource)) {
			((DruidDataSource) this.preferentialDataSource).close();
		}
	}
}
