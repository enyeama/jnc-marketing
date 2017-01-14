package com.sap.jnc.marketing.service.config.migration;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sap.jnc.marketing.service.migration.validate.rule.EntityExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.MappingExistRule;
import com.sap.jnc.marketing.service.migration.validate.rule.Rule;

public class UploadConf {

	private Class<? extends UploadRow> clazz;

	private UploadFile uploadFile;

	private Map<Class<? extends Serializable>, Cacheable> cacheMap = new HashMap<Class<? extends Serializable>, Cacheable>();

	private Map<String, UploadField> fieldsAnnotation = new HashMap<String, UploadField>();

	private Mapping mapping;

	public UploadConf(Class<? extends UploadRow> clazz) {
		this.clazz = clazz;
		parse(clazz);
	}

	public UploadFile getFileAnnotation() {
		return uploadFile;
	}

	public Map<String, UploadField> getFieldsAnnotation() {
		return fieldsAnnotation;
	}
	
	@SuppressWarnings("rawtypes")
	public List<UploadField> getUploadFields(Class dependEntityCls){
		List<UploadField> list = new ArrayList<UploadField>();
		for(UploadField uploadField : fieldsAnnotation.values()){
			if(uploadField.dependencyEntity() == dependEntityCls){
				list.add(uploadField);
			}
		}
		return list;
	}

	public Cacheable getCache(Class<? extends Serializable> clazz) {
		return cacheMap.get(clazz);
	}

	public Map<Class<? extends Serializable>, Cacheable> getCacheMap() {
		return cacheMap;
	}

	public Mapping getMapping() {
		return mapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

	private void parse(Class<? extends UploadRow> clazz) {
		UploadFile uploadFile = clazz.getAnnotation(UploadFile.class);
		this.uploadFile = uploadFile;

		Caches caches = clazz.getAnnotation(Caches.class);
		for (Cacheable cache : caches.list()) {
			cacheMap.put(cache.entity(), cache);
		}

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			UploadField uploadField = field.getAnnotation(UploadField.class);
			fieldsAnnotation.put(field.getName(), uploadField);
		}

		Mapping mapping = clazz.getAnnotation(Mapping.class);
		setMapping(mapping);

		List<Class<? extends Rule>> list = Arrays.asList(uploadFile.rules());
		if (list.contains(MappingExistRule.class) && mapping == null) {
			throw new RuntimeException("规则中包含Mapping校验，但没有指定Mapping");
		}
		if (list.contains(MappingExistRule.class) && list.contains(EntityExistRule.class)) {
			throw new RuntimeException("行校验规则中不能同时指定Mapping校验和Entity校验");
		}
	}

	/**
	 * 配置类
	 * 
	 * @return
	 */
	public Class<? extends UploadRow> getConfClazz() {
		return clazz;
	}
}
