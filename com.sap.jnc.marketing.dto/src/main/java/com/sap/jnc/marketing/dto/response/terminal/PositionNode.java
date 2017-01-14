/**
 * 
 */
package com.sap.jnc.marketing.dto.response.terminal;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.PositionView;

/**
 * @author Quansheng Liu I075496
 */
public class PositionNode implements Serializable {

	private static final long serialVersionUID = 5169552545147172693L;

	private Long id;
	private String externalId;
	private String name;

	public PositionNode() {

	}

	public PositionNode(PositionView positionView) {
		if (positionView == null) {
			return;
		}
		this.setExternalId(positionView.getExternalId());
		this.setId(positionView.getId());
		this.setName(positionView.getName());
	}

	public PositionNode(Position position) {
		if (position == null) {
			return;
		}
		this.setExternalId(position.getExternalId());
		this.setId(position.getId());
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
