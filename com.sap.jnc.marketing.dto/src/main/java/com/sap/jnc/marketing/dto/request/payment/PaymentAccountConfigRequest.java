package com.sap.jnc.marketing.dto.request.payment;

import java.io.Serializable;

public class PaymentAccountConfigRequest implements Serializable {

	private static final long serialVersionUID = -7100521370919695505L;

	/** 默认支付账户ID */
	private Long defaultAccountId;

	/** 默认支付账户公众账号ID */
	private String defaultAccountOpenId;
	/** 生效日期 */
	private String validFrom;
	/** 失效日期 */
	private String validTo;

	public Long getDefaultAccountId() {
		return defaultAccountId;
	}

	public void setDefaultAccountId(Long defaultAccountId) {
		this.defaultAccountId = defaultAccountId;
	}

	public String getDefaultAccountOpenId() {
		return defaultAccountOpenId;
	}

	public void setDefaultAccountOpenId(String defaultAccountOpenId) {
		this.defaultAccountOpenId = defaultAccountOpenId;
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
