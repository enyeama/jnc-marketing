package com.sap.jnc.marketing.service.migration.cache;

import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.util.JNCBeanUtils;

/**
 * Entity对象生成器
 * @author I322359
 *
 */
public class EntityGenerator {

	/**
	 * 新建一个对象
	 * @param row
	 * @return
	 */
	public static Object generate(UploadRow row) {
		try {
			CacheManager cacheManager = CacheManager.getInstance();
			UploadConf conf = cacheManager.getUploadConf(row.getClass());
			Object obj = conf.getFileAnnotation().entity().newInstance();
			return update(obj, row);
		}
		catch (Exception e) {
			throw new RuntimeException(row.getClass() + "生成实体失败", e);
		}
	}
	
	/**
	 * 修改一个对象
	 * @param obj
	 * @param row
	 * @return
	 */
	public static void modify(Object obj, UploadRow row){
		update(obj, row);
	}
	
	/**
	 * 
	 * @param row
	 * @return
	 */
	private static Object update(Object obj, UploadRow row) {
		try {
			if(obj == null){
				throw new RuntimeException("需要修改的对象是NULL");
			}

			CacheManager cacheManager = CacheManager.getInstance();
			UploadConf conf = cacheManager.getUploadConf(row.getClass());

			// 将属性添加到Entity实例中
			for (UploadField field : conf.getFieldsAnnotation().values()) {
				String prop = field.prop();
				if (!field.dependency()) {
					// 如果是Native属性, 直接设置
					Object value = JNCBeanUtils.getProperty(row, prop);
					JNCBeanUtils.setProperty(obj, field.field(), value);
				}
				else {
					// 如果是dependency，则需要设置Entity对象
					BusinessKey businessKey = BusinessKeyGenerator.generateDependencyKey(row, field, conf);
					Object value = cacheManager.getByKey(field.dependencyEntity(), businessKey);
					JNCBeanUtils.setProperty(obj, field.field(), value);
				}
			}
			return obj;
		}
		catch (Exception e) {
			throw new RuntimeException(row.getClass() + "生成实体失败", e);
		}
	}
}
