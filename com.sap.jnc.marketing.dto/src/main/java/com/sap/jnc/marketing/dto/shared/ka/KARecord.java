/**
 * 
 */
package com.sap.jnc.marketing.dto.shared.ka;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.BranchLevel;
import com.sap.jnc.marketing.persistence.enumeration.PromoterType;

/**
 * @author Quansheng Liu I075496
 */
public class KARecord implements Serializable {

	private static final long serialVersionUID = -5539696307225501939L;
	private Long id;
	private Long kaOfficeId;
	private String kaOfficeDescription;
	private String branchName;
	private String address;
	private String storeNumber;
	private String kaSystemNumber;
	private String kaSystemName;
	private String kaProducts;
	private Long accountManagerPositionId;
	private String accountManagerPositionDescription;
	private Long accountManagerEmployeeId;
	private String accountManagerEmployeeName;
	private Long specialistPositionId;
	private String specialistPositionDescription;
	private Long specialistEmployeeId;
	private String specialistEmployeeName;
	private Long maintainerPositionId;
	private String maintainerPositionDescription;
	private Long maintainerEmployeeId;
	private String maintainerEmployeeName;
	private String province;
	private String city;
	private String county;
	private String countyId;
	private Long regionId;
	private Long channelId;
	private String businessStatus;
	private BranchLevel branchLevel;
	private String systemPropertyNumber;
	private String systemPropertyName;
	private Long keyUserContactId;
	private String keyUserContactName;
	private String keyUserContactPhone;
	private String keyUserContactWechat;
	private Long purchaseContactId;
	private String purchaseContactName;
	private String purchaseContactPhone;
	private String purchaseContactWechat;
	private Long kaPurchaseContactId;
	private String kaPurchaseContactName;
	private String kaPurchaseContactPhone;
	private String kaPurchaseContactWechat;
	private Long promoterContactId;
	private String promoterContactName;
	private String promoterContactPhone;
	private String promoterContactWechat;
	private PromoterType promoterType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getKaOfficeId() {
		return kaOfficeId;
	}

	public void setKaOfficeId(Long kaOfficeId) {
		this.kaOfficeId = kaOfficeId;
	}

	public String getKaOfficeDescription() {
		return kaOfficeDescription;
	}

	public void setKaOfficeDescription(String kaOfficeDescription) {
		this.kaOfficeDescription = kaOfficeDescription;
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

	public String getKaProducts() {
		return kaProducts;
	}

	public void setKaProducts(String kaProducts) {
		this.kaProducts = kaProducts;
	}

	public Long getAccountManagerPositionId() {
		return accountManagerPositionId;
	}

	public void setAccountManagerPositionId(Long accountManagerPositionId) {
		this.accountManagerPositionId = accountManagerPositionId;
	}

	public String getAccountManagerPositionDescription() {
		return accountManagerPositionDescription;
	}

	public void setAccountManagerPositionDescription(String accountManagerPositionDescription) {
		this.accountManagerPositionDescription = accountManagerPositionDescription;
	}

	public Long getAccountManagerEmployeeId() {
		return accountManagerEmployeeId;
	}

	public void setAccountManagerEmployeeId(Long accountManagerEmployeeId) {
		this.accountManagerEmployeeId = accountManagerEmployeeId;
	}

	public String getAccountManagerEmployeeName() {
		return accountManagerEmployeeName;
	}

	public void setAccountManagerEmployeeName(String accountManagerEmployeeName) {
		this.accountManagerEmployeeName = accountManagerEmployeeName;
	}

	public Long getSpecialistPositionId() {
		return specialistPositionId;
	}

	public void setSpecialistPositionId(Long specialistPositionId) {
		this.specialistPositionId = specialistPositionId;
	}

	public String getSpecialistPositionDescription() {
		return specialistPositionDescription;
	}

	public void setSpecialistPositionDescription(String specialistPositionDescription) {
		this.specialistPositionDescription = specialistPositionDescription;
	}

	public Long getSpecialistEmployeeId() {
		return specialistEmployeeId;
	}

	public void setSpecialistEmployeeId(Long specialistEmployeeId) {
		this.specialistEmployeeId = specialistEmployeeId;
	}

	public String getSpecialistEmployeeName() {
		return specialistEmployeeName;
	}

	public void setSpecialistEmployeeName(String specialistEmployeeName) {
		this.specialistEmployeeName = specialistEmployeeName;
	}

	public Long getMaintainerPositionId() {
		return maintainerPositionId;
	}

	public void setMaintainerPositionId(Long maintainerPositionId) {
		this.maintainerPositionId = maintainerPositionId;
	}

	public String getMaintainerPositionDescription() {
		return maintainerPositionDescription;
	}

	public void setMaintainerPositionDescription(String maintainerPositionDescription) {
		this.maintainerPositionDescription = maintainerPositionDescription;
	}

	public Long getMaintainerEmployeeId() {
		return maintainerEmployeeId;
	}

	public void setMaintainerEmployeeId(Long maintainerEmployeeId) {
		this.maintainerEmployeeId = maintainerEmployeeId;
	}

	public String getMaintainerEmployeeName() {
		return maintainerEmployeeName;
	}

	public void setMaintainerEmployeeName(String maintainerEmployeeName) {
		this.maintainerEmployeeName = maintainerEmployeeName;
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

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
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

	public Long getKeyUserContactId() {
		return keyUserContactId;
	}

	public void setKeyUserContactId(Long keyUserContactId) {
		this.keyUserContactId = keyUserContactId;
	}

	public String getKeyUserContactName() {
		return keyUserContactName;
	}

	public void setKeyUserContactName(String keyUserContactName) {
		this.keyUserContactName = keyUserContactName;
	}

	public String getKeyUserContactPhone() {
		return keyUserContactPhone;
	}

	public void setKeyUserContactPhone(String keyUserContactPhone) {
		this.keyUserContactPhone = keyUserContactPhone;
	}

	public String getKeyUserContactWechat() {
		return keyUserContactWechat;
	}

	public void setKeyUserContactWechat(String keyUserContactWechat) {
		this.keyUserContactWechat = keyUserContactWechat;
	}

	public Long getPurchaseContactId() {
		return purchaseContactId;
	}

	public void setPurchaseContactId(Long purchaseContactId) {
		this.purchaseContactId = purchaseContactId;
	}

	public String getPurchaseContactName() {
		return purchaseContactName;
	}

	public void setPurchaseContactName(String purchaseContactName) {
		this.purchaseContactName = purchaseContactName;
	}

	public String getPurchaseContactPhone() {
		return purchaseContactPhone;
	}

	public void setPurchaseContactPhone(String purchaseContactPhone) {
		this.purchaseContactPhone = purchaseContactPhone;
	}

	public String getPurchaseContactWechat() {
		return purchaseContactWechat;
	}

	public void setPurchaseContactWechat(String purchaseContactWechat) {
		this.purchaseContactWechat = purchaseContactWechat;
	}

	public Long getKaPurchaseContactId() {
		return kaPurchaseContactId;
	}

	public void setKaPurchaseContactId(Long kaPurchaseContactId) {
		this.kaPurchaseContactId = kaPurchaseContactId;
	}

	public String getKaPurchaseContactName() {
		return kaPurchaseContactName;
	}

	public void setKaPurchaseContactName(String kaPurchaseContactName) {
		this.kaPurchaseContactName = kaPurchaseContactName;
	}

	public String getKaPurchaseContactPhone() {
		return kaPurchaseContactPhone;
	}

	public void setKaPurchaseContactPhone(String kaPurchaseContactPhone) {
		this.kaPurchaseContactPhone = kaPurchaseContactPhone;
	}

	public String getKaPurchaseContactWechat() {
		return kaPurchaseContactWechat;
	}

	public void setKaPurchaseContactWechat(String kaPurchaseContactWechat) {
		this.kaPurchaseContactWechat = kaPurchaseContactWechat;
	}

	public Long getPromoterContactId() {
		return promoterContactId;
	}

	public void setPromoterContactId(Long promoterContactId) {
		this.promoterContactId = promoterContactId;
	}

	public String getPromoterContactName() {
		return promoterContactName;
	}

	public void setPromoterContactName(String promoterContactName) {
		this.promoterContactName = promoterContactName;
	}

	public String getPromoterContactPhone() {
		return promoterContactPhone;
	}

	public void setPromoterContactPhone(String promoterContactPhone) {
		this.promoterContactPhone = promoterContactPhone;
	}

	public String getPromoterContactWechat() {
		return promoterContactWechat;
	}

	public void setPromoterContactWechat(String promoterContactWechat) {
		this.promoterContactWechat = promoterContactWechat;
	}

	public PromoterType getPromoterType() {
		return promoterType;
	}

	public void setPromoterType(PromoterType promoterType) {
		this.promoterType = promoterType;
	}

}
