package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VerifiedBottleQuantity implements Serializable {

	private static final long serialVersionUID = -8492789044473575326L;

	@Column(name = "verifiedBottleQuantityValue")
	private BigDecimal value;

	@Column(name = "verifiedBottleQuantityUnit")
	private String unit;

	public BigDecimal getValue() {
		return this.value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
