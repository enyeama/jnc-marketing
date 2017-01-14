package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TotalVerifiedQuantity implements Serializable {

	private static final long serialVersionUID = 8051469015297596083L;

	@Column(name = "totalVerifiedQuantityValue")
	private BigDecimal value;

	@Column(name = "totalVerifiedQuantityUnit")
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
