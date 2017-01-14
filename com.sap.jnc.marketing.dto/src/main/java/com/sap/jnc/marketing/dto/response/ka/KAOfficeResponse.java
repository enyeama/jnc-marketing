/**
 * 
 */
package com.sap.jnc.marketing.dto.response.ka;

import com.sap.jnc.marketing.persistence.model.DepartmentView;

/**
 * @author Quansheng Liu I075496
 */
public class KAOfficeResponse {
	private Long id;
	private String name;

	public KAOfficeResponse() {

	}

	public KAOfficeResponse(DepartmentView department) {
		if (department == null) {
			return;
		}
		this.setId(department.getId());
		this.setName(department.getName());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
