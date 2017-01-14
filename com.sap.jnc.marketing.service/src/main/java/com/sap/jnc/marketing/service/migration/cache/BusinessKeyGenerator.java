package com.sap.jnc.marketing.service.migration.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadFile;
import com.sap.jnc.marketing.service.config.migration.UploadRow;

@SuppressWarnings("all")
public class BusinessKeyGenerator {

	private static Logger logger = LoggerFactory.getLogger(BusinessKeyGenerator.class);

	/**
	 * 获得JPA Entity的BusinessKey
	 * 
	 * @param t
	 * @param keys
	 * @return
	 */
	public static <T extends Serializable> BusinessKey generateEntityKey(T t, List<UploadField> uploadFields) {
		Set<String> set = new HashSet<String>();
		for (UploadField uploadField : uploadFields) {
			set.add(uploadField.dependencyKeys());
		}
		return generateEntityKey(t, set);
	}
	
	/**
	 * 获得JPA Entity的BusinessKey
	 * 
	 * @param t
	 * @param keys
	 * @return
	 */
	public static <T extends Serializable> BusinessKey generateEntityKey(T t, String[] keys) {
		Set<String> set = new HashSet<String>();
		for (String key : keys) {
			set.add(key);
		}
		return generateEntityKey(t, set);
	}

	/**
	 * 获得JPA Entity的BusinessKey
	 * 
	 * @param t
	 * @param keys
	 * @return
	 */
	public static <T extends Serializable> BusinessKey generateEntityKey(T t, Set<String> keys) {
		try {
			BusinessKey businessKey = new BusinessKey();
			if (keys.size() <= 0) {
				throw new RuntimeException(t.getClass() + "没有配置业务主键");
			}
			for (String key : keys) {
				Object val = BeanUtils.getProperty(t, key);
				businessKey.addKeyValue(key, val == null ? "" : val);
			}
			return businessKey;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获得Row的Dependency Entity的BusinessKey
	 * 
	 * @param row
	 * @param uploadField
	 * @param conf
	 * @return
	 */
	public static <T extends UploadRow> BusinessKey generateDependencyKey(T row, UploadField uploadField, UploadConf conf) {
		try {

			Map<String, UploadField> uploadFields = conf.getFieldsAnnotation();
			Class<? extends Serializable> entityCls = uploadField.dependencyEntity();

			List<UploadField> list = new ArrayList<UploadField>();
			for (UploadField field : uploadFields.values()) {
				if (field.dependencyEntity() == entityCls) {
					list.add(field);
				}
			}

			return generateDependencyKey(row, list);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T extends UploadRow> BusinessKey generateDependencyKey(T row, List<UploadField> list) {
		try {
			BusinessKey businessKey = new BusinessKey();
			for (UploadField field : list) {
				String prop = field.prop();
				businessKey.addKeyValue(field.dependencyKeys(), BeanUtils.getProperty(row, prop));
			}
			return businessKey;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获得Row的Entity的BusinessKey
	 * 
	 * @param row
	 * @param uploadFile
	 * @param conf
	 * @return
	 */
	public static <T extends UploadRow> BusinessKey generateRowKey(T row, UploadFile uploadFile, UploadConf conf) {
		try {
			BusinessKey businessKey = new BusinessKey();
			for (String key : uploadFile.keys()) {
				businessKey.addKeyValue(key, BeanUtils.getProperty(row, key));
			}
			return businessKey;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
