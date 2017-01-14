package com.sap.jnc.marketing.dto.shared.banquet;

import com.sap.jnc.marketing.dto.response.banquet.BanquetTabVerificationHeaderResponse;
import com.sap.jnc.marketing.persistence.model.BanquetVerification;

/**
 * 
 * @author I324442 Sha Liu
 *
 */
public class BanquetTabVerificationResult {
  private BanquetTabVerificationHeaderResponse headerResponse;

  private BanquetVerification banquetVerification;
  
  public BanquetTabVerificationResult(){};
  
  public BanquetTabVerificationResult(BanquetTabVerificationHeaderResponse headerResponse, BanquetVerification banquetVerification){
	  this.setHeaderResponse(headerResponse);
	  this.setBanquetVerification(banquetVerification);
  }
  
  public BanquetTabVerificationHeaderResponse getHeaderResponse() {
		return headerResponse;
	}

	public void setHeaderResponse(BanquetTabVerificationHeaderResponse headerResponse) {
		this.headerResponse = headerResponse;
	}

	public BanquetVerification getBanquetVerification() {
		return banquetVerification;
	}

	public void setBanquetVerification(BanquetVerification banquetVerification) {
		this.banquetVerification = banquetVerification;
	}

}
