package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditOfficeUnassignRequest implements Serializable {

	private static final long serialVersionUID = 3797605608690388487L;

	private Long id;

	private Calendar validTo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getValidTo() {
		return validTo;
	}

	public void setValidTo(Calendar validTo) {
		this.validTo = validTo;
	}

}
