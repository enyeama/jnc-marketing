package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;

/**
 * 
 * @author I324442
 *
 */
public class BanquetTabVerificationRequest implements Serializable{

	private static final long serialVersionUID = 8470753776446183838L;
	
	private String code;

	private String banquetVerificationNumber;
	
	public BanquetTabVerificationRequest(String verificationNumber, String code){
		this.setBanquetVerificationNumber(verificationNumber);
		this.setCode(code);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBanquetVerificationNumber() {
		return banquetVerificationNumber;
	}

	public void setBanquetVerificationNumber(String banquetVerificationNumber) {
		this.banquetVerificationNumber = banquetVerificationNumber;
	}

}
