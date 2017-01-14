package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DispatchAmount implements Serializable {

	private static final long serialVersionUID = 2164989838597083520L;

	@Column(name = "dispatchAmountValue")
	private BigDecimal value;

	@Column(name = "dispatchAmountCurrency")
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
