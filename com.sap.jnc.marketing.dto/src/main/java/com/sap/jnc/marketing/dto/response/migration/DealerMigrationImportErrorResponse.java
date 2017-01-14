package com.sap.jnc.marketing.dto.response.migration;

import java.io.Serializable;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.dto.shared.contact.ContactNode;
import com.sap.jnc.marketing.persistence.enumeration.DealerStatus;
import com.sap.jnc.marketing.persistence.model.Dealer;

/**
 * @author I323691 Marco Huang
 */
public class DealerMigrationImportErrorResponse implements Serializable {

	/**
	 * Generate serial id
	 */
	private static final long serialVersionUID = 2391729789403666543L;

	public DealerMigrationImportErrorResponse() {
	}

	private Long id;

	private String externalId;

	private String status;

	private String name;

	private Boolean isPlatformDealer;

	private String parentDealerName;

	private String rowNumber;
	private String operation;
	private String errorInfo;
	// 主经销商标识
	private String isCentralDealer;
	// 平台公司
	private String isPlatformCompany;
	// 经销商负责人岗位，暂时不用
	private String citiManagerPosition;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsPlatformDealer() {
		return this.isPlatformDealer;
	}

	public void setIsPlatformDealer(Boolean isPlatformDealer) {
		this.isPlatformDealer = isPlatformDealer;
	}

	public String getParentDealerName() {
		return this.parentDealerName;
	}

	public void setParentDealerName(String parentDealerName) {
		this.parentDealerName = parentDealerName;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getIsCentralDealer() {
		return isCentralDealer;
	}

	public void setIsCentralDealer(String isCentralDealer) {
		this.isCentralDealer = isCentralDealer;
	}

	public String getIsPlatformCompany() {
		return isPlatformCompany;
	}

	public void setIsPlatformCompany(String isPlatformCompany) {
		this.isPlatformCompany = isPlatformCompany;
	}

	public String getCitiManagerPosition() {
		return citiManagerPosition;
	}

	public void setCitiManagerPosition(String citiManagerPosition) {
		this.citiManagerPosition = citiManagerPosition;
	}

	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("{");
		result.append("\"rowNumber\":" + "\"" + this.rowNumber + "\",");
		result.append("\"operation\":" + "\"" + operation + "\",");
		result.append("\"name\":" + "\"" + name + "\",");
		result.append("\"externalId\":" + "\"" + externalId + "\",");
		result.append("\"status\":" + "\"" + status + "\",");
		result.append("\"errorInfo\":" + "\"" + errorInfo + "\"");
		result.append("}");
		return result.toString();
	}

}
