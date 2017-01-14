package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.sap.jnc.marketing.persistence.model.PlanQuantity;

/**
 * @author I332242 Zhu Qiang
 */
public class PlanQuantityInfoResponse implements Serializable {

	private static final long serialVersionUID = -7108168897398438662L;

	private BigDecimal value;

	private String unit;
	
	public PlanQuantityInfoResponse (PlanQuantity planQuantity) {
		if (null == planQuantity) {
			return;
		}
		if (null != planQuantity.getValue()) {
			this.value = planQuantity.getValue();
		}
		if (StringUtils.isNotBlank(planQuantity.getUnit())) {
			this.unit = planQuantity.getUnit();
		}
	}

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
