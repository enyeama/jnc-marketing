package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;
import com.sap.jnc.marketing.persistence.enumeration.AuditType;
import com.sap.jnc.marketing.persistence.enumeration.TerminalType;

/**
 * @author I330182 Vodka Li
 *
 */
public class AuditBriefRequest implements Serializable {

	private static final long serialVersionUID = -7866086281390866486L;
	
	private Long auditorId;
	private AuditType auditType;
	private AuditStatus auditStatus;
	private Long auditId;
	private TerminalType terminalType;
	
	public Long getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Long auditorId) {
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

	public void setAuditStatus(AuditStatus auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public TerminalType getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(TerminalType terminalType) {
		this.terminalType = terminalType;
	}

	
}
