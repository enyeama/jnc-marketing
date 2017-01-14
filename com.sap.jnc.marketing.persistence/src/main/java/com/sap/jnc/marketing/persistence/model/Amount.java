package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Amount implements Serializable {

	private static final long serialVersionUID = -8492789044473575326L;

	@Column(name = "amountValue", precision = 34, scale = 2)
	private BigDecimal value;

	@Column(name = "amountCurrency")
	private String currency;

	public BigDecimal getValue() {
		return this.value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
