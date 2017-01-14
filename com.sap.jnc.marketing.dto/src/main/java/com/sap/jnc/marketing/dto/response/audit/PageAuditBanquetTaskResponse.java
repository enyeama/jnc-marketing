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
import com.sap.jnc.marketing.persistence.model.AuditBanquet;
import com.sap.jnc.marketing.persistence.model.BanquetType;

/**
 * @author C5231393 Xu Xiaolei
 */
public class PageAuditBanquetTaskResponse extends PageImpl<PageAuditBanquetTaskResponse.Item> implements Serializable {

	private static final long serialVersionUID = 377487108225371315L;

	private static final List<PageAuditBanquetTaskResponse.Item> EMPTY_LIST = new ArrayList<>(0);

	public PageAuditBanquetTaskResponse(PageRequest pageRequest) {
		super(
			// Content
			EMPTY_LIST,
			// Page Request
			pageRequest,
			// Total
			0);
	}

	public PageAuditBanquetTaskResponse(Page<AuditBanquet> pages, PageRequest pageRequest) {
		super(
			// Content
			pages.getContent().stream().map(audit -> new Item(audit)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}

	public static class Item implements Serializable {

		private static final long serialVersionUID = -5127629717367481042L;

		// main columns
		private Long id;

		private Long auditId;

		private AuditType type;

		private AuditResult result;

		private Long target;

		private String auditor;

		private AuditStatus status;

		private Calendar createTime;

		private Calendar assignTime;

		private Calendar auditTime;

		private String office;

		// banquet columns

		private BanquetType banquetType;

		private Calendar banquetTime;

		private Long terminalId;

		private String branchName;

		private String address;

		private boolean isBanquetHotel;

		public Item(AuditBanquet audit) {
			if (audit == null) {
				return;
			}
			if (audit.getId() != null) {
				this.setId(audit.getId());
			}
			if (audit.getBanquet() != null && audit.getBanquet().getId() != null) {
				this.setAuditId(audit.getBanquet().getId());
			}
			if (audit.getType() != null) {
				this.setType(audit.getType());
			}
			if (audit.getBanquet() != null && audit.getBanquet().getId() != null) {
				this.setTarget(audit.getBanquet().getId());
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
			if (audit.getOffice() != null && !StringUtils.isEmpty(audit.getOffice().getName())) {
				this.setOffice(audit.getOffice().getName());
			}
			if (audit.getBanquet() != null && audit.getBanquet().getType() != null) {
				this.setBanquetType(audit.getBanquet().getType());
			}
			if (audit.getBanquet() != null && audit.getBanquet().getBanquetTime() != null) {
				this.setBanquetTime(audit.getBanquet().getBanquetTime());
			}
			if (audit.getBanquet() != null && audit.getBanquet().getTerminal() != null && audit.getBanquet().getTerminal().getId() != null) {
				this.setTerminalId(audit.getBanquet().getTerminal().getId());
			}
			if (audit.getBanquet() != null && audit.getBanquet().getTerminal() != null && !StringUtils.isEmpty(audit.getBanquet().getTerminal()
				.getBranchName())) {
				this.setBranchName(audit.getBanquet().getTerminal().getBranchName());
			}
			if (audit.getBanquet() != null && !StringUtils.isEmpty(audit.getBanquet().getHostAddress())) {
				this.setAddress(audit.getBanquet().getHostAddress());
			}
			if (audit.getBanquet() != null && audit.getBanquet().getTerminal() != null && audit.getBanquet().getTerminal() != null && audit
				.getBanquet().getTerminal().getIsBanquetHotel() != null) {
				this.setBanquetHotel(audit.getBanquet().getTerminal().getIsBanquetHotel());
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

		public String getOffice() {
			return office;
		}

		public void setOffice(String office) {
			this.office = office;
		}

		public BanquetType getBanquetType() {
			return banquetType;
		}

		public void setBanquetType(BanquetType banquetType) {
			this.banquetType = banquetType;
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

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public boolean isBanquetHotel() {
			return isBanquetHotel;
		}

		public void setBanquetHotel(boolean isBanquetHotel) {
			this.isBanquetHotel = isBanquetHotel;
		}

	}
}
