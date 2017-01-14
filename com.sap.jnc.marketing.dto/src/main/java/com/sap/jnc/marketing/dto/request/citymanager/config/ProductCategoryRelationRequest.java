/**
 * 
 */
package com.sap.jnc.marketing.dto.request.citymanager.config;

/**
 * @author Quansheng Liu I075496
 */
public class ProductCategoryRelationRequest {
	private long productSalesCategoryId;
	private long productErpCategoryId;

	public long getProductSalesCategoryId() {
		return productSalesCategoryId;
	}

	public void setProductSalesCategoryId(long productSalesCategoryId) {
		this.productSalesCategoryId = productSalesCategoryId;
	}

	public long getProductErpCategoryId() {
		return productErpCategoryId;
	}

	public void setProductErpCategoryId(long productErpCategoryId) {
		this.productErpCategoryId = productErpCategoryId;
	}

}
