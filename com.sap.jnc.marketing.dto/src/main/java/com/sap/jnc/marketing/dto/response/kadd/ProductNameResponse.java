package com.sap.jnc.marketing.dto.response.kadd;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.Product;

/**
 * @author Maggie Liu
 */
public class ProductNameResponse implements Serializable {
	private static final long serialVersionUID = -8614359251870526453L;
	
	private String productName;
	private String productId;
	
	public ProductNameResponse(Product product){
		if(product==null){
			return;
		}
		this.productName=product.getName()!=null?product.getName():"";
		this.productId=product.getId()!=null?product.getId():"";
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
