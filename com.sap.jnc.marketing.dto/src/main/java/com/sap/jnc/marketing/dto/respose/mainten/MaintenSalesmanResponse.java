package com.sap.jnc.marketing.dto.respose.mainten;

import java.io.Serializable;
/**
 * @author Maggie Liu
 */
public class MaintenSalesmanResponse implements Serializable {
	private static final long serialVersionUID = -8796907416016561130L;
	
	private String positionId;
	private String name;
	private String phone;
	public String getJobId() {
		return positionId;
	}
	public void setJobId(String jobId) {
		this.positionId = jobId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
