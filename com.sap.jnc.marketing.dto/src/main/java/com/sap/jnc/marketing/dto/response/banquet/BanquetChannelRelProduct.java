package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author I332242 Zhu Qiang
 */
public class BanquetChannelRelProduct implements Serializable{

	private static final long serialVersionUID = 4927780534136089491L;

	private Set<ProductInfoResponse> productInfoResponse;
	
	private Set<ChannelInfoResponse> channel;

	public Set<ProductInfoResponse> getProductInfoResponse() {
		if (null == productInfoResponse) {
			productInfoResponse = new HashSet<ProductInfoResponse>();
		}
		return productInfoResponse;
	}

	public void setProductInfoResponse(Set<ProductInfoResponse> productInfoResponse) {
		if (null != productInfoResponse) {
			this.productInfoResponse = productInfoResponse;
		}
	}

	public Set<ChannelInfoResponse> getChannel() {
		if (null == channel) {
			channel = new HashSet<ChannelInfoResponse>();
		}
		return channel;
	}

	public void setChannel(Set<ChannelInfoResponse> channel) {
		if (null != channel) {
			this.channel = channel;
		}
	}
}
