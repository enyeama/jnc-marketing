/**
 * 
 */
package com.sap.jnc.marketing.dto.response.ka;

import com.sap.jnc.marketing.persistence.model.PositionView;

/**
 * @author Quansheng Liu I075496
 */
public class PositionNodeResponse {
	private long id;
	private String name;

	public PositionNodeResponse() {

	}

	public PositionNodeResponse(PositionView positionView) {
		if (positionView == null) {
			return;
		}
		this.setId(positionView.getId());
		this.setName(positionView.getName());
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
