package com.sap.jnc.marketing.service.migration.validate.rule;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadRow;

@Component
public class NonBlankRule implements Rule {

	@Override
	public <T extends UploadRow> String check(T row, UploadField uploadField, UploadConf conf) {
		try {
			return !StringUtils.isBlank(BeanUtils.getProperty(row, uploadField.prop())) ? null : uploadField.header() + "不能为空";
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
