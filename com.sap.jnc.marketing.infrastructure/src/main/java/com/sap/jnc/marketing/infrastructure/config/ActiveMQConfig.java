package com.sap.jnc.marketing.infrastructure.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.sap.jnc.marketing.infrastructure.ExcludeForTest;

@Configuration
@ExcludeForTest
@PropertySource(value = { "classpath:META-INF/active-mq.properties" })
public class ActiveMQConfig {

	private static final String PROPERTY_JMS_BROKER_URL = "jms.broker.url";
	private static final String PROPERTY_JMS_USERNAME = "jms.broker.username";
	private static final String PROPERTY_JMS_PASSWORD = "jms.broker.password";

	@Autowired
	private Environment environment;

	@Bean(name = "jmsListenerContainerFactory")
	@Autowired
	JmsListenerContainerFactory<?> myJmsContainerFactory(PooledConnectionFactory connectionFactory) {
		if (connectionFactory == null) {
			return null;
		}
		final SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		return factory;
	}

	@Bean
	@Autowired
	JmsTemplate jmsTemplate(PooledConnectionFactory pooledConnectionFactory) {
		if (pooledConnectionFactory == null) {
			return null;
		}
		return new JmsTemplate(pooledConnectionFactory);
	}

	@Bean
	@Autowired
	PooledConnectionFactory pooledConnectionFactory(ActiveMQConnectionFactory connectionFactory) {
		if (connectionFactory == null) {
			return null;
		}
		final PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
		pooledConnectionFactory.setConnectionFactory(connectionFactory);
		return pooledConnectionFactory;
	}

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		try {
			final ActiveMQConnectionFactory activeMQConncetionFactory = new ActiveMQConnectionFactory();
			activeMQConncetionFactory.setBrokerURL(this.environment.getProperty(PROPERTY_JMS_BROKER_URL));
			activeMQConncetionFactory.setUserName(this.environment.getProperty(PROPERTY_JMS_USERNAME));
			activeMQConncetionFactory.setPassword(this.environment.getProperty(PROPERTY_JMS_PASSWORD));
			activeMQConncetionFactory.setUseAsyncSend(true);
			return activeMQConncetionFactory;
		}
		catch (final NullPointerException ex) {
			return null;
		}
	}
}
