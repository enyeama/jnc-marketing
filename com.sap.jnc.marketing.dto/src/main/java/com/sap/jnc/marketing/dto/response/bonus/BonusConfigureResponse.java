package com.sap.jnc.marketing.dto.response.bonus;

import java.io.Serializable;

public class BonusConfigureResponse implements Serializable {

	private static final long serialVersionUID = 2488752765242771460L;

	/** categoryName */
	private String productId;
	/** 计划均化金额 */
	private String averageAmount;
	/** 均值误差金额 */
	private String varianceAmount;
	/** 均值生效时间 */
	private String validFrom;
	/** 均值失效时间 */
	private String validTo;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAverageAmount() {
		return averageAmount;
	}

	public void setAverageAmount(String averageAmount) {
		this.averageAmount = averageAmount;
	}

	public String getVarianceAmount() {
		return varianceAmount;
	}

	public void setVarianceAmount(String varianceAmount) {
		this.varianceAmount = varianceAmount;
	}

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

}
