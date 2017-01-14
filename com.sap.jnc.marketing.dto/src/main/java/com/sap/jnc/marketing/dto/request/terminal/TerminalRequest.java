package com.sap.jnc.marketing.dto.request.terminal;

import java.math.BigDecimal;

import com.sap.jnc.marketing.persistence.enumeration.*;
import org.springframework.util.StringUtils;

import com.sap.jnc.marketing.persistence.model.Terminal;

/**
 * Created by guokai on 16/6/15.
 */
public class TerminalRequest {

	private Long id;

	private Integer type;

	private String branchLevel;

	private String branchType;

	private String title;

	private String pictureURL;

	private String creationClerk;

	private String description;

	private String countyId;

	private String branchName;

	private String address;

	private String businessLicenseName;

	private String warehouseAddress;

	private String registrantName;

	private String phone;

	private String cashPersonOpenId;

	private String licensePictureURL;

	private Integer inWholeSaleMarket;

	private String picture;

	private String licensePicture;

	private String fullAddress;

	private String countryCode;

	private String status;

	private String kaType;

	private String promoterType;

	private String purchaserType;

	private Boolean isRefundAllowed;

	private Long keyUserId;

	private String keyUserName;

	private String keyUserPhone;

	private String keyUserWechat;

	private Long purchaserId;

	private String purchaserName;

	private String purchaserPhone;

	private String purchaserWechat;

	private Long kaPurchaserId;

	private String kaPurchaserName;

	private String kaPurchaserPhone;

	private String kaPurchaserWechat;

	private Long supervisorId;

	private String supervisorName;

	private String supervisorPhone;

	private String supervisorWechat;

	private Long businessId;

	private String businessName;

	private String businessPhone;

	private String businessWechat;

	private Long promoterId;

	private String promoterName;

	private String promoterPhone;

	private String promoterWechat;

	private Long cashPersonId;

	private String cashPersonName;

	private String cashPersonPhone;

	private String cashPersonWechat;

	private String accuracy;

	private BigDecimal longitude;

	private BigDecimal latitude;

	private String province;

	private String city;

	private String country;

	private String gpsDescription;

	private String keyUserType;

	private String keyUserPosition;

	private String organizationCode;

	private Integer isBanquetHotel;

	public Terminal toTerminal(Terminal obj) {
		obj.setId(id == null ? obj.getId() : id);
		obj.setType(type == null ? obj.getType() : TerminalType.valueOf(type));
		obj.setStatus(status == null ? obj.getStatus() : TerminalStatus.labelOf(status));
		obj.setBranchLevel(branchLevel == null ? obj.getBranchLevel() : BranchLevel.labelOf(branchLevel));
		obj.setBranchType(branchType == null ? obj.getBranchType() : BranchType.labelOf(branchType));
		obj.setKAType(kaType == null ? obj.getKAType() : KAType.labelOf(kaType));
		obj.setPromoterType(promoterType == null ? obj.getPromoterType() : PromoterType.labelOf(promoterType));
		obj.setPurchaserType(purchaserType == null ? obj.getPurchaserType() : PurchaserType.labelOf(purchaserType));
		obj.setTitle(StringUtils.isEmpty(title) ? obj.getTitle() : title);
		obj.setPictureURL(StringUtils.isEmpty(pictureURL) ? obj.getPictureURL() : pictureURL);
		obj.setCreationClerk(StringUtils.isEmpty(creationClerk) ? obj.getCreationClerk() : creationClerk);
		obj.setDescription(StringUtils.isEmpty(description) ? obj.getDescription() : description);
		obj.setCountyId(StringUtils.isEmpty(countyId) ? obj.getCountyId() : countyId);
		obj.setBranchName(obj.getTitle());
		obj.setAddress(StringUtils.isEmpty(address) ? obj.getAddress() : address);
		obj.setBusinessLicenseName(StringUtils.isEmpty(businessLicenseName) ? obj.getBusinessLicenseName() : businessLicenseName);
		obj.setWarehouseAddress(StringUtils.isEmpty(warehouseAddress) ? obj.getWarehouseAddress() : warehouseAddress);
		obj.setRegistrantName(StringUtils.isEmpty(registrantName) ? obj.getRegistrantName() : registrantName);
		obj.setPhone(StringUtils.isEmpty(phone) ? obj.getPhone() : phone);
		obj.setLicensePictureURL(StringUtils.isEmpty(licensePicture) ? obj.getLicensePictureURL() : licensePictureURL);
		obj.setInWholeSaleMarket(inWholeSaleMarket == null ? obj.getInWholeSaleMarket() : getInWholeSaleMarketBoolean());
		obj.setIsRefundAllowed(isRefundAllowed == null ? obj.getIsRefundAllowed() : isRefundAllowed);
		obj.setKeyUserPosition(StringUtils.isEmpty(keyUserPosition) ? obj.getKeyUserPosition() : TerminalKeyUserPosition.labelOf(keyUserPosition));
		obj.setOrganizationCode(StringUtils.isEmpty(organizationCode) ? obj.getOrganizationCode() : organizationCode);
		obj.setIsBanquetHotel(isBanquetHotel == null ? obj.getIsBanquetHotel() : getIsBanquetHotelBoolean());
		obj.setCashPersonMobile(StringUtils.isEmpty(cashPersonPhone) ? obj.getCashPersonMobile() : cashPersonPhone);
		obj.setCashPersonName(StringUtils.isEmpty(cashPersonName) ? obj.getCashPersonName() : cashPersonName);
		obj.setCashPersonWechat(StringUtils.isEmpty(cashPersonWechat) ? obj.getGoodsReceiverWechat() : cashPersonWechat);
		return obj;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBranchLevel() {
		return branchLevel;
	}

	public void setBranchLevel(String branchLevel) {
		this.branchLevel = branchLevel;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public String getCreationClerk() {
		return creationClerk;
	}

	public void setCreationClerk(String creationClerk) {
		this.creationClerk = creationClerk;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getBusinessLicenseName() {
		return businessLicenseName;
	}

	public void setBusinessLicenseName(String businessLicenseName) {
		this.businessLicenseName = businessLicenseName;
	}

	public String getWarehouseAddress() {
		return warehouseAddress;
	}

	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}

	public String getRegistrantName() {
		return registrantName;
	}

	public void setRegistrantName(String registrantName) {
		this.registrantName = registrantName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCashPersonOpenId() {
		return cashPersonOpenId;
	}

	public void setCashPersonOpenId(String cashPersonOpenId) {
		this.cashPersonOpenId = cashPersonOpenId;
	}

	public String getLicensePictureURL() {
		return licensePictureURL;
	}

	public void setLicensePictureURL(String licensePictureURL) {
		this.licensePictureURL = licensePictureURL;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getLicensePicture() {
		return licensePicture;
	}

	public void setLicensePicture(String licensePicture) {
		this.licensePicture = licensePicture;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKaType() {
		return kaType;
	}

	public void setKaType(String kaType) {
		this.kaType = kaType;
	}

	public String getPromoterType() {
		return promoterType;
	}

	public void setPromoterType(String promoterType) {
		this.promoterType = promoterType;
	}

	public String getPurchaserType() {
		return purchaserType;
	}

	public void setPurchaserType(String purchaserType) {
		this.purchaserType = purchaserType;
	}

	public Boolean getRefundAllowed() {
		return isRefundAllowed;
	}

	public void setRefundAllowed(Boolean refundAllowed) {
		isRefundAllowed = refundAllowed;
	}

	public Long getKeyUserId() {
		return keyUserId;
	}

	public void setKeyUserId(Long keyUserId) {
		this.keyUserId = keyUserId;
	}

	public String getKeyUserName() {
		return keyUserName;
	}

	public void setKeyUserName(String keyUserName) {
		this.keyUserName = keyUserName;
	}

	public String getKeyUserPhone() {
		return keyUserPhone;
	}

	public void setKeyUserPhone(String keyUserPhone) {
		this.keyUserPhone = keyUserPhone;
	}

	public String getKeyUserWechat() {
		return keyUserWechat;
	}

	public void setKeyUserWechat(String keyUserWechat) {
		this.keyUserWechat = keyUserWechat;
	}

	public Long getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(Long purchaserId) {
		this.purchaserId = purchaserId;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getPurchaserPhone() {
		return purchaserPhone;
	}

	public void setPurchaserPhone(String purchaserPhone) {
		this.purchaserPhone = purchaserPhone;
	}

	public String getPurchaserWechat() {
		return purchaserWechat;
	}

	public void setPurchaserWechat(String purchaserWechat) {
		this.purchaserWechat = purchaserWechat;
	}

	public Long getKaPurchaserId() {
		return kaPurchaserId;
	}

	public void setKaPurchaserId(Long kaPurchaserId) {
		this.kaPurchaserId = kaPurchaserId;
	}

	public String getKaPurchaserName() {
		return kaPurchaserName;
	}

	public void setKaPurchaserName(String kaPurchaserName) {
		this.kaPurchaserName = kaPurchaserName;
	}

	public String getKaPurchaserPhone() {
		return kaPurchaserPhone;
	}

	public void setKaPurchaserPhone(String kaPurchaserPhone) {
		this.kaPurchaserPhone = kaPurchaserPhone;
	}

	public String getKaPurchaserWechat() {
		return kaPurchaserWechat;
	}

	public void setKaPurchaserWechat(String kaPurchaserWechat) {
		this.kaPurchaserWechat = kaPurchaserWechat;
	}

	public Long getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Long supervisorId) {
		this.supervisorId = supervisorId;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public String getSupervisorPhone() {
		return supervisorPhone;
	}

	public void setSupervisorPhone(String supervisorPhone) {
		this.supervisorPhone = supervisorPhone;
	}

	public String getSupervisorWechat() {
		return supervisorWechat;
	}

	public void setSupervisorWechat(String supervisorWechat) {
		this.supervisorWechat = supervisorWechat;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getBusinessWechat() {
		return businessWechat;
	}

	public void setBusinessWechat(String businessWechat) {
		this.businessWechat = businessWechat;
	}

	public Long getPromoterId() {
		return promoterId;
	}

	public void setPromoterId(Long promoterId) {
		this.promoterId = promoterId;
	}

	public String getPromoterName() {
		return promoterName;
	}

	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}

	public String getPromoterPhone() {
		return promoterPhone;
	}

	public void setPromoterPhone(String promoterPhone) {
		this.promoterPhone = promoterPhone;
	}

	public String getPromoterWechat() {
		return promoterWechat;
	}

	public void setPromoterWechat(String promoterWechat) {
		this.promoterWechat = promoterWechat;
	}

	public Long getCashPersonId() {
		return cashPersonId;
	}

	public void setCashPersonId(Long cashPersonId) {
		this.cashPersonId = cashPersonId;
	}

	public String getCashPersonName() {
		return cashPersonName;
	}

	public void setCashPersonName(String cashPersonName) {
		this.cashPersonName = cashPersonName;
	}

	public String getCashPersonPhone() {
		return cashPersonPhone;
	}

	public void setCashPersonPhone(String cashPersonPhone) {
		this.cashPersonPhone = cashPersonPhone;
	}

	public String getCashPersonWechat() {
		return cashPersonWechat;
	}

	public void setCashPersonWechat(String cashPersonWechat) {
		this.cashPersonWechat = cashPersonWechat;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGpsDescription() {
		return gpsDescription;
	}

	public void setGpsDescription(String gpsDescription) {
		this.gpsDescription = gpsDescription;
	}

	public String getKeyUserType() {
		return keyUserType;
	}

	public void setKeyUserType(String keyUserType) {
		this.keyUserType = keyUserType;
	}

	public String getKeyUserPosition() {
		return keyUserPosition;
	}

	public void setKeyUserPosition(String keyUserPosition) {
		this.keyUserPosition = keyUserPosition;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public Integer getIsBanquetHotel() {
		return isBanquetHotel;
	}

	public void setIsBanquetHotel(Integer isBanquetHotel) {
		this.isBanquetHotel = isBanquetHotel;
	}

	public Boolean getIsBanquetHotelBoolean() {
		if (this.isBanquetHotel == 1) {
			return true;
		}
		else if (this.isBanquetHotel == 0) {
			return false;
		}
		return null;
	}

	public Integer getInWholeSaleMarket() {
		return inWholeSaleMarket;
	}

	public void setInWholeSaleMarket(Integer inWholeSaleMarket) {
		this.inWholeSaleMarket = inWholeSaleMarket;
	}

	public Boolean getInWholeSaleMarketBoolean() {
		if (this.inWholeSaleMarket == 1) {
			return true;
		}
		else if (this.inWholeSaleMarket == 0) {
			return false;
		}
		return null;
	}
}
