package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditDetailResponse implements Serializable {

	private static final long serialVersionUID = 7420844264880759616L;

	List<AuditItemResponse> auditItems = new ArrayList<AuditItemResponse>();

	List<AuditPictureResponse> auditPicture = new ArrayList<AuditPictureResponse>();

	public List<AuditItemResponse> getAuditItems() {
		return auditItems;
	}

	public void setAuditItems(List<AuditItemResponse> auditItems) {
		this.auditItems = auditItems;
	}

	public List<AuditPictureResponse> getAuditPicture() {
		return auditPicture;
	}

	public void setAuditPicture(List<AuditPictureResponse> auditPicture) {
		this.auditPicture = auditPicture;
	}

}
