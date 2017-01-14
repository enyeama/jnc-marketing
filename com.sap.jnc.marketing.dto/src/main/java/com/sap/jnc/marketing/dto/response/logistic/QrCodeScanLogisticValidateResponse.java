/**
 * 
 */
package com.sap.jnc.marketing.dto.response.logistic;

/**
 * @author Quansheng Liu I075496
 */
public class QrCodeScanLogisticValidateResponse {
	private long errCode;
	private String errDescription;
	private long orderId;

	public long getErrCode() {
		return errCode;
	}

	public void setErrCode(long errCode) {
		this.errCode = errCode;
	}

	public String getErrDescription() {
		return errDescription;
	}

	public void setErrDescription(String errDescription) {
		this.errDescription = errDescription;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

}
