/**
 * 
 */
package com.sap.jnc.marketing.dto.response.ka;

import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.EmployeeView;

/**
 * @author Quansheng Liu I075496
 */
public class EmployeeNodeResponse {
	private long id;
	private String name;

	public EmployeeNodeResponse() {

	}

	public EmployeeNodeResponse(EmployeeView employee) {
		if (employee == null) {
			return;
		}
		this.setId(employee.getId());
		this.setName(employee.getName());
	}

	public EmployeeNodeResponse(Employee employee) {
		if (employee == null) {
			return;
		}
		this.setId(employee.getId());
		this.setName(employee.getName());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
