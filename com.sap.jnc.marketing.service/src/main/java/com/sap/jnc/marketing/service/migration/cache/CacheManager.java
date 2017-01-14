package com.sap.jnc.marketing.service.migration.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.jnc.marketing.service.config.migration.Cacheable;
import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.util.JNCBeanUtils;

/**
 * 缓存管理, 在多用户操作下，不同的导入会有不同的缓存
 * 
 * @author I322359
 */
public class CacheManager {

	private static Logger logger = LoggerFactory.getLogger(CacheManager.class);

	private static ThreadLocal<CacheManager> cacheManagerLocal = new ThreadLocal<CacheManager>();
	
	private Map<Class, DataCache> dataCaches = new HashMap<Class, DataCache>();

	private Map<Class, UploadConf> uploadConfMap = new HashMap<Class, UploadConf>();

	private Map<Class, Map<BusinessKey, ? extends UploadRow>> treeCaches = new HashMap<Class, Map<BusinessKey, ? extends UploadRow>>();

	public static CacheManager getInstance(){
		if (cacheManagerLocal.get() == null) {  
			// 如果connThreadLocal没有本线程对应的Connection创建一个新的Connection，  
	        // 并将其保存到线程本地变量中。  
			CacheManager cacheManager = new CacheManager();  
			cacheManagerLocal.set(cacheManager);  
            return cacheManager;  
        } else {  
        	// 直接返回线程本地变量  
            return cacheManagerLocal.get();
        }  
	}
	
	public static void clear(){
		cacheManagerLocal.remove();
	}
	
	public void clearCaches() {
		uploadConfMap.clear();
		for (DataCache dataCache : dataCaches.values()) {
			dataCache.clear();
		}
		dataCaches.clear();
	}

	public DataCache getCacheByRowConf(Class<? extends UploadRow> clazz){
		UploadConf conf = getUploadConf(clazz);
		return getCache(conf.getFileAnnotation().entity());
	}
	
	/**
	 * 获得一个对象
	 * 
	 * @param entityCls
	 * @param businessKey
	 * @return
	 */
	public <T extends Serializable> T getByKey(Class<T> entityCls, BusinessKey businessKey) {
		// 首先在数据库缓存中进行查询，若有则返回，否则进入下一个缓存进行查询
		DataCache cache = getCache(entityCls);
		if (cache != null && cache.contains(businessKey)) {
			return (T) cache.getByKey(businessKey);
		}
		Map<BusinessKey, ? extends UploadRow> map = treeCaches.get(entityCls);
		if (map != null && map.containsKey(businessKey)) {
			// 重新组装JPA数据
			return (T) EntityGenerator.generate(map.get(businessKey));
		}
		return null;
	}

	/**
	 * 决断一个实体是否存在
	 * 
	 * @param conf
	 * @param entityCls
	 * @param key
	 * @return
	 */
	public boolean contains(UploadConf conf, Class<? extends Serializable> entityCls, BusinessKey key) {
		DataCache dataCache = getCache(entityCls);
		return dataCache.contains(key);
	}

	/**
	 * 判断一个实体是否存在
	 * 
	 * @param entityCls
	 * @param key
	 * @return
	 */
	public boolean contains(Class<? extends Serializable> entityCls, BusinessKey key) {
		DataCache cache = getCache(entityCls);
		return cache.contains(key);
	}

	/**
	 * 深拷贝缓存
	 * 
	 * @param entityCls
	 * @return
	 */
	public DataCache copyCache(Class<? extends Serializable> entityCls) {
		try {
			DataCache cache = getCache(entityCls);
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(cache);
			// 从流里读出来
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);
			return (DataCache) oi.readObject();
		}
		catch (Exception e) {
			logger.error("深拷贝出错", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param entityCls
	 * @return
	 */
	public Map<BusinessKey, ? extends UploadRow> revertRows(UploadConf conf) {
		try {
			Map<BusinessKey, UploadRow> map = new HashMap<BusinessKey, UploadRow>();
			DataCache dataCache = getCache(conf.getFileAnnotation().entity());
			for (Entry<BusinessKey, Serializable> entry : dataCache.getCacheMap().entrySet()) {
				BusinessKey businessKey = entry.getKey();
				Serializable entity = entry.getValue();
				Class<? extends UploadRow> cls = conf.getConfClazz();
				UploadRow row = cls.newInstance();
				for (UploadField uploadField : conf.getFieldsAnnotation().values()) {
					Object fieldValue = JNCBeanUtils.getProperty(entity, uploadField.field());

					if (uploadField.dependency()) {
						// 获得实体的属性值
						Object dependObjProp = JNCBeanUtils.getProperty(fieldValue, uploadField.dependencyKeys());
						JNCBeanUtils.setProperty(row, uploadField.prop(), dependObjProp);
					}
					else {
						JNCBeanUtils.setProperty(row, uploadField.prop(), fieldValue);
					}
				}
				map.put(businessKey, row);
			}
			return map;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public <T extends UploadRow> void addTreeCache(Class entityCls, Map<BusinessKey, T> treeCache) {
		treeCaches.put(entityCls, treeCache);
	}

	public <T extends UploadRow> Map<BusinessKey, T> getTreeCache(Class entityCls) {
		return (Map<BusinessKey, T>) treeCaches.get(entityCls);
	}

	/**
	 * 获得一个的配置
	 * 
	 * @param clazz
	 *            配置类
	 * @return
	 */
	public UploadConf getUploadConf(Class<? extends UploadRow> clazz) {
		if (!uploadConfMap.containsKey(clazz)) {
			UploadConf conf = new UploadConf(clazz);
			uploadConfMap.put(clazz, conf);
		}
		return uploadConfMap.get(clazz);
	}

	/**
	 * @param entityCls
	 * @return
	 */
	private DataCache getCache(Class<? extends Serializable> entityCls) {
		loadClassCache(entityCls);
		return dataCaches.get(entityCls);
	}

	/**
	 * 加载一个类对应的缓存
	 * 
	 * @param entityCls
	 */
	private void loadClassCache(Class<? extends Serializable> entityCls) {
		if (!dataCaches.containsKey(entityCls)) {
			for (UploadConf conf : uploadConfMap.values()) {
				for (Cacheable cacheable : conf.getCacheMap().values()) {
					if (cacheable.entity() == entityCls) {
						DataCache cache = new DataCache(cacheable.repository(), cacheable.keys());
						dataCaches.put(cacheable.entity(), cache);
						cache.load();
					}
				}
			}
		}
	}

	/**
	 * 加载一个配置下的所有缓存
	 * 
	 * @param conf
	 */
	private void loadCaches(UploadConf conf) {
		for (Cacheable cacheable : conf.getCacheMap().values()) {
			if (dataCaches.containsKey(cacheable.entity())) {
				DataCache cache = new DataCache(cacheable.repository(), cacheable.keys());
				dataCaches.put(cacheable.entity(), cache);
				cache.load();
			}
		}
	}

	/**
	 * 加载所有的配置缓存
	 */
	private void loadAllCaches() {
		for (UploadConf conf : uploadConfMap.values()) {
			loadCaches(conf);
		}
	}

}
