package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.Department;
import com.sap.jnc.marketing.persistence.model.DepartmentView;

/**
 * @author I332242 Zhu Qiang
 */
public class OfficeInfoResponse implements Serializable{
	
	private static final long serialVersionUID = -6700475995415946176L;

	private Long id;
	
	private String name;
	
	public OfficeInfoResponse (Department department) {
		if (department == null) {
			return;
		}
		if (department.getId() != null) {
			this.id = department.getId();
		}
		if (department.getName() != null) {
			this.name = department.getName();
		}
		
	}
	
	public OfficeInfoResponse (DepartmentView departmentView) {
		if (departmentView == null) {
			return;
		}
		if (departmentView.getId() != null) {
			this.id = departmentView.getId();
		}
		if (departmentView.getName() != null) {
			this.name = departmentView.getName();
		}
		
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
