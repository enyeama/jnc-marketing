package com.sap.jnc.marketing.service.exception.migration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sap.jnc.marketing.service.config.migration.UploadRow;

/**
 * 校验有不符合规则记录时异常
 * 
 * @author I322359
 */
public class ValidationRecordException extends RuntimeException {
	
	private static final long serialVersionUID = -3559670037666430522L;

	private Map<String, List<? extends UploadRow>> map = new HashMap<String, List<? extends UploadRow>>();
	
	public ValidationRecordException(String errorMessage, Map<String,  List<? extends UploadRow>> map) {
		super(errorMessage);
		this.setMap(map);
	}
	
	public ValidationRecordException(String errorMessage, String name, List<? extends UploadRow> list) {
		super(errorMessage);
		this.getMap().put(name, list);
	}
	
	public void setMap(Map<String, List<? extends UploadRow>> map) {
		this.map = map;
	}
	
	public Map<String, List<? extends UploadRow>> getMap() {
		return map;
	}
	
}
