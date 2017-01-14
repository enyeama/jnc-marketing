package com.sap.jnc.marketing.dto.respose.mainten;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.ProductSalesCategory;
/**
 * @author Maggie Liu
 */
public class MaintenSalesCategoryResponse implements Serializable {
	private static final long serialVersionUID = -406377779194952032L;
	
	private String externalId;
	private String name;

	public MaintenSalesCategoryResponse(ProductSalesCategory productSalesCategory) {
		if(productSalesCategory==null){
			return;
		}
		this.externalId = productSalesCategory.getExternalId()!=null?productSalesCategory.getExternalId():"";
		this.name = productSalesCategory.getName()!=null?productSalesCategory.getName():"";
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
