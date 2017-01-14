package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class ValidityPeriod implements Serializable {

	private static final long serialVersionUID = -8492789044473575326L;

	@Column(name = "validityPeriodFrom")
	@Temporal(TemporalType.DATE)
	private Calendar validFrom;

	@Column(name = "validityPeriodTo")
	@Temporal(TemporalType.DATE)
	private Calendar validTo;

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
