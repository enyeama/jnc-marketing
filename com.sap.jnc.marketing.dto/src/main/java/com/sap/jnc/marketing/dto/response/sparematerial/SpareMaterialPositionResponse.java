package com.sap.jnc.marketing.dto.response.sparematerial;

import com.sap.jnc.marketing.persistence.model.Position;

public class SpareMaterialPositionResponse {
	private Long id;
	private String externalId;
	private String name;
	
	public SpareMaterialPositionResponse(Position position) {
		this.setId(position.getId());
		this.setExternalId(position.getExternalId());
		this.setName(position.getName());
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
}
