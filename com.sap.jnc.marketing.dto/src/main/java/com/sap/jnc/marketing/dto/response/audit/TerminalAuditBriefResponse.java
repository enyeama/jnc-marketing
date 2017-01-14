package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;
import java.util.Calendar;

import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalType;
import com.sap.jnc.marketing.persistence.model.AuditTerminal;

/**
 * @author I330182 Vodka Li
 *
 */
public class TerminalAuditBriefResponse implements Serializable {

	private static final long serialVersionUID = -4264673060318284106L;
	private Calendar createOn;
	private Calendar assignTime;
	private String branchName;
	private String address;
	private Long targetId;
	private AuditStatus auditStatus;
	private Long auditId;
	private TerminalType terminalType;
	private Calendar updateOn;
	
	public TerminalAuditBriefResponse(AuditTerminal auditTerminal) {
		if (auditTerminal == null) {
			return;
		}
		if (null != auditTerminal.getTerminal()) {
			this.setBranchName(auditTerminal.getTerminal().getBranchName());
			this.setAddress(auditTerminal.getTerminal().getAddress());
			this.setTerminalType(auditTerminal.getTerminal().getType());
		}	
		this.setTargetId(auditTerminal.getTargetId());
		this.setCreateOn(auditTerminal.getCreateOn());
		this.setAssignTime(auditTerminal.getAssignTime());
		this.setStatus(auditTerminal.getStatus());
		this.setUpdateOn(auditTerminal.getUpdateOn());
		this.setCreateOn(auditTerminal.getCreateOn());
		this.setAuditId(auditTerminal.getId());
	}

	public Calendar getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Calendar createOn) {
		this.createOn = createOn;
	}

	public Calendar getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Calendar assignTime) {
		this.assignTime = assignTime;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public AuditStatus getStatus() {
		return this.auditStatus;
	}

	public void setStatus(AuditStatus status) {
		this.auditStatus = status;
	}
	
	public TerminalType getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(TerminalType terminalType) {
		this.terminalType = terminalType;
	}

	public Calendar getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Calendar updateOn) {
		this.updateOn = updateOn;
	}
	
	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

}
