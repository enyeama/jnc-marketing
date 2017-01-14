package com.sap.jnc.marketing.dto.response.terminal;

import java.io.Serializable;
import java.math.BigDecimal;

import com.sap.jnc.marketing.persistence.model.Terminal;
import org.springframework.util.StringUtils;

/**
 * Created by guokai on 16/6/21.
 */
public class TerminalResponse implements Serializable {

	private Long id;

	private String type;

	private String status;

	private String branchLevel;

	private String branchType;

	private String KAType;

	private String promoterType;

	private String purchaserType;

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

	private String cashPersonMobile;

	private String cashPersonOpenId;

	private String licensePictureURL;

	private Boolean InWholeSaleMarket;

	private Boolean isRefundAllowed;

	private BigDecimal longitude;

	private BigDecimal latitude;

	private String accuracy;

	private String province;

	private String city;

	private String county;

	private String GPSDescription;

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

	private String gpsDescription;

	private String organizationCode;

	private String keyUserPosition;

	private Boolean isBanquetHotel;

	// private Region region;

	public TerminalResponse(Terminal terminal) {
		this.id = terminal.getId();
		this.type = terminal.getType() != null ? terminal.getType().getLabel() : "";
		this.status = terminal.getStatus() != null ? terminal.getStatus().getLabel() : "";
		this.branchLevel = terminal.getBranchLevel() != null ? terminal.getBranchLevel().getLabel() : "";
		this.branchType = terminal.getBranchType() != null ? terminal.getBranchType().getLabel() : "";
		this.KAType = terminal.getKAType() != null ? terminal.getKAType().getLabel() : "";
		this.promoterType = terminal.getPromoterType() != null ? terminal.getPromoterType().getLabel() : "";
		this.purchaserType = terminal.getPurchaserType() != null ? terminal.getPurchaserType().getLabel() : "";
		this.title = terminal.getTitle();
		this.pictureURL = terminal.getPictureURL();
		this.creationClerk = terminal.getCreationClerk();
		this.description = terminal.getDescription();
		this.countyId = terminal.getCountyId();
		this.branchName = terminal.getBranchName();
		this.address = terminal.getAddress();
		this.businessLicenseName = terminal.getBusinessLicenseName();
		this.warehouseAddress = terminal.getWarehouseAddress();
		this.registrantName = terminal.getRegistrantName();
		this.phone = terminal.getPhone();
		this.licensePictureURL = terminal.getLicensePictureURL();
		this.InWholeSaleMarket = terminal.getInWholeSaleMarket();
		this.isRefundAllowed = terminal.getIsRefundAllowed();
		this.description = terminal.getDescription();
		this.keyUserPosition = terminal.getKeyUserPosition() != null ? terminal.getKeyUserPosition().getLabel() : "";
		this.organizationCode = terminal.getOrganizationCode();
		this.isBanquetHotel = terminal.getIsBanquetHotel();

		if (terminal.getGps() != null) {
			this.longitude = terminal.getGps().getLongitude();
			this.latitude = terminal.getGps().getLatitude();
			this.accuracy = terminal.getGps().getAccuracy();
			this.province = terminal.getGps().getProvince();
			this.city = terminal.getGps().getCity();
			this.county = terminal.getGps().getCounty();
			this.gpsDescription = terminal.getGps().getDescription();
		}

		if (terminal.getKeyUserContact() != null) {
			this.keyUserId = terminal.getKeyUserContact().getId();
			this.keyUserName = terminal.getKeyUserContact().getName();
			this.keyUserPhone = terminal.getKeyUserContact().getPhone();
			this.keyUserWechat = terminal.getKeyUserContact().getWechat();
		}

		if (terminal.getPurchaserContact() != null) {
			this.purchaserId = terminal.getPurchaserContact().getId();
			this.purchaserName = terminal.getPurchaserContact().getName();
			this.purchaserPhone = terminal.getPurchaserContact().getPhone();
			this.purchaserWechat = terminal.getPurchaserContact().getWechat();
		}

		if (terminal.getKaPurchaserContact() != null) {
			this.kaPurchaserId = terminal.getKaPurchaserContact().getId();
			this.kaPurchaserName = terminal.getKaPurchaserContact().getName();
			this.kaPurchaserPhone = terminal.getKaPurchaserContact().getPhone();
			this.kaPurchaserWechat = terminal.getKaPurchaserContact().getWechat();
		}

		if (terminal.getSupervisorContact() != null) {
			this.supervisorId = terminal.getSupervisorContact().getId();
			this.supervisorName = terminal.getSupervisorContact().getName();
			this.supervisorPhone = terminal.getSupervisorContact().getPhone();
			this.supervisorWechat = terminal.getSupervisorContact().getWechat();
		}

		if (terminal.getBusinessContact() != null) {
			this.businessId = terminal.getBusinessContact().getId();
			this.businessName = terminal.getBusinessContact().getName();
			this.businessPhone = terminal.getBusinessContact().getPhone();
			this.businessWechat = terminal.getBusinessContact().getWechat();
		}

		if (terminal.getPromoterContact() != null) {
			this.promoterId = terminal.getPromoterContact().getId();
			this.promoterName = terminal.getPromoterContact().getName();
			this.promoterPhone = terminal.getPromoterContact().getPhone();
			this.promoterWechat = terminal.getPromoterContact().getWechat();
		}

		if (terminal.getCashPersonContact() != null) {
			this.cashPersonId = terminal.getCashPersonContact().getId();
			this.cashPersonName = terminal.getCashPersonContact().getName();
			this.cashPersonPhone = terminal.getCashPersonContact().getPhone();
			this.cashPersonWechat = terminal.getCashPersonContact().getWechat();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getKAType() {
		return KAType;
	}

	public void setKAType(String KAType) {
		this.KAType = KAType;
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

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
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

	public String getCashPersonMobile() {
		return cashPersonMobile;
	}

	public void setCashPersonMobile(String cashPersonMobile) {
		this.cashPersonMobile = cashPersonMobile;
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

	public Boolean getInWholeSaleMarket() {
		return InWholeSaleMarket;
	}

	public void setInWholeSaleMarket(Boolean inWholeSaleMarket) {
		InWholeSaleMarket = inWholeSaleMarket;
	}

	public Boolean getRefundAllowed() {
		return isRefundAllowed;
	}

	public void setRefundAllowed(Boolean refundAllowed) {
		isRefundAllowed = refundAllowed;
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

	public String getGPSDescription() {
		return GPSDescription;
	}

	public void setGPSDescription(String GPSDescription) {
		this.GPSDescription = GPSDescription;
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

	public String getGpsDescription() {
		return gpsDescription;
	}

	public void setGpsDescription(String gpsDescription) {
		this.gpsDescription = gpsDescription;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getKeyUserPosition() {
		return keyUserPosition;
	}

	public void setKeyUserPosition(String keyUserPosition) {
		this.keyUserPosition = keyUserPosition;
	}

	public Boolean getBanquetHotel() {
		return isBanquetHotel;
	}

	public void setBanquetHotel(Boolean banquetHotel) {
		isBanquetHotel = banquetHotel;
	}
}
