package com.sap.jnc.marketing.dto.response.wechat.ka;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.Terminal;

public class KaTerminalsResponse implements Serializable {

	private static final long serialVersionUID = -2399062011861011303L;

	private Long id;

	private String type;

	private String title;

	private String branchLevel;

	private String channel;

	public KaTerminalsResponse(Terminal entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		if (entity.getType() != null) {
			this.type = entity.getType().getLabel();
		}
		if (entity.getBranchLevel() != null) {
			this.branchLevel = entity.getBranchLevel().getLabel();
		}
		if (entity.getChannel() != null) {
			this.channel = entity.getChannel().getName();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBranchLevel() {
		return branchLevel;
	}

	public void setBranchLevel(String branchLevel) {
		this.branchLevel = branchLevel;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
