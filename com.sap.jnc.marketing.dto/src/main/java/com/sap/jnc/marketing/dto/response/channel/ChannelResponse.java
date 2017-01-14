/**
 * 
 */
package com.sap.jnc.marketing.dto.response.channel;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.Channel;

/**
 * @author I075496
 */
public class ChannelResponse implements Serializable {

	private static final long serialVersionUID = -398737656240757903L;

	private Long id;

	private String name;

	private String description;

	public ChannelResponse() {

	}

	public ChannelResponse(Channel channel) {
		if (channel == null) {
			return;
		}
		this.setId(channel.getId());
		this.setName(channel.getName());
		this.setDescription(channel.getDescription());
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
