package com.sap.jnc.marketing.dto.response.migration;

public class EmployeeErrorMessage {
	//Excel页名称
	private String excelName;
	//Excel行数
	private Integer excelRow;
	//消息
	private String checkResult;
	
	public EmployeeErrorMessage(){
	}
	
	public EmployeeErrorMessage(String excelName, Integer excelRow, String checkResult){
		this.excelName = excelName;
		this.excelRow = excelRow;
		this.checkResult = checkResult;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public Integer getExcelRow() {
		return excelRow;
	}

	public void setExcelRow(Integer excelRow) {
		this.excelRow = excelRow;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	
}
