package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;
import com.sap.jnc.marketing.persistence.enumeration.AuditType;
import com.sap.jnc.marketing.persistence.model.AuditBanquet;
import com.sap.jnc.marketing.persistence.model.AuditItem;

/**
 * @author I330182 Vodka Li
 *
 */
public class BanquetAuditResponse implements Serializable {

	private static final long serialVersionUID = -6643947150197543666L;

	private Calendar createOn;
	private Calendar assignTime;
	private Calendar updateOn;
	private Calendar auditTime;
	private AuditType auditType;
	private Long targetId;
	private AuditStatus auditStatus;
	private Long auditId;
	private String auditorId;
	private String banquetType;
	private String orderHanderId;
	private String officeId;
	private String hostAddress;
	private Calendar banquetTime;
	private Long terminalId;
	private String branchName;
	private List<AuditItem> auditItems;

	public List<AuditItem> getAuditItems() {
		return auditItems;
	}

	public void setAuditItems(List<AuditItem> auditItems) {
		this.auditItems = auditItems;
	}

	public BanquetAuditResponse(AuditBanquet auditBanquet) {
		if (auditBanquet == null) {
			return;
		}
		this.setAuditItems(auditBanquet.getItems());
		this.setCreateOn(auditBanquet.getCreateOn());
		this.setAssignTime(auditBanquet.getAssignTime());
		this.setAuditTime(auditBanquet.getAuditTime());
		this.setCreateOn(auditBanquet.getCreateOn());
		this.setAuditType(auditBanquet.getType());
		this.setAuditStatus(auditBanquet.getStatus());
		this.setTargetId(auditBanquet.getTargetId());
		this.setAuditId(auditBanquet.getId());
		if (null != auditBanquet.getAuditor()) {
			this.setAuditorId(auditBanquet.getAuditor().getExternalId());
		}
		if (null != auditBanquet.getBanquet()) {
			this.setHostAddress(auditBanquet.getBanquet().getHostAddress());
			this.setBanquetTime(auditBanquet.getBanquet().getBanquetTime());
			if (null != auditBanquet.getBanquet().getHandler()) {
				this.setOrderHanderId(auditBanquet.getBanquet().getHandler().getExternalId());
			}
			if (null != auditBanquet.getBanquet().getOffice()) {
				this.setOfficeId(auditBanquet.getBanquet().getOffice().getExternalId());
			}
			if (null != auditBanquet.getBanquet().getTerminal()) {
				this.setTerminalId(auditBanquet.getBanquet().getTerminal().getId());
				this.setBranchName(auditBanquet.getBanquet().getTerminal().getBranchName());
			}
			if (null != auditBanquet.getBanquet().getType()) {
				this.setBanquetType(auditBanquet.getBanquet().getType().getName());
			}
		}
	}

	public Calendar getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Calendar auditTime) {
		this.auditTime = auditTime;
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

	public String getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}

	public String getBanquetType() {
		return banquetType;
	}

	public void setBanquetType(String banquetType) {
		this.banquetType = banquetType;
	}

	public String getOrderHanderId() {
		return orderHanderId;
	}

	public void setOrderHanderId(String orderHanderId) {
		this.orderHanderId = orderHanderId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public Calendar getBanquetTime() {
		return banquetTime;
	}

	public void setBanquetTime(Calendar banquetTime) {
		this.banquetTime = banquetTime;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Calendar getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Calendar assignTime) {
		this.assignTime = assignTime;
	}

	public Calendar getCreateOn() {
		return createOn;
	}

	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public void setCreateOn(Calendar createOn) {
		this.createOn = createOn;
	}

	public Calendar getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Calendar updateOn) {
		this.updateOn = updateOn;
	}
}
