package com.sap.jnc.marketing.service.globalcache.impl;

import com.sap.jnc.marketing.infrastructure.shared.constant.WebAttributes;
import com.sap.jnc.marketing.service.globalcache.GlobalCacheService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author Alex
 */
@Service
public class GlobalCacheServiceImpl implements GlobalCacheService {

	protected static Logger LOGGER = LoggerFactory.getLogger(GlobalCacheServiceImpl.class);

	@Autowired
	private RedisTemplate redisAccess;

	@Override
	public void save(String key, Serializable value, int expire) {
		LOGGER.debug("{} : {} saved in cache", key, value);
		redisAccess.opsForValue().set(WebAttributes.APP_GLOBAL_VARIABLE+ "_" +key, value);
		redisAccess.expire(WebAttributes.APP_GLOBAL_VARIABLE+ "_" +key, expire, TimeUnit.SECONDS);
	}

	@Override
	public void save(String key, Serializable value) {
		LOGGER.debug("{} : {} saved in cache", key, value);
		redisAccess.opsForValue().set(WebAttributes.APP_GLOBAL_VARIABLE+ "_" +key, value);
	}

	@Override
	public Object get(String key) {
		LOGGER.debug("trying to get {} from cache", key);
		return redisAccess.opsForValue().get(WebAttributes.APP_GLOBAL_VARIABLE+ "_" +key);
	}

	@Override
	public boolean delKey(String key) {
		redisAccess.delete(WebAttributes.APP_GLOBAL_VARIABLE+ "_" +key);
		return true;
	}

	@Override
	public Long increBy(String key, Long value) {
		return redisAccess.opsForValue().increment(WebAttributes.APP_GLOBAL_VARIABLE+ "_" +key, value);
	}
}
