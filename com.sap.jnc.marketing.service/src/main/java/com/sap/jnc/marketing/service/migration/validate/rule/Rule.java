package com.sap.jnc.marketing.service.migration.validate.rule;

import com.sap.jnc.marketing.service.config.migration.UploadConf;
import com.sap.jnc.marketing.service.config.migration.UploadField;
import com.sap.jnc.marketing.service.config.migration.UploadRow;

public interface Rule {

	/**
	 * 
	 * @param row
	 * @param uploadField
	 * @param conf
	 * @return
	 * @throws Exception
	 */
	public <T extends UploadRow> String check(T row, UploadField uploadField, UploadConf conf);
}
