package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.AuditItem;

public class WeChatAuditItemsResponse implements Serializable {

	private static final long serialVersionUID = 3986373109206228450L;

	private Boolean isConfirm;
	private String fieldName;
	private String fieldValue;
	private Integer quantity;
	private String comment;

	public WeChatAuditItemsResponse(AuditItem auditItem) {
		this.setIsConfirm(auditItem.getIsConfirm());
		this.setFieldName(auditItem.getFieldName());
		this.setFieldValue(auditItem.getFieldValue());
		this.setQuantity(auditItem.getQuantity());
		this.setComment(auditItem.getComment());
	}

	public Boolean getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Boolean isConfirm) {
		this.isConfirm = isConfirm;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
