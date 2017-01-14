package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.sap.jnc.marketing.persistence.model.Region;

/**
 * @author C5231393 Xu Xiaolei
 */
public class RegionResponse implements Serializable {

	private static final long serialVersionUID = 1361474609906230623L;

	private String countyId;

	private String countyName;

	private String cityId;

	private String cityName;

	private String provinceId;

	private String provinceName;

	private int level;

	public RegionResponse(Region region) {
		if (region == null) {
			return;
		}
		if (!StringUtils.isEmpty(region.getCountyId())) {
			this.setCountyId(region.getCountyId());
		}
		if (!StringUtils.isEmpty(region.getCountyName())) {
			this.setCountyName(region.getCountyName());
		}
		if (!StringUtils.isEmpty(region.getCityId())) {
			this.setCityId(region.getCityId());
		}
		if (!StringUtils.isEmpty(region.getCityName())) {
			this.setCityName(region.getCityName());
		}
		if (!StringUtils.isEmpty(region.getProvinceId())) {
			this.setProvinceId(region.getProvinceId());
		}
		if (!StringUtils.isEmpty(region.getProvinceName())) {
			this.setProvinceName(region.getProvinceName());
		}
		if (region.getLevel() != null) {
			this.setLevel(region.getLevel());
		}
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
