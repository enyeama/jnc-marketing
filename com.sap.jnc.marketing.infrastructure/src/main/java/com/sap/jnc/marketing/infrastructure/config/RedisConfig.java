package com.sap.jnc.marketing.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.sap.jnc.marketing.infrastructure.ExcludeForTest;
import com.sap.jnc.marketing.infrastructure.shared.utils.serialization.FSTObjectSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ExcludeForTest
@PropertySource(value = { "classpath:META-INF/redis.properties" })
public class RedisConfig implements AutoCloseable {

	private static final String PROPERTY_NAME_REDIS_MAX_IDLE = "redis.maxIdle";
	private static final String PROPERTY_NAME_REDIS_MAX_ACTIVE = "redis.maxActive";
	private static final String PROPERTY_NAME_REDIS_MAX_WAIT = "redis.maxWait";
	private static final String PROPERTY_NAME_REDIS_TEST_ON_BORROW = "redis.testOnBorrow";
	private static final String PROPERTY_NAME_REDIS_TEST_ON_RETURN = "redis.testOnReturn";
	private static final String PROPERTY_REDIS_CLUSTER_NODES = "spring.redis.cluster.nodes";

	private final static Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

	@Autowired
	private Environment environment;

	private JedisPoolConfig jedisPoolConfig;

	private RedisClusterConfiguration redisClusterConfiguration;

	private JedisConnectionFactory jedisConnectionFactory;

	private StringRedisTemplate stringRedisTemplate;

	@Bean
	public synchronized JedisPoolConfig jedisPoolConfig() {
		if (this.jedisPoolConfig == null) {
			try {
				this.jedisPoolConfig = new JedisPoolConfig();
				// Max idle
				this.jedisPoolConfig.setMaxIdle(Integer.parseInt(this.environment.getRequiredProperty(PROPERTY_NAME_REDIS_MAX_IDLE)));
				// Max active
				this.jedisPoolConfig.setMaxTotal(Integer.parseInt(this.environment.getRequiredProperty(PROPERTY_NAME_REDIS_MAX_ACTIVE)));
				// Max active
				this.jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(this.environment.getRequiredProperty(PROPERTY_NAME_REDIS_MAX_WAIT)));
				// Test on borrow
				this.jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(this.environment.getRequiredProperty(PROPERTY_NAME_REDIS_TEST_ON_BORROW)));
				// Test on return
				this.jedisPoolConfig.setTestOnReturn(Boolean.parseBoolean(this.environment.getRequiredProperty(PROPERTY_NAME_REDIS_TEST_ON_RETURN)));
			}
			catch (final Exception ex) {
				this.jedisPoolConfig = null;
			}
		}
		return this.jedisPoolConfig;
	}

	@Bean
	public synchronized RedisClusterConfiguration redisClusterConfiguration() {
		if (this.redisClusterConfiguration == null) {
			try {
				final String node = this.environment.getProperty(PROPERTY_REDIS_CLUSTER_NODES);
				if (this.isCluster(node)) {
					this.redisClusterConfiguration = new RedisClusterConfiguration(new ResourcePropertySource("RedisCluster",
						"classpath:META-INF/redis.properties"));
				}
			}
			catch (final Exception ex) {
				LOGGER.error("error occurred while initializing RedisClusterConfiguration", ex);
				this.redisClusterConfiguration = null;
			}
		}
		return this.redisClusterConfiguration;
	}

	@Bean
	public synchronized JedisConnectionFactory jedisConnectionFactory() {
		if (this.jedisConnectionFactory == null) {
			try {
				final String node = this.environment.getProperty(PROPERTY_REDIS_CLUSTER_NODES);
				try {
					if (this.isCluster(node)) {
						this.jedisConnectionFactory = new JedisConnectionFactory(this.redisClusterConfiguration());
					}
					else {
						final String host = node.split(":")[0];
						final int port = Integer.valueOf(node.split(":")[1]);
						this.jedisConnectionFactory = new JedisConnectionFactory();
						this.jedisConnectionFactory.setHostName(host);
						this.jedisConnectionFactory.setPort(port);
						this.jedisConnectionFactory.setUsePool(true);
					}
				}
				catch (final Exception e) {
					LOGGER.error("redis configuration error", e);
				}
			}
			catch (final Exception ex) {
				LOGGER.error("error occurred while initializing JedisConnectionFactory", ex);
				this.jedisConnectionFactory = null;
			}
		}
		return this.jedisConnectionFactory;
	}

	private boolean isCluster(String nodeString) {
		return (nodeString != null) && nodeString.contains(",");
	}

	@Bean
	public synchronized StringRedisTemplate stringRedisTemplate() {
		if (this.stringRedisTemplate == null) {
			try {
				this.stringRedisTemplate = new StringRedisTemplate(this.jedisConnectionFactory());
				// custom serializer
				final FSTObjectSerializer fstObjectSerializer = new FSTObjectSerializer();
				final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
				this.stringRedisTemplate.setKeySerializer(fstObjectSerializer);
				this.stringRedisTemplate.setHashKeySerializer(fstObjectSerializer);
				this.stringRedisTemplate.setValueSerializer(fstObjectSerializer);
				this.stringRedisTemplate.setHashValueSerializer(fstObjectSerializer);
			}
			catch (final Exception ex) {
				LOGGER.error("error occurred while initializing StringRedisTemplate", ex);
			}
		}
		return this.stringRedisTemplate;
	}

	@Override
	public void close() throws Exception {
		if (this.jedisConnectionFactory != null) {
			this.jedisConnectionFactory.destroy();
		}
	}
}
