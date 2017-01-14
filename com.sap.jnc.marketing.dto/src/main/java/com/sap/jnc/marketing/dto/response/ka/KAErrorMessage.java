package com.sap.jnc.marketing.dto.response.ka;

public class KAErrorMessage {
	// Excel行数
	private Integer excelRow;
	// 操作
	private String type;
	//网点编号
	private String id;
	//网点名称
	private String name;
	//错误信息
	private String errorMessage;
	
	


	public Integer getExcelRow() {
		return excelRow;
	}


	public void setExcelRow(Integer excelRow) {
		this.excelRow = excelRow;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
}
