package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;
import java.util.List;
import com.sap.jnc.marketing.dto.shared.banquet.BoxCasePair;

/**
 * 
 * @author I324442 Sha Liu
 *
 */
public class BanquetScanUpdateRequest implements Serializable {
	private static final long serialVersionUID = 3287179851930864488L;
	private Long banquetId;
	private String materialId;
	private List<BoxCasePair> boxCasePairs;
	private boolean legecyProduct;

	public Long getBanquetId() {
		return banquetId;
	}

	public void setBanquetId(Long banquetId) {
		this.banquetId = banquetId;
	}
	
	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public List<BoxCasePair> getBoxCasePairs() {
		return boxCasePairs;
	}

	public void setBoxCasePairs(List<BoxCasePair> boxCasePairs) {
		this.boxCasePairs = boxCasePairs;
	}
	
	public boolean isLegecyProduct() {
		return legecyProduct;
	}

	public void setLegecyProduct(boolean legecyProduct) {
		this.legecyProduct = legecyProduct;
	}
}
