package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Cacheable;
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
import com.sap.jnc.marketing.persistence.enumeration.BanquetApplicationType;
import com.sap.jnc.marketing.persistence.enumeration.BanquetProcessStatus;
import com.sap.jnc.marketing.persistence.enumeration.BanquetScanType;
import com.sap.jnc.marketing.persistence.enumeration.BanquetStatus;
import com.sap.jnc.marketing.persistence.enumeration.BanquetTimePoint;
import com.sap.jnc.marketing.persistence.enumeration.PaymentStatus;

@Cacheable(false)
@Entity
@Table(name = "T_BANQUET", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "BanquetSeq", sequenceName = "SEQ_BANQUET")
public class Banquet extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 8949342611725075572L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BanquetSeq")
	private Long id;

	@Column(name = "isHalfCatty")
	private Boolean isHalfCatty;

	@Column(name = "processStatus")
	@Enumerated(EnumType.STRING)
	private BanquetProcessStatus processStatus;

	@Column(name = "customerName")
	private String customerName;

	@Column(name = "customerPhone")
	private String customerPhone;

	@Column(name = "receiverName")
	private String receiverName;

	// 订餐人姓名
	@Column(name = "reservationistName")
	private String reservationistName;

	// 订餐人电话
	@Column(name = "reservationistPhone")
	private String reservationistPhone;

	@Column(name = "receiverPhone")
	private String receiverPhone;

	@Column(name = "hostAddress")
	private String hostAddress;

	@Column(name = "receiveAddress")
	private String receiveAddress;

	@Column(name = "hotelKeyPersonWechatID")
	private String hotelKeyPersonWechatID;

	@Column(name = "hotelKeyPersonName")
	private String hotelKeyPersonName;

	@Column(name = "hotelKeyPersonPhone")
	private String hotelKeyPersonPhone;

	@Column(name = "hotelName")
	private String hotelName;

	@Column(name = "hostPhone")
	private String hostPhone;

	// 宴会具体使用时间
	@Column(name = "banquetTime")
	private Calendar banquetTime;

	// 宴会使用时间
	@Column(name = "banquetTimePoint")
	@Enumerated(EnumType.STRING)
	private BanquetTimePoint banquetTimePoint;

	@Column(name = "deliveryTime")
	private Calendar deliveryTime;

	// 兑付时间
	@Column(name = "cashTime")
	private Calendar cashTime;

	@Column(name = "tableCount")
	private Integer tableCount;

	@Column(name = "wishesOnBox")
	private String wishesOnBox;

	@Column(name = "postalCode")
	private String postalCode;

	@Column(name = "applicationType")
	@Enumerated(EnumType.STRING)
	private BanquetApplicationType applicationType;

	@Column(name = "scanType")
	@Enumerated(EnumType.STRING)
	private BanquetScanType scanType;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private BanquetStatus status;

	@Column(name = "paymentStatus")
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	@Column(name = "banquetComment")
	private String banquetComment;

	@Column(name = "approveComment")
	private String approveComment;

	@Column(name = "applyComment")
	private String applyComment;

	private DealPrice dealPrice;

	private PlanQuantity planQuantity;

	private RecycleQuantity recycleQuantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private BanquetType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dealerId")
	private Dealer dealer;

	// 原跟单人
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creatorId")
	private Employee creator;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creatorPositionId")
	private Position creatorPosition;

	// 跟单人
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "handlerId")
	private Employee handler;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "handlerPositionId")
	private Position handlerPosition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityManagerId")
	private Employee cityManager;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityManagerPositionId")
	private Position cityManagerPosition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "officeDirectorId")
	private Employee officeDirector;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "officeDirectorPositionId")
	private Position officeDirectorPosition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "officeId")
	private Department office;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verificationCreatorId")
	private Employee verificationCreator;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verificationCreatorPositionId")
	private Position verificationCreatorPosition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "terminalId")
	private Terminal terminal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materialChannel")
	private Channel materialChannel;

	@OneToMany(mappedBy = "banquet")
	private List<BanquetItem> items;

	@OneToMany(mappedBy = "banquet")
	private List<BanquetPicture> pictures;

	@ManyToMany(mappedBy = "banquets")
	private List<UserReferenceToBanquet> hotelKeyPersonUserReference;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsHalfCatty() {
		return this.isHalfCatty;
	}

	public void setIsHalfCatty(Boolean isHalfCatty) {
		this.isHalfCatty = isHalfCatty;
	}

	public PlanQuantity getPlanQuantity() {
		return this.planQuantity;
	}

	public void setPlanQuantity(PlanQuantity planQuantity) {
		this.planQuantity = planQuantity;
	}

	public RecycleQuantity getRecycleQuantity() {
		return this.recycleQuantity;
	}

	public void setRecycleQuantity(RecycleQuantity recycleQuantity) {
		this.recycleQuantity = recycleQuantity;
	}

	public DealPrice getDealPrice() {
		return this.dealPrice;
	}

	public void setDealPrice(DealPrice dealPrice) {
		this.dealPrice = dealPrice;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return this.customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getReceiverName() {
		return this.receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return this.receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getHostAddress() {
		return this.hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public String getReceiveAddress() {
		return this.receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getHostPhone() {
		return this.hostPhone;
	}

	public void setHostPhone(String hostPhone) {
		this.hostPhone = hostPhone;
	}

	public Calendar getBanquetTime() {
		return this.banquetTime;
	}

	public void setBanquetTime(Calendar banquetTime) {
		this.banquetTime = banquetTime;
	}

	public Calendar getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(Calendar deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getTableCount() {
		return this.tableCount;
	}

	public void setTableCount(Integer tableCount) {
		this.tableCount = tableCount;
	}

	public String getWishesOnBox() {
		return this.wishesOnBox;
	}

	public void setWishesOnBox(String wishesOnBox) {
		this.wishesOnBox = wishesOnBox;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public BanquetStatus getStatus() {
		return this.status;
	}

	public void setStatus(BanquetStatus status) {
		this.status = status;
	}

	public PaymentStatus getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getBanquetComment() {
		return this.banquetComment;
	}

	public void setBanquetComment(String banquetComment) {
		this.banquetComment = banquetComment;
	}

	public String getApproveComment() {
		return this.approveComment;
	}

	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}

	public String getApplyComment() {
		return this.applyComment;
	}

	public void setApplyComment(String applyComment) {
		this.applyComment = applyComment;
	}

	public BanquetType getType() {
		return this.type;
	}

	public void setType(BanquetType type) {
		this.type = type;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Employee getCreator() {
		return this.creator;
	}

	public void setCreator(Employee creator) {
		this.creator = creator;
	}

	public Employee getHandler() {
		return this.handler;
	}

	public void setHandler(Employee handler) {
		this.handler = handler;
	}

	public Employee getCityManager() {
		return this.cityManager;
	}

	public void setCityManager(Employee cityManager) {
		this.cityManager = cityManager;
	}

	public Terminal getTerminal() {
		return this.terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Channel getMaterialChannel() {
		return this.materialChannel;
	}

	public void setMaterialChannel(Channel materialChannel) {
		this.materialChannel = materialChannel;
	}

	public List<BanquetItem> getItems() {
		return this.items;
	}

	public void setItems(List<BanquetItem> items) {
		this.items = items;
	}

	public String getHotelKeyPersonWechatID() {
		return this.hotelKeyPersonWechatID;
	}

	public void setHotelKeyPersonWechatID(String hotelKeyPersonWechatID) {
		this.hotelKeyPersonWechatID = hotelKeyPersonWechatID;
	}

	public String getHotelKeyPersonName() {
		return this.hotelKeyPersonName;
	}

	public void setHotelKeyPersonName(String hotelKeyPersonName) {
		this.hotelKeyPersonName = hotelKeyPersonName;
	}

	public String getHotelKeyPersonPhone() {
		return this.hotelKeyPersonPhone;
	}

	public void setHotelKeyPersonPhone(String hotelKeyPersonPhone) {
		this.hotelKeyPersonPhone = hotelKeyPersonPhone;
	}

	public String getHotelName() {
		return this.hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public BanquetApplicationType getApplicationType() {
		return this.applicationType;
	}

	public void setApplicationType(BanquetApplicationType applicationType) {
		this.applicationType = applicationType;
	}

	public BanquetScanType getScanType() {
		return this.scanType;
	}

	public void setScanType(BanquetScanType scanType) {
		this.scanType = scanType;
	}

	public Position getCreatorPosition() {
		return this.creatorPosition;
	}

	public void setCreatorPosition(Position creatorPosition) {
		this.creatorPosition = creatorPosition;
	}

	public Position getHandlerPosition() {
		return this.handlerPosition;
	}

	public void setHandlerPosition(Position handlerPosition) {
		this.handlerPosition = handlerPosition;
	}

	public Position getCityManagerPosition() {
		return this.cityManagerPosition;
	}

	public void setCityManagerPosition(Position cityManagerPosition) {
		this.cityManagerPosition = cityManagerPosition;
	}

	public Employee getOfficeDirector() {
		return this.officeDirector;
	}

	public void setOfficeDirector(Employee officeDirector) {
		this.officeDirector = officeDirector;
	}

	public Position getOfficeDirectorPosition() {
		return this.officeDirectorPosition;
	}

	public void setOfficeDirectorPosition(Position officeDirectorPosition) {
		this.officeDirectorPosition = officeDirectorPosition;
	}

	public Employee getVerificationCreator() {
		return this.verificationCreator;
	}

	public void setVerificationCreator(Employee verificationCreator) {
		this.verificationCreator = verificationCreator;
	}

	public Position getVerificationCreatorPosition() {
		return this.verificationCreatorPosition;
	}

	public void setVerificationCreatorPosition(Position verificationCreatorPosition) {
		this.verificationCreatorPosition = verificationCreatorPosition;
	}

	public String getReservationistName() {
		return reservationistName;
	}

	public void setReservationistName(String reservationistName) {
		this.reservationistName = reservationistName;
	}

	public String getReservationistPhone() {
		return reservationistPhone;
	}

	public void setReservationistPhone(String reservationistPhone) {
		this.reservationistPhone = reservationistPhone;
	}

	public List<UserReferenceToBanquet> getHotelKeyPersonUserReference() {
		return hotelKeyPersonUserReference;
	}

	public void setHotelKeyPersonUserReference(List<UserReferenceToBanquet> hotelKeyPersonUserReference) {
		this.hotelKeyPersonUserReference = hotelKeyPersonUserReference;
	}

	public BanquetProcessStatus getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(BanquetProcessStatus processStatus) {
		this.processStatus = processStatus;
	}

	public Department getOffice() {
		return office;
	}

	public void setOffice(Department office) {
		this.office = office;
	}

	public BanquetTimePoint getBanquetTimePoint() {
		return banquetTimePoint;
	}

	public void setBanquetTimePoint(BanquetTimePoint banquetTimePoint) {
		this.banquetTimePoint = banquetTimePoint;
	}

	public Calendar getCashTime() {
		return cashTime;
	}

	public void setCashTime(Calendar cashTime) {
		this.cashTime = cashTime;
	}

	public List<BanquetPicture> getPictures() {
		return pictures;
	}

	public void setPictures(List<BanquetPicture> pictures) {
		this.pictures = pictures;
	}
}
