package com.sap.jnc.marketing.service.globalcache;

import java.io.Serializable;

/**
 * @author Alex
 */
public interface GlobalCacheService {
	void save(String key, Serializable value, int expire);

	void save(String key, Serializable value);

	Object get(String key);

	boolean delKey(String key);

	Long increBy(String key, Long value);
}
