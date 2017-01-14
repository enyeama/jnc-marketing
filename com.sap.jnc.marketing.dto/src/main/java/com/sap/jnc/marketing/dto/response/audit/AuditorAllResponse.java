package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.sap.jnc.marketing.persistence.model.EmployeeView;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditorAllResponse implements Serializable {

	private static final long serialVersionUID = -2064012718555844369L;

	private Long id;

	private String name;

	private String positionExternalId;

	private String positionName;

	public AuditorAllResponse(EmployeeView employeeView) {
		if (employeeView == null) {
			return;
		}
		if (employeeView.getId() != null) {
			this.setId(employeeView.getId());
		}
		if (!StringUtils.isEmpty(employeeView.getName())) {
			this.setName(employeeView.getName());
		}
		if (employeeView.getPosition() != null && !StringUtils.isEmpty(employeeView.getPosition().getExternalId())) {
			this.setPositionExternalId(employeeView.getPosition().getExternalId());
		}
		if (employeeView.getPosition() != null && !StringUtils.isEmpty(employeeView.getPosition().getName())) {
			this.setPositionName(employeeView.getPosition().getName());
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

	public String getPositionExternalId() {
		return positionExternalId;
	}

	public void setPositionExternalId(String positionExternalId) {
		this.positionExternalId = positionExternalId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

}
