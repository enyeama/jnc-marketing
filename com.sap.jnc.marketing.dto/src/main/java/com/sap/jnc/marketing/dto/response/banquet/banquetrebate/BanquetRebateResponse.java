/**
 * 
 */
package com.sap.jnc.marketing.dto.response.banquet.banquetrebate;

import java.io.Serializable;

/**
 * @author Joel.Cheng I310645
 *
 */
public class BanquetRebateResponse implements Serializable {

	private static final long serialVersionUID = 419457693022571157L;
	
	private String resultCode;
	
	private String returnMessage;

	/**
	 * 
	 */
	public BanquetRebateResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

}
