/**
 * 
 */
package com.sap.jnc.marketing.dto.request.banquet.banquetrebate;

import java.io.Serializable;

/**
 * @author Joel.Cheng I310645
 *
 */
public class BanquetRebateScanRequest implements Serializable {

	private static final long serialVersionUID = 2017368556824961373L;
	
	private long banquetId;
	
	private String scanCode;
	
	public BanquetRebateScanRequest(){
	}

	public long getBanquetId() {
		return banquetId;
	}

	public void setBanquetId(long banquetId) {
		this.banquetId = banquetId;
	}

	public String getScanCode() {
		return scanCode;
	}

	public void setScanCode(String scanCode) {
		this.scanCode = scanCode;
	}
	
}
