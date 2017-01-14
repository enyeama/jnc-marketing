package com.sap.jnc.marketing.dto.response.admin;

import java.io.Serializable;

public class DealerSMResponse implements Serializable {


	private static final long serialVersionUID = -8574697726555616907L;
	
	private boolean status;
	
	private String message;
	
	public DealerSMResponse(boolean status, String message) {
		this.status = status;
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
