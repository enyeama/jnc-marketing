package com.sap.jnc.marketing.dto.response.csv;

import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment;

/**
 * @author Zero
 * @anthor Vodka
 */
public class ProvinceManagerOfficeResponse {
	private Long id;
	private String provManagerId;
	private String officeId;

	public String getProvManagerId() {
		return provManagerId;
	}

	public void setProvManagerId(String provManagerId) {
		this.provManagerId = provManagerId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public ProvinceManagerOfficeResponse(ProvinceManagerOfficeAssignment pmoa) {
		this.setId(pmoa.getId());
		this.setProvManagerId(pmoa.getProvinceManager().getExternalId());
		this.setOfficeId(pmoa.getProvinceManager().getDepartment().getExternalId());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
