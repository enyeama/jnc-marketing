package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;

/**
 * @author Joel.Cheng I310645
 *
 */
public class BanquetVerificationRequest implements Serializable {

	private static final long serialVersionUID = 1192344380053514149L;
	
	private Long id;

	public BanquetVerificationRequest() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
