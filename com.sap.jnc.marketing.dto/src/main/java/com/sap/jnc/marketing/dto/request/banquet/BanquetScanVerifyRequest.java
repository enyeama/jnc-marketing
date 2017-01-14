package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;

/**
 * 
 * @author I324442 Sha Liu
 *
 */
public class BanquetScanVerifyRequest implements Serializable {
	private static final long serialVersionUID = -157389938528789510L;
	private String scanCode;
	private String materialId;
	
	public BanquetScanVerifyRequest(String scanCode, String materialId){
		this.setScanCode(scanCode);
		this.setMaterialId(materialId);
	}

	public String getScanCode() {
		return scanCode;
	}

	public void setScanCode(String scanCode) {
		this.scanCode = scanCode;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
}
