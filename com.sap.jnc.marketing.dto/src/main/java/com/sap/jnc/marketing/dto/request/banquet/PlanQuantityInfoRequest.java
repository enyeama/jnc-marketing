package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author I332242 Zhu Qiang
 */
public class PlanQuantityInfoRequest implements Serializable {

	private static final long serialVersionUID = 6294201489919854667L;

	private BigDecimal value;

	private String unit;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
