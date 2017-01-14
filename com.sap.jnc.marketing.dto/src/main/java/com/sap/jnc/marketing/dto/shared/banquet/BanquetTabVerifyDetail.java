package com.sap.jnc.marketing.dto.shared.banquet;

import java.util.Calendar;

import com.sap.jnc.marketing.persistence.enumeration.BanquetVerificationInvalidReason;
import com.sap.jnc.marketing.persistence.enumeration.BanquetVerificationResult;

/**
 * 
 * @author I324442 Sha Liu
 *
 */
public class BanquetTabVerifyDetail {
	private String code;

	private BanquetVerificationResult verificationResult;

	private BanquetVerificationInvalidReason invalidReason;

	private Calendar tabVefificationDate;

	public BanquetTabVerifyDetail(String code, BanquetVerificationResult verificationResult,
			BanquetVerificationInvalidReason invalidReason, Calendar tabVerificationDate) {
		this.setCode(code);
		this.setVerificationResult(verificationResult);
		this.setInvalidReason(invalidReason);
		this.setTabVefificationDate(tabVerificationDate);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BanquetVerificationResult getVerificationResult() {
		return verificationResult;
	}

	public void setVerificationResult(BanquetVerificationResult verificationResult) {
		this.verificationResult = verificationResult;
	}

	public BanquetVerificationInvalidReason getInvalidReason() {
		return invalidReason;
	}

	public void setInvalidReason(BanquetVerificationInvalidReason invalidReason) {
		this.invalidReason = invalidReason;
	}

	public Calendar getTabVefificationDate() {
		return tabVefificationDate;
	}

	public void setTabVefificationDate(Calendar tabVefificationDate) {
		this.tabVefificationDate = tabVefificationDate;
	}
}
