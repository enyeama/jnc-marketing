package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;
import java.util.List;
import org.springframework.http.HttpStatus;
import com.sap.jnc.marketing.dto.shared.banquet.BoxCasePair;

/**
 * 
 * @author I324442 Sha Liu
 *
 */

public class BanquetScanVerifyResponse implements Serializable {
	private static final long serialVersionUID = -6177845449036225261L;
	private HttpStatus status;
	private List<BoxCasePair> boxCasePairs;
	public BanquetScanVerifyResponse(){};

	public BanquetScanVerifyResponse(HttpStatus status, List<BoxCasePair> boxCasePairs){
		this.setStatus(status);
		this.setBoxCasePairs(boxCasePairs);
	}
	
	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	public List<BoxCasePair> getBoxCasePairs() {
		return boxCasePairs;
	}

	public void setBoxCasePairs(List<BoxCasePair> boxCasePairs) {
		this.boxCasePairs = boxCasePairs;
	}
}
