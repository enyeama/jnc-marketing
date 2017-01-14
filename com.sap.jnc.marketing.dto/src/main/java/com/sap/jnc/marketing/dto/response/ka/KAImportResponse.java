/**
 * 
 */
package com.sap.jnc.marketing.dto.response.ka;

/**
 * @author Quansheng Liu I075496
 */
public class KAImportResponse {
	private String name;
	private long errorCode;
	private String errorDescription;
	private long rowNumber;

	public KAImportResponse() {

	}

	public KAImportResponse(long rowNumber, String name, long errorCode, String errorDescription) {
		this.setErrorCode(errorCode);
		this.setErrorDescription(errorDescription);
		this.setName(name);
		this.setRowNumber(rowNumber);
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
