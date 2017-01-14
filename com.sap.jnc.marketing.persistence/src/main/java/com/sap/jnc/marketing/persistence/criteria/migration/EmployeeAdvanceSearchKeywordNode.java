package com.sap.jnc.marketing.persistence.criteria.migration;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;

public class EmployeeAdvanceSearchKeywordNode implements SearchKeywordNode {

	private static final long serialVersionUID = -4810732548520694585L;
	//员工号
	private String externalId;
	//员工姓名
	private String name;
	//身份证号码
	private String idCardNO;
	//员工电话号码
	private String phone;
	//所属岗位编号
	private String positionExternalId;
	//所属部门编号
	private String departmentExternalId;
	//人事子范围编码
	private String productSalesCategoryExternalId;
	
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCardNO() {
		return idCardNO;
	}
	public void setIdCardNO(String idCardNO) {
		this.idCardNO = idCardNO;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPositionExternalId() {
		return positionExternalId;
	}
	public void setPositionExternalId(String positionExternalId) {
		this.positionExternalId = positionExternalId;
	}
	public String getDepartmentExternalId() {
		return departmentExternalId;
	}
	public void setDepartmentExternalId(String departmentExternalId) {
		this.departmentExternalId = departmentExternalId;
	}
	public String getProductSalesCategoryExternalId() {
		return productSalesCategoryExternalId;
	}
	public void setProductSalesCategoryExternalId(String productSalesCategoryExternalId) {
		this.productSalesCategoryExternalId = productSalesCategoryExternalId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
