package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;
import java.util.Calendar;

import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;
import com.sap.jnc.marketing.persistence.enumeration.AuditType;

/**
 * @author I330182 Vodka Li
 *
 */
public class WeChatAuditDetailRequest implements Serializable {

	private static final long serialVersionUID = -7435726618625930715L;
	
	private String auditorId;
	private Long id;
	private AuditType auditType;
	private AuditStatus auditStatus;
	private Calendar updateOn;
	
	public String getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setAuditStatus(AuditStatus auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public Calendar getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Calendar updateOn) {
		this.updateOn = updateOn;
	}

}
