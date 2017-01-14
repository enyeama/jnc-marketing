package com.sap.jnc.marketing.service.migration.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.service.config.migration.MappingElement;
import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadFile;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.cache.BusinessKey;
import com.sap.jnc.marketing.service.migration.cache.BusinessKeyGenerator;
import com.sap.jnc.marketing.service.migration.cache.CacheManager;
import com.sap.jnc.marketing.service.migration.cache.EntityGenerator;
import com.sap.jnc.marketing.service.util.JNCApplicationContextUtil;
import com.sap.jnc.marketing.service.util.JNCBeanUtils;

/**
 * 持久化
 * 
 * @author I322359
 */
@Component
@SuppressWarnings("all")
public class UploadPersistence {

	private static Logger logger = LoggerFactory.getLogger(UploadPersistence.class);

	/**
	 * 持久化
	 * 
	 * @param rows
	 *            读取上传文件Sheet的数据
	 * @param clazz
	 *            上传配置类
	 */
	public <T extends UploadRow> Map<String, List> persist(List<T> rows, Class<T> clazz) {
		CacheManager cacheManager = CacheManager.getInstance();
		logger.info("持久化开始：" + cacheManager.getUploadConf(clazz).getFileAnnotation().sheetName());
		List<T> creationList = new LinkedList<T>();
		List<T> modificationList = new LinkedList<T>();
		List<T> deleteList = new LinkedList<T>();
		for (T row : rows) {
			if (row.getOperation().equalsIgnoreCase("C") || row.getOperation().equals("") || row.getOperation() == null) {
				creationList.add(row);
			}
			else if (row.getOperation().equalsIgnoreCase("U")) {
				modificationList.add(row);
			}
			else if (row.getOperation().equalsIgnoreCase("D")) {
				deleteList.add(row);
			}
			else {

			}
		}

		Map<String, List> map = new HashMap<String, List>();
		map.put("C", persistCreation(creationList, clazz));
		map.put("U", persistModification(modificationList, clazz));
		map.put("D", persistDelete(deleteList, clazz));

		logger.info("持久化完成：" + cacheManager.getUploadConf(clazz).getFileAnnotation().sheetName());
		return map;
	}

	/**
	 * 创建
	 * 
	 * @param rows
	 *            行记录
	 * @param clazz
	 *            使用的配置类
	 */
	private <T extends UploadRow> List persistCreation(List<T> rows, Class<T> clazz) {
		CacheManager cacheManager = CacheManager.getInstance();
		UploadConf conf = cacheManager.getUploadConf(clazz);
		UploadFile uploadFile = conf.getFileAnnotation();

		// 获得当前配置类所对应的Entity Class和字段属性
		Class<?> entityCls = conf.getFileAnnotation().entity();
		Map<String, UploadField> uploadFields = conf.getFieldsAnnotation();
		try {
			if (conf.getMapping() == null) {
				// 一般对象维护
				List list = new ArrayList();
				for (T row : rows) {
					Object obj = EntityGenerator.generate(row);
					list.add(obj);
				}
				SimpleJpaRepository repository = (SimpleJpaRepository) JNCApplicationContextUtil.getBean(uploadFile.repository());
				repository.save(list);
				return list;
			}
			else {
				// 映射关系处理
				List majorList = new ArrayList();
				List slaveList = new ArrayList();
				MappingElement major = conf.getMapping().major();
				MappingElement slave = conf.getMapping().slave();
				for (T row : rows) {
					BusinessKey majorBusinessKey = BusinessKeyGenerator.generateDependencyKey(row, conf.getUploadFields(major.entity()));
					Serializable majorEntity = cacheManager.getByKey(major.entity(), majorBusinessKey);
					List<Serializable> slaveEntityList = (List<Serializable>) JNCBeanUtils.getProperty(majorEntity, major.prop());
					if(slaveEntityList == null && majorEntity != null){
						slaveEntityList = new ArrayList();
						JNCBeanUtils.setProperty(majorEntity, major.prop(), slaveEntityList);
					}
					
					BusinessKey slaveBusinessKey = BusinessKeyGenerator.generateDependencyKey(row, conf.getUploadFields(slave.entity()));
					Serializable slaveEntity = cacheManager.getByKey(slave.entity(), slaveBusinessKey);
					List<Serializable> majorEntityList = (List<Serializable>) JNCBeanUtils.getProperty(slaveEntity, slave.prop());
					if(majorEntityList == null && slaveEntity != null){
						majorEntityList = new ArrayList();
						JNCBeanUtils.setProperty(slaveEntity, slave.prop(), majorEntityList);
					}
				
					if(slaveEntityList != null){
						slaveEntityList.add(slaveEntity);
					}
					if(majorEntityList != null){
						majorEntityList.add(majorEntity);
					}
					
					if(majorEntity != null && majorList != null){
						majorList.add(majorEntity);
					}
					if(slaveEntity != null && slaveList != null){
						slaveList.add(slaveEntity);
					}
				}

				SimpleJpaRepository majorRepository = (SimpleJpaRepository) JNCApplicationContextUtil.getBean(major.repository());
				majorRepository.save(majorList);

				SimpleJpaRepository slaveRepository = (SimpleJpaRepository) JNCApplicationContextUtil.getBean(slave.repository());
				majorRepository.save(slaveList);

				return majorList;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 更新
	 * 
	 * @param rows
	 * @param clazz
	 */
	private <T extends UploadRow> List persistModification(List<T> rows, Class<T> clazz) {
		List list = new ArrayList();
		CacheManager cacheManager = CacheManager.getInstance();
		UploadConf conf = cacheManager.getUploadConf(clazz);
		Map<String, UploadField> uploadFields = conf.getFieldsAnnotation();
		try {
			if (conf.getMapping() == null) {
				for (T row : rows) {
					// get business key
					BusinessKey businessKey = BusinessKeyGenerator.generateRowKey(row, conf.getFileAnnotation(), conf);

					// get entity
					Object obj = cacheManager.getByKey(conf.getFileAnnotation().entity(), businessKey);
					EntityGenerator.modify(obj, row);
					list.add(obj);
				}
				SimpleJpaRepository repository = (SimpleJpaRepository) JNCApplicationContextUtil.getBean(conf.getFileAnnotation().repository());
				repository.save(list);
				return list;
			}
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除
	 * 
	 * @param rows
	 * @param clazz
	 */
	private <T extends UploadRow> List persistDelete(List<T> rows, Class<T> clazz) {
		List list = new ArrayList();
		CacheManager cacheManager = CacheManager.getInstance();
		UploadConf conf = cacheManager.getUploadConf(clazz);
		try {
			if (conf.getMapping() == null) {
				for (T row : rows) {
					BusinessKey businessKey = BusinessKeyGenerator.generateRowKey(row, conf.getFileAnnotation(), conf);
					Object obj = cacheManager.getByKey(conf.getFileAnnotation().entity(), businessKey);
					list.add(obj);
				}
				SimpleJpaRepository repository = (SimpleJpaRepository) JNCApplicationContextUtil.getBean(conf.getFileAnnotation().repository());
				repository.delete(list);
				return list;
			}
			else {
				// 映射关系处理
				List majorList = new ArrayList();
				List slaveList = new ArrayList();
				MappingElement major = conf.getMapping().major();
				MappingElement slave = conf.getMapping().slave();
				for (T row : rows) {
					BusinessKey majorBusinessKey = BusinessKeyGenerator.generateDependencyKey(row, conf.getUploadFields(major.entity()));
					Serializable majorEntity = cacheManager.getByKey(major.entity(), majorBusinessKey);
					List<Serializable> slaveEntityList = (List<Serializable>) JNCBeanUtils.getProperty(majorEntity, major.prop());

					BusinessKey slaveBusinessKey = BusinessKeyGenerator.generateDependencyKey(row, conf.getUploadFields(slave.entity()));
					Serializable slaveEntity = cacheManager.getByKey(slave.entity(), slaveBusinessKey);
					List<Serializable> majorEntityList = (List<Serializable>) JNCBeanUtils.getProperty(majorEntity, major.prop());

					slaveEntityList.remove(slaveEntity);
					majorEntityList.remove(majorEntity);

					majorList.add(majorEntity);
					slaveList.add(slaveEntity);
				}

				SimpleJpaRepository majorRepository = (SimpleJpaRepository) JNCApplicationContextUtil.getBean(major.repository());
				majorRepository.save(majorList);

				SimpleJpaRepository slaveRepository = (SimpleJpaRepository) JNCApplicationContextUtil.getBean(slave.repository());
				majorRepository.save(slaveList);

				return majorList;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
