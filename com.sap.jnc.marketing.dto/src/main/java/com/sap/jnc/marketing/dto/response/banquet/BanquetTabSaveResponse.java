package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;
import org.springframework.http.HttpStatus;

/**
 * 
 * @author I324442 Sha Liu
 *
 */
public class BanquetTabSaveResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private HttpStatus status;
	
	private int updatedCount;
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public int getUpdatedCount() {
		return updatedCount;
	}
	public void setUpdatedCount(int updatedCount) {
		this.updatedCount = updatedCount;
	}

}
