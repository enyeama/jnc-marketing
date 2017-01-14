package com.sap.jnc.marketing.service.migration.validate.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.cache.BusinessKey;
import com.sap.jnc.marketing.service.migration.cache.CacheManager;

/**
 * 缓存依赖校验
 * 
 * @author I322359
 */
@Component
public class DependencyRule implements Rule {

	private static Logger logger = LoggerFactory.getLogger(EntityExistRule.class);

	@Override
	public <T extends UploadRow> String check(T row, UploadField uploadField, UploadConf conf) {
		try {
			if (row == null) {
				throw new RuntimeException("被校验的数据为空");
			}
			if (!uploadField.dependency()) {
				return null;
			}
			Class<? extends Serializable> entityCls = uploadField.dependencyEntity();

			// 得到相同实体的所有业务键
			Map<String, UploadField> uploadFields = conf.getFieldsAnnotation();
			List<UploadField> list = new ArrayList<UploadField>();
			for (UploadField field : uploadFields.values()) {
				if (field.dependencyEntity() == entityCls) {
					list.add(field);
				}
			}
			BusinessKey businessKey = new BusinessKey();
			for (UploadField filed : list) {
				// TODO get values
				String prop = filed.prop();
				businessKey.addKeyValue(filed.dependencyKeys(), BeanUtils.getProperty(row, prop));
			}
			CacheManager cacheManager = CacheManager.getInstance();
			return cacheManager.contains(conf, entityCls, businessKey) ? null : uploadField.header() + "不存在";
		}
		catch (Exception e) {
			if (row != null) {
				logger.error(conf.getFileAnnotation().sheetName() + ",错误记录" + JSONObject.toJSONString(row));
			}
			throw new RuntimeException(e);
		}
	}

}
