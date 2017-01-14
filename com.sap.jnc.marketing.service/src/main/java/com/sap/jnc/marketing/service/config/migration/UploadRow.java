package com.sap.jnc.marketing.service.config.migration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public abstract class UploadRow {

	protected String operation;

	protected int rowIndex;

	protected List<String> errorMsg = new ArrayList<String>();

	// public abstract void generate();

	public String getOperation() {
		return StringUtils.isBlank(operation) ? "C" : operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public List<String> getErrorMsg() {
		return errorMsg;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public void setErrorMsg(List<String> errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void addErrorMsg(String msg) {
		errorMsg.add(msg);
	}
}
