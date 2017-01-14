package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.sap.jnc.marketing.persistence.model.Channel;

/**
 * @author I332242 Zhu Qiang
 */
public class ChannelInfoResponse implements Serializable {

	private static final long serialVersionUID = 4795349542462527377L;

	private Long id;

	private String channelId;

	private String channelName;
	
	public ChannelInfoResponse(Channel channel) {
		if (null == channel) {
			return;
		}
		if (null != channel.getId()) {
			this.setId(channel.getId());
		}
		if (StringUtils.isNotBlank(channel.getName())) {
			this.setChannelName(channel.getName());
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public boolean equals(Object obj){
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj instanceof ChannelInfoResponse) {
			ChannelInfoResponse other = (ChannelInfoResponse) obj;
			return other.id.equals(this.id) ;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return this.id.hashCode();
		
	}
}
