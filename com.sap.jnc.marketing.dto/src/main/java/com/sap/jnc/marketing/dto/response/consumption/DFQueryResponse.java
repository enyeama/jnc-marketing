package com.sap.jnc.marketing.dto.response.consumption;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

public class DFQueryResponse implements Serializable {

	private static final long serialVersionUID = 572770766172587746L;
	private String id;

	private String verificationStatus;

	private Calendar verificationDate;

	private String positionId;

	private String positionDescription;

	private String employeeName;

	private String materialID;

	private String materialName;

	private BigDecimal paidQuantity;

	private Calendar paymentDate;

	private BigDecimal verifiedQuantity;

	private BigDecimal differenceQuantity;

	private BigDecimal manualAdjustmentQuantity;

	private String paymentComment;

	private String verificationComment;

	private String manualAdjustmentComment;

	private Calendar manualAdjustmentDate;

	public Calendar getManualAdjustmentDate() {
		return manualAdjustmentDate;
	}

	public void setManualAdjustmentDate(Calendar manualAdjustmentDate) {
		this.manualAdjustmentDate = manualAdjustmentDate;
	}

	public Calendar getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Calendar paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getManualAdjustmentComment() {
		return manualAdjustmentComment;
	}

	public void setManualAdjustmentComment(String manualAdjustmentComment) {
		this.manualAdjustmentComment = manualAdjustmentComment;
	}

	public String getPaymentComment() {
		return paymentComment;
	}

	public void setPaymentComment(String paymentComment) {
		this.paymentComment = paymentComment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public Calendar getVerificationDate() {
		return verificationDate;
	}

	public void setVerificationDate(Calendar verificationDate) {
		this.verificationDate = verificationDate;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionDescription() {
		return positionDescription;
	}

	public void setPositionDescription(String positionDescription) {
		this.positionDescription = positionDescription;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getMaterialID() {
		return materialID;
	}

	public void setMaterialID(String materialID) {
		this.materialID = materialID;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public BigDecimal getPaidQuantity() {
		return paidQuantity;
	}

	public void setPaidQuantity(BigDecimal paidQuantity) {
		this.paidQuantity = paidQuantity;
	}

	public BigDecimal getVerifiedQuantity() {
		return verifiedQuantity;
	}

	public void setVerifiedQuantity(BigDecimal verifiedQuantity) {
		this.verifiedQuantity = verifiedQuantity;
	}

	public BigDecimal getDifferenceQuantity() {
		return differenceQuantity;
	}

	public void setDifferenceQuantity(BigDecimal differenceQuantity) {
		this.differenceQuantity = differenceQuantity;
	}

	public BigDecimal getManualAdjustmentQuantity() {
		return manualAdjustmentQuantity;
	}

	public void setManualAdjustmentQuantity(BigDecimal manualAdjustmentQuantity) {
		this.manualAdjustmentQuantity = manualAdjustmentQuantity;
	}

	public String getVerificationComment() {
		return verificationComment;
	}

	public void setVerificationComment(String verificationComment) {
		this.verificationComment = verificationComment;
	}

}
