package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.SpareMaterialActivityType;
import com.sap.jnc.marketing.persistence.enumeration.SpareMaterialVerificationStatus;

@Entity
@Table(name = "T_SPARE_MAT_PAYMENT", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "SpareMaterialPaymentSeq", sequenceName = "SEQ_SPAREMATERIALPAYMENT")
public class SpareMaterialPayment extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 1863766440174209061L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SpareMaterialPaymentSeq")
	private Long id;

	@Column(name = "paymentId")
	private String paymentId;

	@Column(name = "paymentDate")
	private Calendar paymentDate;

	@Column(name = "verificationDate")
	private Calendar verificationDate;

	@Column(name = "verificationStatus")
	@Enumerated(EnumType.STRING)
	private SpareMaterialVerificationStatus verificationStatus;

	private PaidQuantity paidQuantity;

	private VerifiedQuantity verifiedQuantity;

	private DifferenceQuantity differenceQuantity;

	@Column(name = "activityType")
	@Enumerated(EnumType.STRING)
	private SpareMaterialActivityType activityType;
	
	@Column(name = "manualAdjustmentQuantity")
	private BigDecimal manualAdjustmentQuantity;
	
	@Column(name = "manualAdjustmentDate")
	private Calendar manualAdjustmentDate;

	@Column(name = "manualAdjustmentComment")
	private String ManualAdjustmentComment;

	@Column(name = "paymentComment")
	private String paymentComment;

	@Column(name = "verificationComment")
	private String verificationComment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materialId")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeId")
	private Employee employee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "positionId")
	private Position position;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentId() {
		return this.paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Calendar getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Calendar paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Calendar getVerificationDate() {
		return this.verificationDate;
	}

	public void setVerificationDate(Calendar verificationDate) {
		this.verificationDate = verificationDate;
	}

	public SpareMaterialVerificationStatus getVerificationStatus() {
		return this.verificationStatus;
	}

	public void setVerificationStatus(SpareMaterialVerificationStatus verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public PaidQuantity getPaidQuantity() {
		return this.paidQuantity;
	}

	public void setPaidQuantity(PaidQuantity paidQuantity) {
		this.paidQuantity = paidQuantity;
	}

	public VerifiedQuantity getVerifiedQuantity() {
		return this.verifiedQuantity;
	}

	public void setVerifiedQuantity(VerifiedQuantity verifiedQuantity) {
		this.verifiedQuantity = verifiedQuantity;
	}

	public DifferenceQuantity getDifferenceQuantity() {
		return this.differenceQuantity;
	}

	public void setDifferenceQuantity(DifferenceQuantity differenceQuantity) {
		this.differenceQuantity = differenceQuantity;
	}

	public String getPaymentComment() {
		return this.paymentComment;
	}

	public void setPaymentComment(String paymentComment) {
		this.paymentComment = paymentComment;
	}

	public String getVerificationComment() {
		return this.verificationComment;
	}

	public void setVerificationComment(String verificationComment) {
		this.verificationComment = verificationComment;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public SpareMaterialActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(SpareMaterialActivityType activityType) {
		this.activityType = activityType;
	}

	public BigDecimal getManualAdjustmentQuantity() {
		return manualAdjustmentQuantity;
	}

	public void setManualAdjustmentQuantity(BigDecimal manualAdjustmentQuantity) {
		this.manualAdjustmentQuantity = manualAdjustmentQuantity;
	}

	public Calendar getManualAdjustmentDate() {
		return manualAdjustmentDate;
	}

	public void setManualAdjustmentDate(Calendar manualAdjustmentDate) {
		this.manualAdjustmentDate = manualAdjustmentDate;
	}

	public String getManualAdjustmentComment() {
		return ManualAdjustmentComment;
	}

	public void setManualAdjustmentComment(String manualAdjustmentComment) {
		ManualAdjustmentComment = manualAdjustmentComment;
	}
}
