package com.sap.jnc.marketing.dto.response.dealer;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.EmployeeView;

public class DealerLeaderResponse implements Serializable {

	private static final long serialVersionUID = -6090688187637778499L;

	private String id;
	
	private String name;
	
	public DealerLeaderResponse(EmployeeView entity) {
		this.id = entity.getId().toString();
		this.name = entity.getName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
