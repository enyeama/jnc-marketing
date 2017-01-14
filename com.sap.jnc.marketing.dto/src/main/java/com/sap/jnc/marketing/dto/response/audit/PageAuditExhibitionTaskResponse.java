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
import com.sap.jnc.marketing.persistence.model.AuditExhibition;
import com.sap.jnc.marketing.persistence.model.ExhibitionType;

/**
 * @author C5231393 Xu Xiaolei
 */
public class PageAuditExhibitionTaskResponse extends PageImpl<PageAuditExhibitionTaskResponse.Item> implements Serializable {

	private static final long serialVersionUID = 6491833161972062065L;

	private static final List<PageAuditExhibitionTaskResponse.Item> EMPTY_LIST = new ArrayList<>(0);

	public PageAuditExhibitionTaskResponse(PageRequest pageRequest) {
		super(
			// Content
			EMPTY_LIST,
			// Page Request
			pageRequest,
			// Total
			0);
	}

	public PageAuditExhibitionTaskResponse(Page<AuditExhibition> pages, PageRequest pageRequest) {
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

		// exhibition columns
		private Long terminalId;

		private String branchName;

		private String address;

		private BranchType branchType;

		private BranchLevel branchLevel;

		private ExhibitionType exhibitionType;

		public Item(AuditExhibition audit) {
			if (audit == null) {
				return;
			}
			if (audit.getId() != null) {
				this.setId(audit.getId());
			}
			if (audit.getExhibition() != null && audit.getExhibition().getId() != null) {
				this.setAuditId(audit.getExhibition().getId());
			}
			if (audit.getType() != null) {
				this.setType(audit.getType());
			}
			if (audit.getExhibition() != null && audit.getExhibition().getId() != null) {
				this.setTarget(audit.getExhibition().getId());
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
			if (audit.getExhibition() != null && audit.getExhibition().getTerminal() != null && audit.getExhibition().getTerminal().getId() != null) {
				this.setTerminalId(audit.getExhibition().getTerminal().getId());
			}
			if (audit.getExhibition() != null && audit.getExhibition().getTerminal() != null && !StringUtils.isEmpty(audit.getExhibition()
				.getTerminal().getBranchName())) {
				this.setBranchName(audit.getExhibition().getTerminal().getBranchName());
			}
			if (audit.getExhibition() != null && audit.getExhibition().getTerminal() != null && !StringUtils.isEmpty(audit.getExhibition()
				.getTerminal().getAddress())) {
				this.setAddress(audit.getExhibition().getTerminal().getAddress());
			}
			if (audit.getExhibition() != null && audit.getExhibition().getTerminal() != null && audit.getExhibition().getTerminal()
				.getBranchType() != null) {
				this.setBranchType(audit.getExhibition().getTerminal().getBranchType());
			}
			if (audit.getExhibition() != null && audit.getExhibition().getTerminal() != null && audit.getExhibition().getTerminal()
				.getBranchLevel() != null) {
				this.setBranchLevel(audit.getExhibition().getTerminal().getBranchLevel());
			}
			if (audit.getExhibition() != null && audit.getExhibition().getExhibitionType() != null) {
				this.setExhibitionType(audit.getExhibition().getExhibitionType());
			}

		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getAuditId() {
			return auditId;
		}

		public void setAuditId(Long auditId) {
			this.auditId = auditId;
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

		public String getAuditor() {
			return auditor;
		}

		public void setAuditor(String auditor) {
			this.auditor = auditor;
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

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public BranchType getBranchType() {
			return branchType;
		}

		public void setBranchType(BranchType branchType) {
			this.branchType = branchType;
		}

		public BranchLevel getBranchLevel() {
			return branchLevel;
		}

		public void setBranchLevel(BranchLevel branchLevel) {
			this.branchLevel = branchLevel;
		}

		public ExhibitionType getExhibitionType() {
			return exhibitionType;
		}

		public void setExhibitionType(ExhibitionType exhibitionType) {
			this.exhibitionType = exhibitionType;
		}

	}
}
