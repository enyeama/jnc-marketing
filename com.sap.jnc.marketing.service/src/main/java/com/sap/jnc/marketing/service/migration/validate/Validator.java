package com.sap.jnc.marketing.service.migration.validate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.cache.CacheManager;

@Component
public class Validator implements Validate {

	private static Logger logger = LoggerFactory.getLogger(Validator.class);

	@Autowired
	private RuleValidator ruleValidator;

	@Autowired
	private RepeatValidator repeatValidator;

	@Autowired
	private TreeValidator treeValidator;

	/**
	 * 校验所有的记录
	 * 
	 * @param rows
	 *            所有的行
	 * @param clazz
	 *            配置类
	 * @return
	 */
	public <T extends UploadRow> List<T> validate(List<T> rows, Class<T> clazz) {
		CacheManager cacheManager = CacheManager.getInstance();
		cacheManager.clearCaches();
		UploadConf conf = cacheManager.getUploadConf(clazz);
		logger.info("正在校验：" + conf.getFileAnnotation().sheetName());
		repeatValidator.validate(rows, clazz);
		if (conf.getFileAnnotation().parentKeys().length >= 1) {
			treeValidator.validate(rows, clazz);
		}
		List<T> errorRows = ruleValidator.validate(rows, clazz);
		logger.info("完成校验：" + conf.getFileAnnotation().sheetName());
		return errorRows;
	}
}
