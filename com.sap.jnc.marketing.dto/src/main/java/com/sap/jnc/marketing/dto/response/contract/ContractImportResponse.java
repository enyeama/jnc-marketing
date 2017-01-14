/*
 * @author James Jiang
 */
package com.sap.jnc.marketing.dto.response.contract;

import java.io.Serializable;

public class ContractImportResponse implements Serializable {

	private static final long serialVersionUID = 6774359785684719586L;

	private String operation;
	private String contractId;
	private String name;
	private long errorCode;
	private String errorDescription;
	private long rowNumber;

	public ContractImportResponse() {

	}

	public ContractImportResponse(String operation, String contractId, long rowNumber, String name, long errorCode, String errorDescription) {
		this.setErrorCode(errorCode);
		this.setErrorDescription(errorDescription);
		this.setName(name);
		this.setRowNumber(rowNumber);
		this.setOperation(operation);
		this.setContractId(contractId);
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(long rowNumber) {
		this.rowNumber = rowNumber;
	}

	public long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

}