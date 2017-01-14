package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GPS implements Serializable {

	private static final long serialVersionUID = -8492789044473575326L;

	@Column(name = "gpsLongitude", precision = 34, scale = 10)
	private BigDecimal longitude;

	@Column(name = "gpsLatitude", precision = 34, scale = 10)
	private BigDecimal latitude;

	@Column(name = "gpsAccuracy")
	private String accuracy;

	@Column(name = "gpsProvince")
	private String province;

	@Column(name = "gpsCity")
	private String city;

	@Column(name = "gpsCounty")
	private String county;

	@Column(name = "gpsDescription")
	private String description;

	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getAccuracy() {
		return this.accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
