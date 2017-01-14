package com.sap.jnc.marketing.dto.request.ka;

import com.sap.jnc.marketing.persistence.enumeration.BranchLevel;

/**
 * Created by Quansheng Liu i075496
 */
public class KAImportRequest {
	private Long kaOfficeId;
	private String branchName;
	private String address;
	private String storeNumber;
	private String kaSystemNumber;
	private String kaSystemName;
	private Long accountManagerPositionId;
	private Long specialistPositionId;
	private Long maintainerPositionId;
	private String country;
	private String province;
	private String city;
	private Long regionId;
	private String businessStatus;
	private String channelNumber;
	private BranchLevel branchLevel;
	private String systemPropertyNumber;
	private String systemPropertyName;

	public Long getKaOfficeId() {
		return kaOfficeId;
	}

	public void setKaOfficeId(Long kaOfficeId) {
		this.kaOfficeId = kaOfficeId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getKaSystemNumber() {
		return kaSystemNumber;
	}

	public void setKaSystemNumber(String kaSystemNumber) {
		this.kaSystemNumber = kaSystemNumber;
	}

	public String getKaSystemName() {
		return kaSystemName;
	}

	public void setKaSystemName(String kaSystemName) {
		this.kaSystemName = kaSystemName;
	}

	public Long getAccountManagerPositionId() {
		return accountManagerPositionId;
	}

	public void setAccountManagerPositionId(Long accountManagerPositionId) {
		this.accountManagerPositionId = accountManagerPositionId;
	}

	public Long getSpecialistPositionId() {
		return specialistPositionId;
	}

	public void setSpecialistPositionId(Long specialistPositionId) {
		this.specialistPositionId = specialistPositionId;
	}

	public Long getMaintainerPositionId() {
		return maintainerPositionId;
	}

	public void setMaintainerPositionId(Long maintainerPositionId) {
		this.maintainerPositionId = maintainerPositionId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	public BranchLevel getBranchLevel() {
		return branchLevel;
	}

	public void setBranchLevel(BranchLevel branchLevel) {
		this.branchLevel = branchLevel;
	}

	public String getSystemPropertyNumber() {
		return systemPropertyNumber;
	}

	public void setSystemPropertyNumber(String systemPropertyNumber) {
		this.systemPropertyNumber = systemPropertyNumber;
	}

	public String getSystemPropertyName() {
		return systemPropertyName;
	}

	public void setSystemPropertyName(String systemPropertyName) {
		this.systemPropertyName = systemPropertyName;
	}

}
