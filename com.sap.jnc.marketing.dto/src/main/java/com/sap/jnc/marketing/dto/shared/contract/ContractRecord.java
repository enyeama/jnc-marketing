package com.sap.jnc.marketing.dto.shared.contract;

import java.io.Serializable;
import java.util.Calendar;

import com.sap.jnc.marketing.persistence.enumeration.ContractStatus;

public class ContractRecord implements Serializable {

	private static final long serialVersionUID = 1111121804105317107L;

	private String operationFlag;

	private String contratId;

	private String contractTemplate;

	private String status;

	private ContractStatus statusEnum;

	private String dealerId;

	private String positionId;

	private String finacialYear;

	private Calendar ValidFrom;
	private String ValidFromStr;

	private Calendar ValidTo;
	private String ValidToStr;

	private String dmsCategoryId;

	private String channelId;

	private String region;

	public String getValidFromStr() {
		return ValidFromStr;
	}

	public void setValidFromStr(String validFromStr) {
		ValidFromStr = validFromStr;
	}

	public String getValidToStr() {
		return ValidToStr;
	}

	public void setValidToStr(String validToStr) {
		ValidToStr = validToStr;
	}

	public ContractStatus getStatusEnum() {
		return statusEnum;
	}

	public void setStatusEnum(ContractStatus statusEnum) {
		this.statusEnum = statusEnum;
	}

	public String getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	public String getContratId() {
		return contratId;
	}

	public void setContratId(String contratId) {
		this.contratId = contratId;
	}

	public String getContractTemplate() {
		return contractTemplate;
	}

	public void setContractTemplate(String contractTemplate) {
		this.contractTemplate = contractTemplate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getFinacialYear() {
		return finacialYear;
	}

	public void setFinacialYear(String finacialYear) {
		this.finacialYear = finacialYear;
	}

	public Calendar getValidFrom() {
		return ValidFrom;
	}

	public void setValidFrom(Calendar validFrom) {
		ValidFrom = validFrom;
	}

	public Calendar getValidTo() {
		return ValidTo;
	}

	public void setValidTo(Calendar validTo) {
		ValidTo = validTo;
	}

	public String getDmsCategoryId() {
		return dmsCategoryId;
	}

	public void setDmsCategoryId(String dmsCategoryId) {
		this.dmsCategoryId = dmsCategoryId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
