/**
 * 
 */
package com.sap.jnc.marketing.dto.response.logistic;

/**
 * @author I075496 Quansheng Liu
 */
public class QrCodeScanLogisticFailedRecord {
	String qrCode;
	String errorDescription;

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

}
