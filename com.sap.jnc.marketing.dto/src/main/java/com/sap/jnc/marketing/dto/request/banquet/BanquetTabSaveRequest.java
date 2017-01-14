package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;
import java.util.List;

import com.sap.jnc.marketing.dto.shared.banquet.BanquetTabVerifyDetail;

/**
 * 
 * @author I324442 Sha Liu
 *
 */

public class BanquetTabSaveRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String banquetVerificationNumber;

	private List<BanquetTabVerifyDetail> verifyDetails;
	
	public String getBanquetVerificationId() {
		return banquetVerificationNumber;
	}

	public void setBanquetVerificationNumber(String banquetVerificationNumber) {
		this.banquetVerificationNumber = banquetVerificationNumber;
	}

	public List<BanquetTabVerifyDetail> getVerifyDetails() {
		return verifyDetails;
	}

	public void setVerifyDetails(List<BanquetTabVerifyDetail> verifyDetails) {
		this.verifyDetails = verifyDetails;
	}

}
