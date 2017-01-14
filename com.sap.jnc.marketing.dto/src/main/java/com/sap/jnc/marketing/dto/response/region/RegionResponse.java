/**
 * 
 */
package com.sap.jnc.marketing.dto.response.region;

import java.io.Serializable;
import java.util.Calendar;

import com.sap.jnc.marketing.persistence.model.Region;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod;

/**
 * @author Quansheng Liu I075496
 */
public class RegionResponse implements Serializable {

	private static final long serialVersionUID = -6630322817341986794L;

	private Long id;

	private String provinceId;

	private String provinceName;

	private String cityId;

	private String cityName;

	private String countyId;

	private String countyName;

	private Calendar validFrom;

	private Calendar validTo;

	private Integer level;

	private String regionFullName;

	private Boolean isValid;

	public RegionResponse() {

	}

	public RegionResponse(Region region) {
		if (region == null) {
			return;
		}
		this.setCityId(region.getCityId());
		this.setCityName(region.getCityName());
		this.setCountyId(region.getCountyId());
		this.setCountyName(region.getCountyName());
		this.setId(region.getId());
		this.setIsValid(region.getIsValid());
		this.setLevel(region.getLevel());
		this.setProvinceId(region.getProvinceId());
		this.setProvinceName(region.getProvinceName());
		this.setRegionFullName(region.getRegionFullName());
		ValidityPeriod validityPeriod = region.getValidityPeriod();
		if (validityPeriod != null) {
			this.setValidFrom(validityPeriod.getValidFrom());
			this.setValidTo(validityPeriod.getValidTo());
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public Calendar getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Calendar validFrom) {
		this.validFrom = validFrom;
	}

	public Calendar getValidTo() {
		return validTo;
	}

	public void setValidTo(Calendar validTo) {
		this.validTo = validTo;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getRegionFullName() {
		return regionFullName;
	}

	public void setRegionFullName(String regionFullName) {
		this.regionFullName = regionFullName;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

}
