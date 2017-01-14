package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

import com.sap.jnc.marketing.persistence.enumeration.AuditResult;
import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;
import com.sap.jnc.marketing.persistence.enumeration.AuditType;
import com.sap.jnc.marketing.persistence.enumeration.BranchLevel;
import com.sap.jnc.marketing.persistence.enumeration.BranchType;
import com.sap.jnc.marketing.persistence.model.AuditTerminal;

/**
 * @author C5231393 Xu Xiaolei
 */
public class PageAuditTerminalTaskResponse extends PageImpl<PageAuditTerminalTaskResponse.Item> implements Serializable {

	private static final long serialVersionUID = -789219639739571775L;

	private static final List<PageAuditTerminalTaskResponse.Item> EMPTY_LIST = new ArrayList<>(0);

	public PageAuditTerminalTaskResponse(PageRequest pageRequest) {
		super(
			// Content
			EMPTY_LIST,
			// Page Request
			pageRequest,
			// Total
			0);
	}

	public PageAuditTerminalTaskResponse(Page<AuditTerminal> pages, PageRequest pageRequest) {
		super(
			// Content
			pages.getContent().stream().map(audit -> new Item(audit)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}

	public static class Item implements Serializable {

		private static final long serialVersionUID = 5000922401640377108L;

		// main columns
		private Long id;

		private Long auditId;

		private AuditType type;

		private Long target;

		private String auditor;

		private AuditStatus status;

		private AuditResult result;

		private Calendar createTime;

		private Calendar assignTime;

		private Calendar auditTime;

		// hotel columns
		private String branchName;

		private String address;

		private BranchLevel branchLevel;

		private BranchType branchType;

		private String channel;

		private boolean isBanquetHotel;

		private String keyUserName;

		private String keyUserPhone;

		private String keyUserPositionId;

		public Item(AuditTerminal audit) {
			if (audit == null) {
				return;
			}
			if (audit.getId() != null) {
				this.setId(audit.getId());
			}
			if (audit.getTerminal() != null && audit.getTerminal().getId() != null) {
				this.setAuditId(audit.getTerminal().getId());
			}
			if (audit.getType() != null) {
				this.setType(audit.getType());
			}
			if (audit.getTerminal() != null && audit.getTerminal().getId() != null) {
				this.setTarget(audit.getTerminal().getId());
			}
			if (audit.getAuditor() != null) {
				this.setAuditor(audit.getAuditor().getName());
			}
			if (audit.getStatus() != null) {
				this.setStatus(audit.getStatus());
			}
			if (audit.getAuditResult() != null) {
				this.setResult(audit.getAuditResult());
			}
			if (audit.getCreateOn() != null) {
				this.setCreateTime(audit.getCreateOn());
			}
			if (audit.getAssignTime() != null) {
				this.setAssignTime(audit.getAssignTime());
			}
			if (audit.getAuditTime() != null) {
				this.setAuditTime(audit.getAuditTime());
			}
			if (audit.getTerminal() != null && !StringUtils.isEmpty(audit.getTerminal().getBranchName())) {
				this.setBranchName(audit.getTerminal().getBranchName());
			}
			if (audit.getTerminal() != null && !StringUtils.isEmpty(audit.getTerminal().getAddress())) {
				this.setAddress(audit.getTerminal().getAddress());
			}
			if (audit.getTerminal() != null && audit.getTerminal().getBranchLevel() != null) {
				this.setBranchLevel(audit.getTerminal().getBranchLevel());
			}
			if (audit.getTerminal() != null && audit.getTerminal().getBranchType() != null) {
				this.setBranchType(audit.getTerminal().getBranchType());
			}
			if (audit.getTerminal() != null && audit.getTerminal().getChannel() != null && !StringUtils.isEmpty(audit.getTerminal().getChannel()
				.getName())) {
				this.setChannel(audit.getTerminal().getChannel().getName());
			}
			if (audit.getTerminal() != null && audit.getTerminal().getIsBanquetHotel() != null) {
				this.setBanquetHotel(audit.getTerminal().getIsBanquetHotel());
			}
			if (audit.getTerminal() != null && audit.getTerminal().getKeyUserContact() != null && !StringUtils.isEmpty(audit.getTerminal()
				.getKeyUserContact().getName())) {
				this.setKeyUserName(audit.getTerminal().getKeyUserContact().getName());
			}
			if (audit.getTerminal() != null && audit.getTerminal().getKeyUserContact() != null && !StringUtils.isEmpty(audit.getTerminal()
				.getKeyUserContact().getPhone())) {
				this.setKeyUserPhone(audit.getTerminal().getKeyUserContact().getPhone());
			}
			if (audit.getTerminal() != null && audit.getTerminal().getKeyUserContact() != null && audit.getTerminal().getKeyUserContact()
				.getPosition() != null) {
				this.setKeyUserPositionId(audit.getTerminal().getKeyUserContact().getPosition());
			}
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public AuditType getType() {
			return type;
		}

		public void setType(AuditType type) {
			this.type = type;
		}

		public Long getTarget() {
			return target;
		}

		public void setTarget(Long target) {
			this.target = target;
		}

		public AuditStatus getStatus() {
			return status;
		}

		public void setStatus(AuditStatus status) {
			this.status = status;
		}

		public AuditResult getResult() {
			return result;
		}

		public void setResult(AuditResult result) {
			this.result = result;
		}

		public String getAuditor() {
			return auditor;
		}

		public void setAuditor(String auditor) {
			this.auditor = auditor;
		}

		public Calendar getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Calendar createTime) {
			this.createTime = createTime;
		}

		public Calendar getAssignTime() {
			return assignTime;
		}

		public void setAssignTime(Calendar assignTime) {
			this.assignTime = assignTime;
		}

		public Calendar getAuditTime() {
			return auditTime;
		}

		public void setAuditTime(Calendar auditTime) {
			this.auditTime = auditTime;
		}

		public Long getAuditId() {
			return auditId;
		}

		public void setAuditId(Long auditId) {
			this.auditId = auditId;
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

		public BranchLevel getBranchLevel() {
			return branchLevel;
		}

		public void setBranchLevel(BranchLevel branchLevel) {
			this.branchLevel = branchLevel;
		}

		public BranchType getBranchType() {
			return branchType;
		}

		public void setBranchType(BranchType branchType) {
			this.branchType = branchType;
		}

		public String getChannel() {
			return channel;
		}

		public void setChannel(String channel) {
			this.channel = channel;
		}

		public boolean isBanquetHotel() {
			return isBanquetHotel;
		}

		public void setBanquetHotel(boolean isBanquetHotel) {
			this.isBanquetHotel = isBanquetHotel;
		}

		public String getKeyUserName() {
			return keyUserName;
		}

		public void setKeyUserName(String keyUserName) {
			this.keyUserName = keyUserName;
		}

		public String getKeyUserPhone() {
			return keyUserPhone;
		}

		public void setKeyUserPhone(String keyUserPhone) {
			this.keyUserPhone = keyUserPhone;
		}

		public String getKeyUserPositionId() {
			return keyUserPositionId;
		}

		public void setKeyUserPositionId(String keyUserPositionId) {
			this.keyUserPositionId = keyUserPositionId;
		}

	}
}
