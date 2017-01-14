package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;

/**
 * 
 * @author I324442
 *
 */
public class BanquetTabVerificationHeaderRequest implements Serializable{
	private static final long serialVersionUID = -1131778345301485390L;
	
	private Long banquetVerificationId;

	public Long getBanquetVerificationId() {
		return banquetVerificationId;
	}

	public void setBanquetVerificationId(Long banquetVerificationId) {
		this.banquetVerificationId = banquetVerificationId;
	}
	
}
