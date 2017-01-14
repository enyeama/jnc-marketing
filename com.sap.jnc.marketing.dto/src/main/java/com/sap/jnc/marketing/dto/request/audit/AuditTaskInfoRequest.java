package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditTaskInfoRequest implements Serializable {

	private static final long serialVersionUID = 6639727496314302121L;

	private Long id;

	private AuditStatus auditStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AuditStatus getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(AuditStatus auditStatus) {
		this.auditStatus = auditStatus;
	}

}
