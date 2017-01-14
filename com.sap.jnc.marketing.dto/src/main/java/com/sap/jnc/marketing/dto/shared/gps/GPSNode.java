/**
 * 
 */
package com.sap.jnc.marketing.dto.shared.gps;

import java.math.BigDecimal;

import com.sap.jnc.marketing.persistence.model.GPS;

/**
 * @author Quansheng Liu I075496
 */
public class GPSNode {
	private BigDecimal longitude;

	private BigDecimal latitude;

	private String accuracy;

	private String province;

	private String city;

	private String county;

	private String description;

	public GPSNode() {

	}

	public GPSNode(GPS gps) {
		if (gps == null) {
			return;
		}
		this.setAccuracy(gps.getAccuracy());
		this.setCity(gps.getCity());
		this.setCounty(gps.getCounty());
		this.setDescription(gps.getDescription());
		this.setLatitude(gps.getLatitude());
		this.setLongitude(gps.getLongitude());
		this.setProvince(gps.getProvince());

	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
