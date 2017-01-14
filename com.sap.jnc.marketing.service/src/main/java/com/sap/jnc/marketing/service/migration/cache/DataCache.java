package com.sap.jnc.marketing.service.migration.cache;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.sap.jnc.marketing.service.util.JNCApplicationContextUtil;

/**
 * 缓存数据
 * 
 * @author I322359
 */
@SuppressWarnings("all")
public class DataCache {

	private static Logger logger = LoggerFactory.getLogger(DataCache.class);

	private Class<? extends SimpleJpaRepository> repositoryCls;
	private Set<String> keys = new HashSet<String>();

	private Map<BusinessKey, Serializable> cacheMap = new HashMap<BusinessKey, Serializable>();

	public DataCache(Class<? extends SimpleJpaRepository> repositoryCls, String[] keys) {
		this.repositoryCls = repositoryCls;
		for (String key : keys) {
			this.keys.add(key);
		}
	}

	/**
	 * @param repositoryCls
	 *            JPA Repository
	 */
	public DataCache(Class<? extends SimpleJpaRepository> repositoryCls, Set<String> keys) {
		this.repositoryCls = repositoryCls;
		this.keys = keys;
	}

	/**
	 * 加载数据
	 */
	public void load() {
		logger.info("缓存加载开始:" + repositoryCls);
		SimpleJpaRepository jpaRepository = (SimpleJpaRepository) JNCApplicationContextUtil.getBean(repositoryCls);
		List<Serializable> list = jpaRepository.findAll();
		Map<BusinessKey, Serializable> map = new HashMap<BusinessKey, Serializable>();
		for (Serializable t : list) {
			BusinessKey businessKey = BusinessKeyGenerator.generateEntityKey(t, keys);
			map.put(businessKey, t);
		}
		synchronized (cacheMap) {
			cacheMap = map;
		}
		logger.info("缓存加载结束");
	}

	/***
	 * 判断数据是否存在
	 * 
	 * @param businessKey
	 * @return
	 */
	public boolean contains(BusinessKey businessKey) {
		if (cacheMap.containsKey(businessKey)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * 移除缓存对象
	 * 
	 * @param
	 */
	public void remove(BusinessKey businessKey) {
		cacheMap.remove(businessKey);
	}
	
	public void clear(){
		cacheMap.clear();
	}

	/**
	 * 新增一个对象
	 * 
	 * @param businessKey
	 * @param value
	 */
	public void add(BusinessKey businessKey, Serializable value) {
		cacheMap.put(businessKey, value);
	}

	/**
	 * 查询缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public Serializable getByKey(Map<String, Object> keys) {
		return cacheMap.get(new BusinessKey(keys));
	}

	/**
	 * 查询缓存对象
	 * 
	 * @param businessKey
	 * @return
	 */
	public Serializable getByKey(BusinessKey businessKey) {
		return cacheMap.get(businessKey);
	}

	public List<Object> getList() {
		return Arrays.asList(cacheMap.values().toArray());
	}
	
	public Map<BusinessKey, Serializable> getCacheMap() {
		return cacheMap;
	}
}
