package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.AuditResult;
import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;
import com.sap.jnc.marketing.persistence.enumeration.AuditType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Table(name = "T_AUDIT", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "AuditSeq", sequenceName = "SEQ_AUDIT")
public class Audit extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 1171346042369580158L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AuditSeq")
	private Long id;

	@Column(name = "type", insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private AuditType type;

	@Column(name = "targetId", insertable = false, updatable = false)
	private Long targetId;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private AuditStatus status;

	@Column(name = "auditResult")
	@Enumerated(EnumType.STRING)
	private AuditResult auditResult;

	@Column(name = "assignTime")
	private Calendar assignTime;

	@Column(name = "auditTime")
	private Calendar auditTime;

	@Column(name = "approvalTime")
	private Calendar approvalTime;

	@Column(name = "archiveTime")
	private Calendar archiveTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "regionId")
	private Region region;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "officeId")
	private Department office;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auditorPositionId")
	private Position auditorPosition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auditorId")
	private Employee auditor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "marketingManagerId")
	private Employee marketingManager;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provinceManagerId")
	private Employee provinceManager;

	@OneToMany(mappedBy = "audit")
	private List<AuditItem> items;

	@OneToMany(mappedBy = "audit")
	private List<AuditPicture> pictures;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AuditType getType() {
		return this.type;
	}

	public void setType(AuditType type) {
		this.type = type;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public AuditStatus getStatus() {
		return this.status;
	}

	public void setStatus(AuditStatus status) {
		this.status = status;
	}

	public Calendar getAssignTime() {
		return this.assignTime;
	}

	public void setAssignTime(Calendar assignTime) {
		this.assignTime = assignTime;
	}

	public Calendar getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Calendar auditTime) {
		this.auditTime = auditTime;
	}

	public Calendar getApprovalTime() {
		return this.approvalTime;
	}

	public void setApprovalTime(Calendar approvalTime) {
		this.approvalTime = approvalTime;
	}

	public Calendar getArchiveTime() {
		return this.archiveTime;
	}

	public void setArchiveTime(Calendar archiveTime) {
		this.archiveTime = archiveTime;
	}

	public Region getRegion() {
		return this.region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Department getOffice() {
		return this.office;
	}

	public void setOffice(Department office) {
		this.office = office;
	}

	public Position getAuditorPosition() {
		return this.auditorPosition;
	}

	public void setAuditorPosition(Position auditorPosition) {
		this.auditorPosition = auditorPosition;
	}

	public Employee getAuditor() {
		return this.auditor;
	}

	public void setAuditor(Employee auditor) {
		this.auditor = auditor;
	}

	public Employee getMarketingManager() {
		return this.marketingManager;
	}

	public void setMarketingManager(Employee marketingManager) {
		this.marketingManager = marketingManager;
	}

	public Employee getProvinceManager() {
		return this.provinceManager;
	}

	public void setProvinceManager(Employee provinceManager) {
		this.provinceManager = provinceManager;
	}

	public List<AuditItem> getItems() {
		return this.items;
	}

	public void setItems(List<AuditItem> items) {
		this.items = items;
	}

	public List<AuditPicture> getPictures() {
		return this.pictures;
	}

	public void setPictures(List<AuditPicture> pictures) {
		this.pictures = pictures;
	}

	public AuditResult getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(AuditResult auditResult) {
		this.auditResult = auditResult;
	}
}
