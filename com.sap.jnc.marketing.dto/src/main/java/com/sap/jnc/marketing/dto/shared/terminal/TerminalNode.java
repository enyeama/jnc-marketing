/**
 * 
 */
package com.sap.jnc.marketing.dto.shared.terminal;

import java.io.Serializable;

import com.sap.jnc.marketing.dto.response.channel.ChannelResponse;
import com.sap.jnc.marketing.dto.response.contact.ContactResponse;
import com.sap.jnc.marketing.dto.response.department.DepartmentResponse;
import com.sap.jnc.marketing.dto.response.region.RegionResponse;
import com.sap.jnc.marketing.dto.shared.gps.GPSNode;
import com.sap.jnc.marketing.dto.shared.kaorder.EmployeePositionInfo;
import com.sap.jnc.marketing.persistence.enumeration.BranchLevel;
import com.sap.jnc.marketing.persistence.enumeration.BranchType;
import com.sap.jnc.marketing.persistence.enumeration.KAType;
import com.sap.jnc.marketing.persistence.enumeration.PromoterType;
import com.sap.jnc.marketing.persistence.enumeration.PurchaserType;
import com.sap.jnc.marketing.persistence.enumeration.TerminalStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalType;
import com.sap.jnc.marketing.persistence.model.Channel;
import com.sap.jnc.marketing.persistence.model.Contact;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.GPS;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Region;
import com.sap.jnc.marketing.persistence.model.Terminal;

/**
 * @author Quansheng Liu I075496
 */
public class TerminalNode implements Serializable {

	private static final long serialVersionUID = 4933147426804038054L;

	private Long id;

	private TerminalType type;

	private TerminalStatus status;

	private BranchLevel branchLevel;

	private BranchType branchType;

	private KAType kaType;

	private PromoterType promoterType;

	private PurchaserType purchaserType;

	private String externalId;

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

	private String cashPersonName;

	private String cashPersonMobile;

	private String cashPersonWechat;

	private String goodsReceiverWechat;

	private String cashPersonOpenId;

	private String licensePictureURL;

	private Boolean inWholeSaleMarket;

	private Boolean isRefundAllowed;

	private Boolean isBanquetHotel;

	// 店号
	private String storeNumber;

	// 商超系统编号
	private String kaSystemNumber;

	// 商超系统描述
	private String kaSystemName;

	// 维护人岗位
	private EmployeePositionInfo maintainer;

	// 渠道编号
	private String channelNumber;

	// 渠道描述
	private String channelName;

	// 门店业态
	private String businessStatus;

	// KA产品描述
	private String kaProducts;

	// 商超系统性质编号
	private String kaSystemPropertyNumber;

	// 商超系统性质描述
	private String kaSystemPropertyName;

	private GPSNode gps;

	private ChannelResponse channel;

	private ContactResponse keyUserContact;

	private ContactResponse purchaserContact;

	private ContactResponse kaPurchaserContact;

	private ContactResponse supervisorContact;

	private ContactResponse businessContact;

	private ContactResponse promoterContact;

	private ContactResponse cashPersonContact;

	private ContactResponse goodsReceiverContact;

	private DepartmentResponse kaOffice;

	// KA客户经理
	private EmployeePositionInfo kaAccountManager;

	// KA专员
	private EmployeePositionInfo kaSpecialist;

	private RegionResponse region;

	private EmployeePositionInfo cityManager;

	// TODO
	// private List<Exhibition> exhibitions;

	// TODO
	// private List<Banquet> banquets;

	// TODO
	// private List<TerminalOrder> terminalOrders;

	// TODO
	// protected List<PositionView> salesmen;

	public TerminalNode() {

	}

	public TerminalNode(Terminal terminal) {
		if (terminal == null) {
			return;
		}
		this.setAddress(terminal.getAddress());
		this.setBranchLevel(terminal.getBranchLevel());
		this.setBranchName(terminal.getBranchName());
		this.setBranchType(terminal.getBranchType());
		this.setBusinessLicenseName(terminal.getBusinessLicenseName());
		this.setBusinessStatus(terminal.getBusinessStatus());
		this.setCashPersonMobile(terminal.getCashPersonMobile());
		this.setCashPersonName(terminal.getCashPersonName());
		this.setCashPersonOpenId(terminal.getCashPersonOpenId());
		this.setCashPersonWechat(terminal.getCashPersonWechat());
		this.setChannelName(terminal.getChannelName());
		this.setChannelNumber(terminal.getChannelNumber());
		this.setCountyId(terminal.getCountyId());
		this.setCreationClerk(terminal.getCreationClerk());
		this.setDescription(terminal.getDescription());
		this.setExternalId(terminal.getExternalId());
		this.setGoodsReceiverWechat(terminal.getGoodsReceiverWechat());
		this.setId(terminal.getId());
		this.setInWholeSaleMarket(terminal.getInWholeSaleMarket());
		this.setIsBanquetHotel(terminal.getIsBanquetHotel());
		this.setIsRefundAllowed(terminal.getIsRefundAllowed());
		this.setKaProducts(terminal.getKAProducts());
		this.setKaSystemName(terminal.getKASystemName());
		this.setKaSystemNumber(terminal.getKASystemNumber());
		this.setKaSystemPropertyName(terminal.getKASystemPropertyName());
		this.setKaSystemPropertyNumber(terminal.getKASystemPropertyNumber());
		this.setKaType(terminal.getKAType());
		this.setLicensePictureURL(terminal.getLicensePictureURL());
		this.setPhone(terminal.getPhone());
		this.setPictureURL(terminal.getPictureURL());
		this.setRegistrantName(terminal.getRegistrantName());
		this.setStatus(terminal.getStatus());
		this.setStoreNumber(terminal.getStoreNumber());
		this.setTitle(terminal.getTitle());
		this.setType(terminal.getType());
		this.setPromoterType(terminal.getPromoterType());
		this.setPurchaserType(terminal.getPurchaserType());
		this.setWarehouseAddress(terminal.getWarehouseAddress());
		Contact businessContact = terminal.getBusinessContact();
		if (businessContact != null) {
			this.setBusinessContact(new ContactResponse(businessContact));
		}
		else {
			this.setBusinessContact(null);
		}
		Contact cashPersonContact = terminal.getCashPersonContact();
		if (cashPersonContact != null) {
			this.setCashPersonContact(new ContactResponse(cashPersonContact));
		}
		else {
			this.setCashPersonContact(null);
		}
		Channel channel = terminal.getChannel();
		if (channel != null) {
			this.setChannel(new ChannelResponse(channel));
		}
		else {
			this.setChannel(null);
		}
		Contact goodsReceiverContact = terminal.getGoodsReceiverContact();
		if (goodsReceiverContact != null) {
			this.setGoodsReceiverContact(new ContactResponse(goodsReceiverContact));
		}
		else {
			this.setGoodsReceiverContact(null);
		}
		GPS gps = terminal.getGps();
		if (gps != null) {
			this.setGps(new GPSNode(gps));
		}
		else {
			this.setGps(null);
		}
		PositionView kaAccountManager = terminal.getKAAcountManager();
		if (kaAccountManager != null) {
			this.setKaAccountManager(new EmployeePositionInfo(kaAccountManager));
		}
		else {
			this.setKaAccountManager(null);
		}
		DepartmentView kaOffice = terminal.getKAOffice();
		if (kaOffice != null) {
			this.setKaOffice(new DepartmentResponse(kaOffice));
		}
		else {
			this.setKaOffice(null);
		}
		Contact kaPurchaserContact = terminal.getKaPurchaserContact();
		if (kaPurchaserContact != null) {
			this.setKaPurchaserContact(new ContactResponse(kaPurchaserContact));
		}
		else {
			this.setKaPurchaserContact(null);
		}
		PositionView kaSpecialist = terminal.getKASpecialist();
		if (kaSpecialist != null) {
			this.setKaSpecialist(new EmployeePositionInfo(kaSpecialist));
		}
		else {
			this.setKaSpecialist(null);
		}
		Contact keyUserContact = terminal.getKeyUserContact();
		if (keyUserContact != null) {
			this.setKeyUserContact(new ContactResponse(keyUserContact));
		}
		else {
			this.setKeyUserContact(null);
		}
		PositionView maintainer = terminal.getMaintainer();
		if (maintainer != null) {
			this.setMaintainer(new EmployeePositionInfo(maintainer));
		}
		else {
			this.setMaintainer(null);
		}
		Contact promoterContact = terminal.getPromoterContact();
		if (promoterContact != null) {
			this.setPromoterContact(new ContactResponse(promoterContact));
		}
		else {
			this.setPromoterContact(null);
		}
		Contact purchaserContact = terminal.getPurchaserContact();
		if (purchaserContact != null) {
			this.setPurchaserContact(new ContactResponse(purchaserContact));
		}
		else {
			this.setPurchaserContact(null);
		}
		Region region = terminal.getRegion();
		if (region != null) {
			this.setRegion(new RegionResponse(region));
		}
		else {
			this.setRegion(null);
		}
		Contact supervisorContact = terminal.getSupervisorContact();
		if (supervisorContact != null) {
			this.setSupervisorContact(new ContactResponse(supervisorContact));
		}
		else {
			this.setSupervisorContact(null);
		}
		PositionView cityManager = terminal.getCityManager();
		if (cityManager != null) {
			this.setCityManager(new EmployeePositionInfo(cityManager));
		}
		else {
			this.setCityManager(null);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TerminalType getType() {
		return type;
	}

	public void setType(TerminalType type) {
		this.type = type;
	}

	public TerminalStatus getStatus() {
		return status;
	}

	public void setStatus(TerminalStatus status) {
		this.status = status;
	}

	public BranchLevel getBranchLevel() {
		return branchLevel;
	}

	public void setBranchLevel(BranchLevel branchLevel) {
		this.branchLevel = branchLevel;
	}

	public BranchType getBranchType() {
		return branchType;
	}

	public void setBranchType(BranchType branchType) {
		this.branchType = branchType;
	}

	public KAType getKaType() {
		return kaType;
	}

	public void setKaType(KAType kaType) {
		this.kaType = kaType;
	}

	public PromoterType getPromoterType() {
		return promoterType;
	}

	public void setPromoterType(PromoterType promoterType) {
		this.promoterType = promoterType;
	}

	public PurchaserType getPurchaserType() {
		return purchaserType;
	}

	public void setPurchaserType(PurchaserType purchaserType) {
		this.purchaserType = purchaserType;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
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

	public String getCashPersonName() {
		return cashPersonName;
	}

	public void setCashPersonName(String cashPersonName) {
		this.cashPersonName = cashPersonName;
	}

	public String getCashPersonMobile() {
		return cashPersonMobile;
	}

	public void setCashPersonMobile(String cashPersonMobile) {
		this.cashPersonMobile = cashPersonMobile;
	}

	public String getCashPersonWechat() {
		return cashPersonWechat;
	}

	public void setCashPersonWechat(String cashPersonWechat) {
		this.cashPersonWechat = cashPersonWechat;
	}

	public String getGoodsReceiverWechat() {
		return goodsReceiverWechat;
	}

	public void setGoodsReceiverWechat(String goodsReceiverWechat) {
		this.goodsReceiverWechat = goodsReceiverWechat;
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
		return inWholeSaleMarket;
	}

	public void setInWholeSaleMarket(Boolean inWholeSaleMarket) {
		this.inWholeSaleMarket = inWholeSaleMarket;
	}

	public Boolean getIsRefundAllowed() {
		return isRefundAllowed;
	}

	public void setIsRefundAllowed(Boolean isRefundAllowed) {
		this.isRefundAllowed = isRefundAllowed;
	}

	public Boolean getIsBanquetHotel() {
		return isBanquetHotel;
	}

	public void setIsBanquetHotel(Boolean isBanquetHotel) {
		this.isBanquetHotel = isBanquetHotel;
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

	public EmployeePositionInfo getMaintainer() {
		return maintainer;
	}

	public void setMaintainer(EmployeePositionInfo maintainer) {
		this.maintainer = maintainer;
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public String getKaProducts() {
		return kaProducts;
	}

	public void setKaProducts(String kaProducts) {
		this.kaProducts = kaProducts;
	}

	public String getKaSystemPropertyNumber() {
		return kaSystemPropertyNumber;
	}

	public void setKaSystemPropertyNumber(String kaSystemPropertyNumber) {
		this.kaSystemPropertyNumber = kaSystemPropertyNumber;
	}

	public String getKaSystemPropertyName() {
		return kaSystemPropertyName;
	}

	public void setKaSystemPropertyName(String kaSystemPropertyName) {
		this.kaSystemPropertyName = kaSystemPropertyName;
	}

	public GPSNode getGps() {
		return gps;
	}

	public void setGps(GPSNode gps) {
		this.gps = gps;
	}

	public ChannelResponse getChannel() {
		return channel;
	}

	public void setChannel(ChannelResponse channel) {
		this.channel = channel;
	}

	public ContactResponse getKeyUserContact() {
		return keyUserContact;
	}

	public void setKeyUserContact(ContactResponse keyUserContact) {
		this.keyUserContact = keyUserContact;
	}

	public ContactResponse getPurchaserContact() {
		return purchaserContact;
	}

	public void setPurchaserContact(ContactResponse purchaserContact) {
		this.purchaserContact = purchaserContact;
	}

	public ContactResponse getKaPurchaserContact() {
		return kaPurchaserContact;
	}

	public void setKaPurchaserContact(ContactResponse kaPurchaserContact) {
		this.kaPurchaserContact = kaPurchaserContact;
	}

	public ContactResponse getSupervisorContact() {
		return supervisorContact;
	}

	public void setSupervisorContact(ContactResponse supervisorContact) {
		this.supervisorContact = supervisorContact;
	}

	public ContactResponse getBusinessContact() {
		return businessContact;
	}

	public void setBusinessContact(ContactResponse businessContact) {
		this.businessContact = businessContact;
	}

	public ContactResponse getPromoterContact() {
		return promoterContact;
	}

	public void setPromoterContact(ContactResponse promoterContact) {
		this.promoterContact = promoterContact;
	}

	public ContactResponse getCashPersonContact() {
		return cashPersonContact;
	}

	public void setCashPersonContact(ContactResponse cashPersonContact) {
		this.cashPersonContact = cashPersonContact;
	}

	public ContactResponse getGoodsReceiverContact() {
		return goodsReceiverContact;
	}

	public void setGoodsReceiverContact(ContactResponse goodsReceiverContact) {
		this.goodsReceiverContact = goodsReceiverContact;
	}

	public DepartmentResponse getKaOffice() {
		return kaOffice;
	}

	public void setKaOffice(DepartmentResponse kaOffice) {
		this.kaOffice = kaOffice;
	}

	public RegionResponse getRegion() {
		return region;
	}

	public void setRegion(RegionResponse region) {
		this.region = region;
	}

	public EmployeePositionInfo getKaAccountManager() {
		return kaAccountManager;
	}

	public void setKaAccountManager(EmployeePositionInfo kaAccountManager) {
		this.kaAccountManager = kaAccountManager;
	}

	public EmployeePositionInfo getKaSpecialist() {
		return kaSpecialist;
	}

	public void setKaSpecialist(EmployeePositionInfo kaSpecialist) {
		this.kaSpecialist = kaSpecialist;
	}

	public EmployeePositionInfo getCityManager() {
		return cityManager;
	}

	public void setCityManager(EmployeePositionInfo cityManager) {
		this.cityManager = cityManager;
	}

}
