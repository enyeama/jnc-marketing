package com.sap.jnc.marketing.dto.shared.kaorder;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.PositionView;

/**
 * @author Joel.Cheng I310645
 */
public class PositionInfo implements Serializable {

	private static final long serialVersionUID = 1137019834892676933L;

	private Long id;

	private String externalId;

	private String name;

	private Boolean isHead;

	public PositionInfo() {
	}

	public PositionInfo(Position position) {
		this.id = position.getId();
		this.externalId = position.getExternalId();
		this.name = position.getName();
		this.isHead = position.getIsHead();
	}

	public PositionInfo(PositionView position) {
		this.id = position.getId();
		this.externalId = position.getExternalId();
		this.name = position.getName();
		this.isHead = position.getIsHead();
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

	public Boolean getIsHead() {
		return isHead;
	}

	public void setIsHead(Boolean isHead) {
		this.isHead = isHead;
	}

}
