/**
 * 
 */
package com.sap.jnc.marketing.dto.request.terminalorder;

import java.io.Serializable;

/**
 * @author Quansheng Liu I075496
 */
public class DealerLeaderSearchRequest implements Serializable {

	private static final long serialVersionUID = 4897779128882489402L;
	private Long cityManagerPositionId;
	private String cityManagerPositionExternalId;
	private Long productId;
	private Long regionId;
	private Long channelId;
	private Long terminalId;

	public Long getCityManagerPositionId() {
		return cityManagerPositionId;
	}

	public void setCityManagerPositionId(Long cityManagerPositionId) {
		this.cityManagerPositionId = cityManagerPositionId;
	}

	public String getCityManagerPositionExternalId() {
		return cityManagerPositionExternalId;
	}

	public void setCityManagerPositionExternalId(String cityManagerPositionExternalId) {
		this.cityManagerPositionExternalId = cityManagerPositionExternalId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

}
