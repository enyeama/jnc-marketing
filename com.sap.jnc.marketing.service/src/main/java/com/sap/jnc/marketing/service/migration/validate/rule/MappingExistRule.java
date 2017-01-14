package com.sap.jnc.marketing.service.migration.validate.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.service.config.migration.MappingElement;
import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.cache.BusinessKey;
import com.sap.jnc.marketing.service.migration.cache.BusinessKeyGenerator;
import com.sap.jnc.marketing.service.migration.cache.CacheManager;
import com.sap.jnc.marketing.service.util.JNCBeanUtils;

/**
 * 关系表的行级别校验, 与EntityExistRule不能共存
 * 
 * @author I322359
 */
@Component
@SuppressWarnings("all")
public class MappingExistRule implements Rule {

	private static Logger logger = LoggerFactory.getLogger(MappingExistRule.class);

	@Override
	public <T extends UploadRow> String check(T row, UploadField uploadField, UploadConf conf) {
		// 分别得到两个Entity
		Map<Class, List<UploadField>> uploadFieldMap = new HashMap<Class, List<UploadField>>();
		
		for (UploadField field : conf.getFieldsAnnotation().values()) {
			if(!field.dependency()){
				continue;
			}
			if (!uploadFieldMap.containsKey(field.dependencyEntity())) {
				uploadFieldMap.put(field.dependencyEntity(), new ArrayList<UploadField>());
			}
			uploadFieldMap.get(field.dependencyEntity()).add(field);
		}

		if (uploadFieldMap.size() != 2) {
			throw new RuntimeException("不是一对一的映射, 暂不实现");
		}

		MappingElement major = conf.getMapping().major();
		MappingElement slave = conf.getMapping().slave();

		BusinessKey majorBusinessKey = BusinessKeyGenerator.generateDependencyKey(row, uploadFieldMap.get(major.entity()));
		BusinessKey slaveBusinessKey = BusinessKeyGenerator.generateDependencyKey(row, uploadFieldMap.get(slave.entity()));

		CacheManager cacheManager = CacheManager.getInstance();
		Serializable majorEntity = cacheManager.getByKey(major.entity(), majorBusinessKey);
		// Serializable slaveEntity = cacheManager.getByKey(slave.entity(), slaveBusinessKey);

		// 判断一个Entity中是否包含另外一个Entity, 如果包含，表示关系存在，如果不包含，表示关系不存在
		List<Serializable> slaveEntityList = (List<Serializable>) JNCBeanUtils.getProperty(majorEntity, major.prop());
		
		boolean exist = false;
		if (slaveEntityList == null) {
			exist = false;
		}
		else {
			Map<BusinessKey, Serializable> map = new HashMap<BusinessKey, Serializable>();
			for (Serializable slaveEntity : slaveEntityList) {
				BusinessKey key = BusinessKeyGenerator.generateEntityKey(slaveEntity, uploadFieldMap.get(slave.entity()));
				map.put(key, slaveEntity);
			}
			exist = map.containsKey(slaveBusinessKey);
		}

		// 根据CUD设置msg
		if (row.getOperation().equalsIgnoreCase("C") && exist) {
			return "关系已存在";
		}
		else if ((row.getOperation().equalsIgnoreCase("U") || row.getOperation().equalsIgnoreCase("U")) && !exist) {
			return "关系不存在";
		}
		else {
			return null;
		}
	}

}
