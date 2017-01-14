/**
 * 
 */
package com.sap.jnc.marketing.dto.response.logistic;

import java.util.Collection;

/**
 * @author I075496 Quansheng Liu
 */
public class QrCodeScanLogisticResponse {
	Collection<String> successRecords;
	Collection<QrCodeScanLogisticFailedRecord> failedRecords;

	public Collection<String> getSuccessRecords() {
		return successRecords;
	}

	public void setSuccessRecords(Collection<String> successRecords) {
		this.successRecords = successRecords;
	}

	public Collection<QrCodeScanLogisticFailedRecord> getFailedRecords() {
		return failedRecords;
	}

	public void setFailedRecords(Collection<QrCodeScanLogisticFailedRecord> failedRecords) {
		this.failedRecords = failedRecords;
	}

}
