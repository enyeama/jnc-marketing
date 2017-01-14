package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.BranchLevel;
import com.sap.jnc.marketing.persistence.enumeration.BranchType;
import com.sap.jnc.marketing.persistence.enumeration.KAType;
import com.sap.jnc.marketing.persistence.enumeration.PromoterType;
import com.sap.jnc.marketing.persistence.enumeration.PurchaserType;
import com.sap.jnc.marketing.persistence.enumeration.TerminalKeyUserPosition;
import com.sap.jnc.marketing.persistence.enumeration.TerminalStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalType;

@Entity
@Table(name = "T_TERMINAL", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "TerminalSeq", sequenceName = "SEQ_TERMINAL")
public class Terminal extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 5029322536573532587L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TerminalSeq")
	private Long id;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private TerminalType type;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private TerminalStatus status;

	// 组织机构代码
	@Column(name = "organizationCode")
	private String organizationCode;

	@Column(name = "branchLevel")
	@Enumerated(EnumType.STRING)
	private BranchLevel branchLevel;

	@Column(name = "branchType")
	@Enumerated(EnumType.STRING)
	private BranchType branchType;

	@Column(name = "KAType")
	@Enumerated(EnumType.STRING)
	private KAType KAType;

	@Column(name = "promoterType")
	@Enumerated(EnumType.STRING)
	private PromoterType promoterType;

	@Column(name = "purchaserType")
	@Enumerated(EnumType.STRING)
	private PurchaserType purchaserType;

	@Column(name = "externalId")
	private String externalId;

	@Column(name = "title")
	private String title;

	@Column(name = "pictureURL")
	private String pictureURL;

	@Column(name = "creationClerk")
	private String creationClerk;

	@Column(name = "description")
	private String description;

	@Column(name = "countyId")
	private String countyId;

	@Column(name = "branchName")
	private String branchName;

	@Column(name = "address")
	private String address;

	@Column(name = "businessLicenseName")
	private String businessLicenseName;

	@Column(name = "warehouseAddress")
	private String warehouseAddress;

	@Column(name = "registrantName")
	private String registrantName;

	@Column(name = "phone")
	private String phone;

	@Column(name = "cashPersonName")
	private String cashPersonName;

	@Column(name = "cashPersonMobile")
	private String cashPersonMobile;

	@Column(name = "cashPersonWechat")
	private String cashPersonWechat;

	@Column(name = "goodsReceiverWechat")
	private String goodsReceiverWechat;

	@Column(name = "cashPersonOpenId")
	private String cashPersonOpenId;

	@Column(name = "licensePictureURL")
	private String licensePictureURL;

	@Column(name = "InWholeSaleMarket")
	private Boolean InWholeSaleMarket;

	@Column(name = "isRefundAllowed")
	private Boolean isRefundAllowed;

	@Column(name = "isBanquetHotel")
	private Boolean isBanquetHotel;

	// 店号
	@Column(name = "storeNumber")
	private String storeNumber;

	// 商超系统编号
	@Column(name = "KASystemNumber")
	private String KASystemNumber;

	// 商超系统描述
	@Column(name = "KASystemName")
	private String KASystemName;

	// 维护人岗位
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maintainer")
	private PositionView maintainer;

	// 渠道编号
	@Column(name = "channelNumber")
	private String channelNumber;

	// 渠道描述
	@Column(name = "channelName")
	private String channelName;

	// 门店业态
	@Column(name = "businessStatus")
	private String businessStatus;

	// KA产品描述
	@Column(name = "KAProducts")
	private String KAProducts;

	// 商超系统性质编号
	@Column(name = "KASystemPropertyNumber")
	private String KASystemPropertyNumber;

	// 商超系统性质描述
	@Column(name = "KASystemPropertyName")
	private String KASystemPropertyName;

	private GPS gps;

	// 酒店关键人职位
	@Column(name = "keyUserPosition")
	@Enumerated(EnumType.STRING)
	private TerminalKeyUserPosition keyUserPosition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channelId")
	private Channel channel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "keyUserContactId")
	private Contact keyUserContact;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchaserContactId")
	private Contact purchaserContact;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kaPurchaserContactId")
	private Contact kaPurchaserContact;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supervisorContactId")
	private Contact supervisorContact;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "businessContactId")
	private Contact businessContact;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "promoterContactId")
	private Contact promoterContact;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cashPersonContactId")
	private Contact cashPersonContact;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "goodsReceiverContactId")
	private Contact goodsReceiverContact;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KAOfficId")
	private DepartmentView KAOffice;

	// KA客户经理
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KAAcountManagerId")
	private PositionView KAAcountManager;

	// 城市经理
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityManagerId")
	private PositionView cityManager;

	// KA专员
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KASpecialistId")
	private PositionView KASpecialist;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "regionID")
	private Region region;

	@OneToMany(mappedBy = "terminal")
	private List<Exhibition> exhibitions;

	@OneToMany(mappedBy = "terminal")
	private List<Banquet> banquets;

	@OneToMany(mappedBy = "terminal")
	private List<TerminalOrder> terminalOrders;

	@OneToMany(mappedBy = "terminal")
	private List<UserReferenceToTerminal> userReferences;

	@OneToMany(mappedBy = "terminal")
	private List<UserVerificationForTerminal> userVerifications;

	@ManyToMany(mappedBy = "terminals")
	protected List<PositionView> salesmen;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TerminalType getType() {
		return this.type;
	}

	public void setType(TerminalType type) {
		this.type = type;
	}

	public TerminalStatus getStatus() {
		return this.status;
	}

	public void setStatus(TerminalStatus status) {
		this.status = status;
	}

	public String getOrganizationCode() {
		return this.organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public BranchLevel getBranchLevel() {
		return this.branchLevel;
	}

	public void setBranchLevel(BranchLevel branchLevel) {
		this.branchLevel = branchLevel;
	}

	public BranchType getBranchType() {
		return this.branchType;
	}

	public void setBranchType(BranchType branchType) {
		this.branchType = branchType;
	}

	public KAType getKAType() {
		return this.KAType;
	}

	public void setKAType(KAType kAType) {
		this.KAType = kAType;
	}

	public PromoterType getPromoterType() {
		return this.promoterType;
	}

	public void setPromoterType(PromoterType promoterType) {
		this.promoterType = promoterType;
	}

	public PurchaserType getPurchaserType() {
		return this.purchaserType;
	}

	public void setPurchaserType(PurchaserType purchaserType) {
		this.purchaserType = purchaserType;
	}

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPictureURL() {
		return this.pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public String getCreationClerk() {
		return this.creationClerk;
	}

	public void setCreationClerk(String creationClerk) {
		this.creationClerk = creationClerk;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountyId() {
		return this.countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusinessLicenseName() {
		return this.businessLicenseName;
	}

	public void setBusinessLicenseName(String businessLicenseName) {
		this.businessLicenseName = businessLicenseName;
	}

	public String getWarehouseAddress() {
		return this.warehouseAddress;
	}

	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}

	public String getRegistrantName() {
		return this.registrantName;
	}

	public void setRegistrantName(String registrantName) {
		this.registrantName = registrantName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCashPersonName() {
		return this.cashPersonName;
	}

	public void setCashPersonName(String cashPersonName) {
		this.cashPersonName = cashPersonName;
	}

	public String getCashPersonMobile() {
		return this.cashPersonMobile;
	}

	public void setCashPersonMobile(String cashPersonMobile) {
		this.cashPersonMobile = cashPersonMobile;
	}

	public String getCashPersonWechat() {
		return this.cashPersonWechat;
	}

	public void setCashPersonWechat(String cashPersonWechat) {
		this.cashPersonWechat = cashPersonWechat;
	}

	public String getGoodsReceiverWechat() {
		return this.goodsReceiverWechat;
	}

	public void setGoodsReceiverWechat(String goodsReceiverWechat) {
		this.goodsReceiverWechat = goodsReceiverWechat;
	}

	public String getCashPersonOpenId() {
		return this.cashPersonOpenId;
	}

	public void setCashPersonOpenId(String cashPersonOpenId) {
		this.cashPersonOpenId = cashPersonOpenId;
	}

	public String getLicensePictureURL() {
		return this.licensePictureURL;
	}

	public void setLicensePictureURL(String licensePictureURL) {
		this.licensePictureURL = licensePictureURL;
	}

	public Boolean getInWholeSaleMarket() {
		return this.InWholeSaleMarket;
	}

	public void setInWholeSaleMarket(Boolean inWholeSaleMarket) {
		this.InWholeSaleMarket = inWholeSaleMarket;
	}

	public Boolean getIsRefundAllowed() {
		return this.isRefundAllowed;
	}

	public void setIsRefundAllowed(Boolean isRefundAllowed) {
		this.isRefundAllowed = isRefundAllowed;
	}

	public Boolean getIsBanquetHotel() {
		return this.isBanquetHotel;
	}

	public void setIsBanquetHotel(Boolean isBanquetHotel) {
		this.isBanquetHotel = isBanquetHotel;
	}

	public String getStoreNumber() {
		return this.storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getKASystemNumber() {
		return this.KASystemNumber;
	}

	public void setKASystemNumber(String kASystemNumber) {
		this.KASystemNumber = kASystemNumber;
	}

	public String getKASystemName() {
		return this.KASystemName;
	}

	public void setKASystemName(String kASystemName) {
		this.KASystemName = kASystemName;
	}

	public PositionView getMaintainer() {
		return this.maintainer;
	}

	public void setMaintainer(PositionView maintainer) {
		this.maintainer = maintainer;
	}

	public String getChannelNumber() {
		return this.channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	public String getChannelName() {
		return this.channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getBusinessStatus() {
		return this.businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public String getKAProducts() {
		return this.KAProducts;
	}

	public void setKAProducts(String kAProducts) {
		this.KAProducts = kAProducts;
	}

	public String getKASystemPropertyNumber() {
		return this.KASystemPropertyNumber;
	}

	public void setKASystemPropertyNumber(String kASystemPropertyNumber) {
		this.KASystemPropertyNumber = kASystemPropertyNumber;
	}

	public String getKASystemPropertyName() {
		return this.KASystemPropertyName;
	}

	public void setKASystemPropertyName(String kASystemPropertyName) {
		this.KASystemPropertyName = kASystemPropertyName;
	}

	public GPS getGps() {
		return this.gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	public TerminalKeyUserPosition getKeyUserPosition() {
		return this.keyUserPosition;
	}

	public void setKeyUserPosition(TerminalKeyUserPosition keyUserPosition) {
		this.keyUserPosition = keyUserPosition;
	}

	public Channel getChannel() {
		return this.channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Contact getKeyUserContact() {
		return this.keyUserContact;
	}

	public void setKeyUserContact(Contact keyUserContact) {
		this.keyUserContact = keyUserContact;
	}

	public Contact getPurchaserContact() {
		return this.purchaserContact;
	}

	public void setPurchaserContact(Contact purchaserContact) {
		this.purchaserContact = purchaserContact;
	}

	public Contact getKaPurchaserContact() {
		return this.kaPurchaserContact;
	}

	public void setKaPurchaserContact(Contact kaPurchaserContact) {
		this.kaPurchaserContact = kaPurchaserContact;
	}

	public Contact getSupervisorContact() {
		return this.supervisorContact;
	}

	public void setSupervisorContact(Contact supervisorContact) {
		this.supervisorContact = supervisorContact;
	}

	public Contact getBusinessContact() {
		return this.businessContact;
	}

	public void setBusinessContact(Contact businessContact) {
		this.businessContact = businessContact;
	}

	public Contact getPromoterContact() {
		return this.promoterContact;
	}

	public void setPromoterContact(Contact promoterContact) {
		this.promoterContact = promoterContact;
	}

	public Contact getCashPersonContact() {
		return this.cashPersonContact;
	}

	public void setCashPersonContact(Contact cashPersonContact) {
		this.cashPersonContact = cashPersonContact;
	}

	public Contact getGoodsReceiverContact() {
		return this.goodsReceiverContact;
	}

	public void setGoodsReceiverContact(Contact goodsReceiverContact) {
		this.goodsReceiverContact = goodsReceiverContact;
	}

	public DepartmentView getKAOffice() {
		return this.KAOffice;
	}

	public void setKAOffice(DepartmentView kAOffice) {
		this.KAOffice = kAOffice;
	}

	public PositionView getKAAcountManager() {
		return this.KAAcountManager;
	}

	public void setKAAcountManager(PositionView kAAcountManager) {
		this.KAAcountManager = kAAcountManager;
	}

	public PositionView getCityManager() {
		return this.cityManager;
	}

	public void setCityManager(PositionView cityManager) {
		this.cityManager = cityManager;
	}

	public PositionView getKASpecialist() {
		return this.KASpecialist;
	}

	public void setKASpecialist(PositionView kASpecialist) {
		this.KASpecialist = kASpecialist;
	}

	public Region getRegion() {
		return this.region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public List<Exhibition> getExhibitions() {
		return this.exhibitions;
	}

	public void setExhibitions(List<Exhibition> exhibitions) {
		this.exhibitions = exhibitions;
	}

	public List<Banquet> getBanquets() {
		return this.banquets;
	}

	public void setBanquets(List<Banquet> banquets) {
		this.banquets = banquets;
	}

	public List<TerminalOrder> getTerminalOrders() {
		return this.terminalOrders;
	}

	public void setTerminalOrders(List<TerminalOrder> terminalOrders) {
		this.terminalOrders = terminalOrders;
	}

	public List<UserReferenceToTerminal> getUserReferences() {
		return this.userReferences;
	}

	public void setUserReferences(List<UserReferenceToTerminal> userReferences) {
		this.userReferences = userReferences;
	}

	public List<UserVerificationForTerminal> getUserVerifications() {
		return this.userVerifications;
	}

	public void setUserVerifications(List<UserVerificationForTerminal> userVerifications) {
		this.userVerifications = userVerifications;
	}

	public List<PositionView> getSalesmen() {
		return this.salesmen;
	}

	public void setSalesmen(List<PositionView> salesmen) {
		this.salesmen = salesmen;
	}
}
