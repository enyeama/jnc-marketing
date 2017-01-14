package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.EmployeeView;

/**
 * @author I332242 Zhu Qiang
 */
public class EmployeeInfoResponse implements Serializable {

	private static final long serialVersionUID = 7782232760609473196L;

	private Long id;

	private String name;

	public EmployeeInfoResponse(EmployeeView employee) {
		if (null == employee) {
			return;
		}
		if (null != employee.getId()) {
			this.setId(employee.getId());
		}
		if (StringUtils.isNotBlank(employee.getName())) {
			this.setName(employee.getName());
		}
	}
	
	public EmployeeInfoResponse(Employee employee) {
		if (null == employee) {
			return;
		}
		if (null != employee.getId()) {
			this.setId(employee.getId());
		}
		if (StringUtils.isNotBlank(employee.getName())) {
			this.setName(employee.getName());
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
