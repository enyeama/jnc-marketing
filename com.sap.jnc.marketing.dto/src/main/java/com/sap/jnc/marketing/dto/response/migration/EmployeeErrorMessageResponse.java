package com.sap.jnc.marketing.dto.response.migration;

import java.util.ArrayList;
import java.util.List;

public class EmployeeErrorMessageResponse {
	private int status;//0空 1成功 -1失败
	private int totalNum;
	private int successNum;
	private int errorNum;
	private List<EmployeeErrorMessage> data = new ArrayList<EmployeeErrorMessage>();
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public int getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}
	public int getErrorNum() {
		return errorNum;
	}
	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}
	public void setData(List<EmployeeErrorMessage> data) {
		this.data = data;
	}
	public void addMessage(EmployeeErrorMessage msg){
		data.add(msg);
	}
	public List<EmployeeErrorMessage> getData() {
		return data;
	}
}
