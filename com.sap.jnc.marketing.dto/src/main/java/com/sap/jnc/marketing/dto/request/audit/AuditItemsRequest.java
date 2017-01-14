package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditItemsRequest implements Serializable {

	private static final long serialVersionUID = -8919175740820907274L;

	private String fieldName;

	private String fieldValue;

	private Boolean isConfirm;

	private String comment;

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

	public Boolean getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Boolean isConfirm) {
		this.isConfirm = isConfirm;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
