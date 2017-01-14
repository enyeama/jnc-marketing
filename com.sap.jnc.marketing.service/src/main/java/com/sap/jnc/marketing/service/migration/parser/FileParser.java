package com.sap.jnc.marketing.service.migration.parser;

import java.io.InputStream;
import java.util.List;

import com.sap.jnc.marketing.service.config.migration.UploadRow;

public interface FileParser {

	public <T extends UploadRow> List<T> parse(InputStream is, Class<T> clazz);
	
}
