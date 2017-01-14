/**
 * 
 */
package com.sap.jnc.marketing.dto.response.ka;

import com.sap.jnc.marketing.persistence.model.Region;

/**
 * @author Quansheng Liu I075496
 */
public class RegionNodeResponse {
	private Long regionId;
	private String cityId;
	private String cityName;
	private String countyId;
	private String countyName;
	private String provinceId;
	private String provinceName;
	private String regionFullName;

	public RegionNodeResponse() {

	}

	public RegionNodeResponse(Region region) {
		if (region == null) {
			return;
		}
		this.setRegionFullName(region.getRegionFullName());
		this.setCityId(region.getCityId());
		this.setCityName(region.getCityName());
		this.setCountyId(region.getCountyId());
		this.setCountyName(region.getCountyName());
		this.setProvinceId(region.getProvinceId());
		this.setProvinceName(region.getProvinceName());
		this.setRegionId(region.getId());
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
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

	public String getRegionFullName() {
		return regionFullName;
	}

	public void setRegionFullName(String regionFullName) {
		this.regionFullName = regionFullName;
	}

}
