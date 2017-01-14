/**
 * 
 */
package com.sap.jnc.marketing.dto.response.department;

import java.util.Calendar;

import com.sap.jnc.marketing.persistence.model.Department;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod;

/**
 * @author Quansheng Liu I075496
 */
public class DepartmentResponse {
	private Long id;
	private String externalId;
	private String name;
	private Calendar validFrom;
	private Calendar validTo;

	public DepartmentResponse() {

	}

	public DepartmentResponse(Department department) {
		if (department == null) {
			return;
		}
		this.setExternalId(department.getExternalId());
		this.setId(department.getId());
		this.setName(department.getName());
		ValidityPeriod validityPeriod = department.getValidityPeriod();
		this.setValidFrom(validityPeriod == null ? null : validityPeriod.getValidFrom());
		this.setValidTo(validityPeriod == null ? null : validityPeriod.getValidTo());
	}

	public DepartmentResponse(DepartmentView department) {
		if (department == null) {
			return;
		}
		this.setExternalId(department.getExternalId());
		this.setId(department.getId());
		this.setName(department.getName());
		ValidityPeriod validityPeriod = department.getValidityPeriod();
		this.setValidFrom(validityPeriod == null ? null : validityPeriod.getValidFrom());
		this.setValidTo(validityPeriod == null ? null : validityPeriod.getValidTo());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
