/**
 * 
 */
package com.sap.jnc.marketing.dto.response;

/**
 * @author Quansheng Liu I075496
 */
public class ProductCategoryRelationResponse {
	private long productSalesCategoryId;
	private String productSalesCategoryName;
	private long productErpCategoryId;
	private long productCategoryId;
	private String productCategoryName;

	public long getProductSalesCategoryId() {
		return productSalesCategoryId;
	}

	public void setProductSalesCategoryId(long productSalesCategoryId) {
		this.productSalesCategoryId = productSalesCategoryId;
	}

	public String getProductSalesCategoryName() {
		return productSalesCategoryName;
	}

	public void setProductSalesCategoryName(String productSalesCategoryName) {
		this.productSalesCategoryName = productSalesCategoryName;
	}

	public long getProductErpCategoryId() {
		return productErpCategoryId;
	}

	public void setProductErpCategoryId(long productErpCategoryId) {
		this.productErpCategoryId = productErpCategoryId;
	}

	public long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

}
