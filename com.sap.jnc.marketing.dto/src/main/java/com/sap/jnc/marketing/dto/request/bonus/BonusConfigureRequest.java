package com.sap.jnc.marketing.dto.request.bonus;

import java.io.Serializable;

public class BonusConfigureRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 接受查询字段 */
	private String validFrom;

	private String validTo;

	private String categoryId;

	private String orderbyField;
	private int orderByAsc; // -- 0 升序 1 降序

	public String getOrderbyField() {
		return orderbyField;
	}

	public void setOrderbyField(String orderbyField) {
		this.orderbyField = orderbyField;
	}

	public int getOrderByAsc() {
		return orderByAsc;
	}

	public void setOrderByAsc(int orderByAsc) {
		this.orderByAsc = orderByAsc;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

}
