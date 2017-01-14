package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;
import java.util.Calendar;

import com.sap.jnc.marketing.persistence.enumeration.BanquetApplicationType;
import com.sap.jnc.marketing.persistence.enumeration.BanquetScanType;
import com.sap.jnc.marketing.persistence.enumeration.BanquetStatus;
import com.sap.jnc.marketing.persistence.enumeration.BanquetTimePoint;
import com.sap.jnc.marketing.persistence.model.BanquetType;

/**
 * @author I332242 Zhu Qiang
 */
public class BanquetMergeRequest implements Serializable {

	private static final long serialVersionUID = 5871071606962975570L;

	private Long id;

	private BanquetType banquetType;

	private PlanQuantityInfoRequest planQuantity;

	private String customerName;

	private String customerPhone;

	private String hostAddress;

	private String hostPhone;

	private Calendar banquetTime;

	private Integer paymentStatus;

	private BanquetStatus banquetStatus;

	private String banquetComment;

	private DealerInfoRequest dealer;

	private EmployeeInfoRequest creator;

	private EmployeeInfoRequest handler;

	private EmployeeInfoRequest cityManager;

	private TerminalInfoRequest terminal;

	private ProductInfoRequest product;

	private ChannelInfoRequest channel;
	
	private Calendar banquetTimeFrom;
	
	private Calendar banquetTimeTo;
	
	private Integer tableCount;
	
	private BanquetScanType scanType;
	
	private BanquetApplicationType applicationType;
	
	private String reservationistName;

	private String reservationistPhone;
	
	private String officeDirector;
	
	private String approveComment;
	
	private BanquetTimePoint timePoint;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BanquetType getBanquetType() {
		return banquetType;
	}

	public void setBanquetType(BanquetType banquetType) {
		this.banquetType = banquetType;
	}


	public Integer getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getBanquetComment() {
		return banquetComment;
	}

	public void setBanquetComment(String banquetComment) {
		this.banquetComment = banquetComment;
	}

	public Calendar getBanquetTime() {
		return banquetTime;
	}

	public void setBanquetTime(Calendar banquetTime) {
		this.banquetTime = banquetTime;
	}

	public Calendar getBanquetTimeFrom() {
		return banquetTimeFrom;
	}

	public void setBanquetTimeFrom(Calendar banquetTimeFrom) {
		this.banquetTimeFrom = banquetTimeFrom;
	}

	public Calendar getBanquetTimeTo() {
		return banquetTimeTo;
	}

	public void setBanquetTimeTo(Calendar banquetTimeTo) {
		this.banquetTimeTo = banquetTimeTo;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public String getHostPhone() {
		return hostPhone;
	}

	public void setHostPhone(String hostPhone) {
		this.hostPhone = hostPhone;
	}

	public Integer getTableCount() {
		return tableCount;
	}

	public void setTableCount(Integer tableCount) {
		this.tableCount = tableCount;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getOfficeDirector() {
		return officeDirector;
	}

	public void setOfficeDirector(String officeDirector) {
		this.officeDirector = officeDirector;
	}

	public BanquetStatus getBanquetStatus() {
		return banquetStatus;
	}

	public void setBanquetStatus(BanquetStatus banquetStatus) {
		this.banquetStatus = banquetStatus;
	}

	public BanquetScanType getScanType() {
		return scanType;
	}

	public void setScanType(BanquetScanType scanType) {
		this.scanType = scanType;
	}

	public BanquetApplicationType getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(BanquetApplicationType applicationType) {
		this.applicationType = applicationType;
	}

	public String getApproveComment() {
		return approveComment;
	}

	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}

	public BanquetTimePoint getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(BanquetTimePoint timePoint) {
		this.timePoint = timePoint;
	}

	public PlanQuantityInfoRequest getPlanQuantity() {
		return planQuantity;
	}

	public void setPlanQuantity(PlanQuantityInfoRequest planQuantity) {
		this.planQuantity = planQuantity;
	}

	public DealerInfoRequest getDealer() {
		return dealer;
	}

	public void setDealer(DealerInfoRequest dealer) {
		this.dealer = dealer;
	}

	public EmployeeInfoRequest getCreator() {
		return creator;
	}

	public void setCreator(EmployeeInfoRequest creator) {
		this.creator = creator;
	}

	public EmployeeInfoRequest getHandler() {
		return handler;
	}

	public void setHandler(EmployeeInfoRequest handler) {
		this.handler = handler;
	}

	public EmployeeInfoRequest getCityManager() {
		return cityManager;
	}

	public void setCityManager(EmployeeInfoRequest cityManager) {
		this.cityManager = cityManager;
	}

	public TerminalInfoRequest getTerminal() {
		return terminal;
	}

	public void setTerminal(TerminalInfoRequest terminal) {
		this.terminal = terminal;
	}

	public ProductInfoRequest getProduct() {
		return product;
	}

	public void setProduct(ProductInfoRequest product) {
		this.product = product;
	}

	public ChannelInfoRequest getChannel() {
		return channel;
	}

	public void setChannel(ChannelInfoRequest channel) {
		this.channel = channel;
	}
}
