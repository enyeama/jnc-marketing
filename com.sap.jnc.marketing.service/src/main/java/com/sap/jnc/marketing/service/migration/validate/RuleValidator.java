package com.sap.jnc.marketing.service.migration.validate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadRow;
import com.sap.jnc.marketing.service.migration.cache.CacheManager;
import com.sap.jnc.marketing.service.migration.validate.rule.Rule;
import com.sap.jnc.marketing.service.util.JNCApplicationContextUtil;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RuleValidator implements Validate{

	private static Logger logger = LoggerFactory.getLogger(RuleValidator.class);

	/**
	 * @param rows
	 * @param clazz
	 * @return
	 */
	public <T extends UploadRow> List<T> validate(List<T> rows, Class<T> clazz) {
		logger.info("校验开始，共有" + rows.size() + "条记录");
		List<T> errorRows = new LinkedList<T>();
		for (T row : rows) {
			logger.debug("当前行号为" + row.getRowIndex());
			if (!check(row, clazz)) {
				errorRows.add(row);
			}
		}
		logger.info("校验完成，有" + errorRows.size() + "条校验不通过");
		return errorRows;
	}

	/**
	 * 校验记录的信息<br/>
	 * True - 校验成功，False - 校验失败
	 * 
	 * @param row
	 * @param clazz
	 * @return
	 */
	private <T extends UploadRow> boolean check(T row, Class<T> clazz) {
		try {
			if(row == null){
				logger.info("被校验的数据为空");
				throw new RuntimeException("");
			}
			CacheManager cacheManager = CacheManager.getInstance();
			UploadConf conf = cacheManager.getUploadConf(clazz);
			Map<String, UploadField> uploadFields = conf.getFieldsAnnotation();
			
			// 行校验
			for (Class<? extends Rule> ruleCls : conf.getFileAnnotation().rules()) {
				String msg = JNCApplicationContextUtil.getBean(ruleCls).check(row, null, conf);
				if (!StringUtils.isBlank(msg)) {
					row.addErrorMsg(msg);
				}
			}

			// 行单元格校验
			for (Entry<String, UploadField> entry : uploadFields.entrySet()) {
				for (Class<? extends Rule> ruleCls : entry.getValue().rules()) {
					String msg = JNCApplicationContextUtil.getBean(ruleCls).check(row, entry.getValue(), conf);
					if (!StringUtils.isBlank(msg)) {
						row.addErrorMsg(msg);
					}
				}
			}

			return row.getErrorMsg().size() <= 0 ? true : false;
		}
		catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}
}
