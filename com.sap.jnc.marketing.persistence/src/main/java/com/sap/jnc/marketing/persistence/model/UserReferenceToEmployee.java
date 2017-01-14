package com.sap.jnc.marketing.persistence.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("EMPLOYEE")
public class UserReferenceToEmployee extends UserReference {

	private static final long serialVersionUID = 2259503240389929127L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "referenceExternalId")
	private EmployeeView employee;

	public EmployeeView getEmployee() {
		return this.employee;
	}

	public void setEmployee(EmployeeView employee) {
		this.employee = employee;
	}
}
