package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditOfficeAssignRequest implements Serializable {

	private static final long serialVersionUID = -1952103773203795114L;

	private String officeExternalId;

	private String positionExternalId;

	private Calendar validFrom;

	private Calendar validTo;

	public String getOfficeExternalId() {
		return officeExternalId;
	}

	public void setOfficeExternalId(String officeExternalId) {
		this.officeExternalId = officeExternalId;
	}

	public String getPositionExternalId() {
		return positionExternalId;
	}

	public void setPositionExternalId(String positionExternalId) {
		this.positionExternalId = positionExternalId;
	}

	public Calendar getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Calendar validFrom) {
		this.validFrom = validFrom;
	}

	public Calendar getValidTo() {
		return validTo;
	}

	public void setValidTo(Calendar validTo) {
		this.validTo = validTo;
	}

}
