package com.sap.jnc.marketing.dto.respose.mainten;

import com.sap.jnc.marketing.persistence.model.Dealer;

/**
 * @author Maggie Liu
 */
public class MaintenDealerResponse {
	private String id;
	private String name = "";

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

	public MaintenDealerResponse(Dealer d) {
		if (d == null) {
			return;
		}
		if (d.getExternalId() != null) {
			this.id = d.getExternalId();
		}
		if (d.getName() != null) {
			this.name = d.getName();
		}
	}
}
