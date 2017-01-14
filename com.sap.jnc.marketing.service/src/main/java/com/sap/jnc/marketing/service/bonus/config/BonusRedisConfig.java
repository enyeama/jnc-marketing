package com.sap.jnc.marketing.service.bonus.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.jnc.marketing.dto.shared.bonus.BonusRule;

@Configuration
public class BonusRedisConfig {

	private StringRedisTemplate bonusRuleRedisTemplate;
	private StringRedisTemplate bonusListRedisTemplate;

	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	// TODO @Bean(name = "bonusRuleRedisTemplate")
	public synchronized StringRedisTemplate bonusRuleRedisTemplate() {
		if (this.bonusRuleRedisTemplate == null) {
			try {
				this.bonusRuleRedisTemplate = new StringRedisTemplate(this.jedisConnectionFactory);
				this.bonusRuleRedisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<Long>(Long.class));
				this.bonusRuleRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<BonusRule>(BonusRule.class));
			}
			catch (final Exception ex) {
				this.bonusRuleRedisTemplate = null;
			}
		}
		return this.bonusRuleRedisTemplate;
	}

	// TODO @Bean(name = "bonusListRedisTemplate")
	public synchronized StringRedisTemplate bonusListRedisTemplate() {
		if (this.bonusListRedisTemplate == null) {
			try {
				this.bonusListRedisTemplate = new StringRedisTemplate(this.jedisConnectionFactory);
				this.bonusListRedisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<Long>(Long.class));
				final ObjectMapper objectMapper = new ObjectMapper();
				final JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Integer.class);
				this.bonusListRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<List<Integer>>(javaType));
			}
			catch (final Exception ex) {
				this.bonusListRedisTemplate = null;
			}
		}

		return this.bonusListRedisTemplate;
	}
}
