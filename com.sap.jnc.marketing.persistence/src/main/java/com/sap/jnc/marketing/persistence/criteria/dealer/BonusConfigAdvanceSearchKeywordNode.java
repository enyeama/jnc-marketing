package com.sap.jnc.marketing.persistence.criteria.dealer;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;

/**
 * 红包支付配置查询关键字
 * 
 * @author I327119
 */
public class BonusConfigAdvanceSearchKeywordNode implements SearchKeywordNode {

	private static final long serialVersionUID = 2615071678256481131L;

	/**
	 * 生效日期
	 */
	private String validFrom;

	/**
	 * 失效日期
	 */
	private String validTo;

	/**
	 * "物料四级分类"的分类Id
	 */
	private String categoryId;

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

}
