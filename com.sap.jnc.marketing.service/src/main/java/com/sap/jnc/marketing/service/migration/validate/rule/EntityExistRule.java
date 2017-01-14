package com.sap.jnc.marketing.service.migration.validate.rule;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.cache.BusinessKey;
import com.sap.jnc.marketing.service.migration.cache.CacheManager;

/**
 * 实体行级别校验，与MappingExistRule不能共存
 * 
 * @author I322359
 */
@Component
public class EntityExistRule implements Rule {

	private static Logger logger = LoggerFactory.getLogger(EntityExistRule.class);

	@Override
	public <T extends UploadRow> String check(T row, UploadField uploadField, UploadConf conf) {
		try {
			if (row == null) {
				throw new RuntimeException("被校验的数据为空");
			}
			Class<? extends Serializable> entityCls = conf.getFileAnnotation().entity();

			BusinessKey businessKey = new BusinessKey();
			for (String key : conf.getFileAnnotation().keys()) {
				businessKey.addKeyValue(key, BeanUtils.getProperty(row, key));
			}

			CacheManager cacheManager = CacheManager.getInstance();
			boolean exist = cacheManager.contains(conf, entityCls, businessKey);

			if (row.getOperation().equalsIgnoreCase("C") && exist) {
				return "记录已存在";
			}
			else if ((row.getOperation().equalsIgnoreCase("U") || row.getOperation().equalsIgnoreCase("D")) && !exist) {
				return "记录不存在";
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			if (row != null) {
				logger.error(conf.getFileAnnotation().sheetName() + ",错误记录" + JSONObject.toJSONString(row));
			}
			throw new RuntimeException(e);
		}
	}

}
