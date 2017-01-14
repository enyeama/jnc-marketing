package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;
import com.sap.jnc.marketing.persistence.enumeration.AuditType;

/**
 * @author I330182 Vodka Li
 *
 */
public class PendingAuditRequest implements Serializable{
	
	private static final long serialVersionUID = -4105521868523803079L;
	
	private String auditorId;
	private AuditType auditType;
	private AuditStatus auditStatus;
	
	public String getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}


	public AuditType getAuditType() {
		return auditType;
	}

	public void setAuditType(AuditType auditType) {
		this.auditType = auditType;
	}

	public AuditStatus getAuditStatus() {
		return auditStatus;
	}
	
	public void setAuditStatus(AuditStatus auditStatus){
		this.auditStatus = auditStatus;
	}
}
