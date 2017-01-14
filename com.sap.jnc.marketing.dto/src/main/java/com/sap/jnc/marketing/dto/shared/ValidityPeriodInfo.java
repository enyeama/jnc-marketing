package com.sap.jnc.marketing.dto.shared;

import java.io.Serializable;
import java.util.Calendar;

import com.sap.jnc.marketing.persistence.model.ValidityPeriod;

public class ValidityPeriodInfo implements Serializable {

	private static final long serialVersionUID = -8492789044473575326L;

	private Calendar validFrom;

	private Calendar validTo;

	public ValidityPeriodInfo() {
	}

	public ValidityPeriodInfo(ValidityPeriod validityPeriod) {
		this.setValidFrom(validityPeriod.getValidFrom());
		this.setValidTo(validityPeriod.getValidTo());
	}

	public Calendar getValidFrom() {
		return this.validFrom;
	}

	public void setValidFrom(Calendar validFrom) {
		this.validFrom = validFrom;
	}

	public Calendar getValidTo() {
		return this.validTo;
	}

	public void setValidTo(Calendar validTo) {
		this.validTo = validTo;
	}
}
