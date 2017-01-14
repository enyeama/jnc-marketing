package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import com.sap.jnc.marketing.persistence.enumeration.BanquetVerifyApplicationStatus;
import com.sap.jnc.marketing.persistence.model.VerifiedBottleQuantity;

/**
 * @author I332242 Zhu Qiang
 */
public class BanquetVerifyApplyRequest implements Serializable {

	private static final long serialVersionUID = -500868656257978569L;

	private Long id;

	private String expressNO;

	private Calendar expressTime;

	private Calendar planArriveTime;

	private Calendar applyTime;

	private String expressCompany;

	private BanquetVerifyApplicationStatus status;

	private String verifier;

	private Calendar verifiedFinishTime;

	private BigDecimal bottleQuantityValue;
	
	private String bottleQuantityUnit;

	private VerifiedBottleQuantity verifiedBottleQuantity;

	private Long creatorId;

	private Long officeId;

	private String salesChannel;

	private Long banquetId;
	
	private Integer boxQuantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpressNO() {
		return expressNO;
	}

	public void setExpressNO(String expressNO) {
		this.expressNO = expressNO;
	}

	public Calendar getExpressTime() {
		return expressTime;
	}

	public void setExpressTime(Calendar expressTime) {
		this.expressTime = expressTime;
	}

	public Calendar getPlanArriveTime() {
		return planArriveTime;
	}

	public void setPlanArriveTime(Calendar planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	public Calendar getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Calendar applyTime) {
		this.applyTime = applyTime;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public BanquetVerifyApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(BanquetVerifyApplicationStatus status) {
		this.status = status;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public Calendar getVerifiedFinishTime() {
		return verifiedFinishTime;
	}

	public void setVerifiedFinishTime(Calendar verifiedFinishTime) {
		this.verifiedFinishTime = verifiedFinishTime;
	}

	public VerifiedBottleQuantity getVerifiedBottleQuantity() {
		return verifiedBottleQuantity;
	}

	public void setVerifiedBottleQuantity(VerifiedBottleQuantity verifiedBottleQuantity) {
		this.verifiedBottleQuantity = verifiedBottleQuantity;
	}

	public Long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	public Long getBanquetId() {
		return banquetId;
	}

	public void setBanquetId(Long banquetId) {
		this.banquetId = banquetId;
	}

	public BigDecimal getBottleQuantityValue() {
		return bottleQuantityValue;
	}

	public void setBottleQuantityValue(BigDecimal bottleQuantityValue) {
		this.bottleQuantityValue = bottleQuantityValue;
	}

	public String getBottleQuantityUnit() {
		return bottleQuantityUnit;
	}

	public void setBottleQuantityUnit(String bottleQuantityUnit) {
		this.bottleQuantityUnit = bottleQuantityUnit;
	}

	public Integer getBoxQuantity() {
		return boxQuantity;
	}

	public void setBoxQuantity(Integer boxQuantity) {
		this.boxQuantity = boxQuantity;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

}
