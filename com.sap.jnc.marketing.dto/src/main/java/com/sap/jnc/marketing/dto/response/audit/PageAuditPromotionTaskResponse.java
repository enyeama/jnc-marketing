package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.enumeration.AuditResult;
import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;
import com.sap.jnc.marketing.persistence.enumeration.AuditType;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;
import com.sap.jnc.marketing.persistence.model.AuditPromotion;

/**
 * @author C5231393 Xu Xiaolei
 */
public class PageAuditPromotionTaskResponse extends PageImpl<PageAuditPromotionTaskResponse.Item> implements Serializable {

	private static final long serialVersionUID = 5232692252095258062L;

	private static final List<PageAuditPromotionTaskResponse.Item> EMPTY_LIST = new ArrayList<>(0);

	public PageAuditPromotionTaskResponse(PageRequest pageRequest) {
		super(
			// Content
			EMPTY_LIST,
			// Page Request
			pageRequest,
			// Total
			0);
	}

	public PageAuditPromotionTaskResponse(Page<AuditPromotion> pages, PageRequest pageRequest) {
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
		private Long responsibleLeaderId;

		private String responsibleLeaderName;

		private String dealerName;

		private TerminalOrderStatus orderStatus;

		private String materialId;

		private String materialName;

		private Integer quantity;

		private Long terminalId;

		private String branchName;

		private String address;

		public Item(AuditPromotion audit) {
			if (audit == null) {
				return;
			}
			if (audit.getId() != null) {
				this.setId(audit.getId());
			}
			if (audit.getTerminalOrder() != null && audit.getTerminalOrder().getId() != null) {
				this.setAuditId(audit.getTerminalOrder().getId());
			}
			if (audit.getType() != null) {
				this.setType(audit.getType());
			}
			if (audit.getTerminalOrder() != null && audit.getTerminalOrder().getId() != null) {
				this.setTarget(audit.getTerminalOrder().getId());
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
			if (audit.getTerminalOrder().getResponsibleLeader() != null && audit.getTerminalOrder().getResponsibleLeader().getId() != null) {
				this.setResponsibleLeaderId(audit.getTerminalOrder().getResponsibleLeader().getId());
			}
			if (audit.getTerminalOrder().getResponsibleLeader() != null && !StringUtils.isEmpty(audit.getTerminalOrder().getResponsibleLeader()
				.getName())) {
				this.setResponsibleLeaderName(audit.getTerminalOrder().getResponsibleLeader().getName());
			}
			if (audit.getTerminalOrder().getDealer() != null && !StringUtils.isEmpty(audit.getTerminalOrder().getDealer().getName())) {
				this.setDealerName(audit.getTerminalOrder().getDealer().getName());
			}
			if (audit.getTerminalOrder().getStatus() != null) {
				this.setOrderStatus(audit.getTerminalOrder().getStatus());
			}
			if (audit.getTerminalOrder().getProduct() != null && audit.getTerminalOrder().getProduct().getId() != null) {
				this.setMaterialId(audit.getTerminalOrder().getProduct().getId());
			}
			if (audit.getTerminalOrder().getProduct() != null && !StringUtils.isEmpty(audit.getTerminalOrder().getProduct().getName())) {
				this.setMaterialName(audit.getTerminalOrder().getProduct().getName());
			}
			if (audit.getTerminalOrder().getQuantity() != null) {
				this.setQuantity(audit.getTerminalOrder().getQuantity());
			}
			if (audit.getTerminalOrder().getTerminal() != null && audit.getTerminalOrder().getTerminal().getId() != null) {
				this.setTerminalId(audit.getTerminalOrder().getTerminal().getId());
			}
			if (audit.getTerminalOrder().getTerminal() != null && !StringUtils.isEmpty(audit.getTerminalOrder().getTerminal().getBranchName())) {
				this.setBranchName(audit.getTerminalOrder().getTerminal().getBranchName());
			}
			if (audit.getTerminalOrder().getTerminal() != null && !StringUtils.isEmpty(audit.getTerminalOrder().getTerminal().getAddress())) {
				this.setAddress(audit.getTerminalOrder().getTerminal().getAddress());
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

		public Long getResponsibleLeaderId() {
			return responsibleLeaderId;
		}

		public void setResponsibleLeaderId(Long responsibleLeaderId) {
			this.responsibleLeaderId = responsibleLeaderId;
		}

		public String getResponsibleLeaderName() {
			return responsibleLeaderName;
		}

		public void setResponsibleLeaderName(String responsibleLeaderName) {
			this.responsibleLeaderName = responsibleLeaderName;
		}

		public String getDealerName() {
			return dealerName;
		}

		public void setDealerName(String dealerName) {
			this.dealerName = dealerName;
		}

		public TerminalOrderStatus getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(TerminalOrderStatus orderStatus) {
			this.orderStatus = orderStatus;
		}

		public String getMaterialId() {
			return materialId;
		}

		public void setMaterialId(String materialId) {
			this.materialId = materialId;
		}

		public String getMaterialName() {
			return materialName;
		}

		public void setMaterialName(String materialName) {
			this.materialName = materialName;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
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

	}
}
