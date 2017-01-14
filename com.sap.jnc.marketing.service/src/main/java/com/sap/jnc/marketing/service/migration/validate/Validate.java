package com.sap.jnc.marketing.service.migration.validate;

import java.util.List;

import com.sap.jnc.marketing.service.config.migration.UploadRow;

public interface Validate {

	/**
	 * 校验所有的记录
	 * 
	 * @param rows 所有的行
	 * @param clazz 配置类
	 * @return
	 */
	public <T extends UploadRow> List<T> validate(List<T> rows, Class<T> clazz);
}
