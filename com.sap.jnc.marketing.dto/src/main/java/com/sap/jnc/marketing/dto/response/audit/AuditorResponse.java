package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sap.jnc.marketing.persistence.model.EmployeeView;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditorResponse implements Serializable {

	private static final long serialVersionUID = 2356308747434022911L;

	private Long id;

	private String name;

	private Long positionId;

	private String positionName;

	public AuditorResponse(EmployeeView employeeView) {
		if (employeeView == null) {
			return;
		}
		if (employeeView.getId() != null) {
			this.setId(employeeView.getId());
		}
		if (!StringUtils.isEmpty(employeeView.getName())) {
			this.setName(employeeView.getName());
		}
		if (!CollectionUtils.isEmpty(employeeView.getPositions())) {
			this.setPositionId(employeeView.getPositions().get(0).getId());
			this.setPositionName(employeeView.getPositions().get(0).getName());
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

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

}
