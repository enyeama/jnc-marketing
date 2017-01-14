package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

import com.sap.jnc.marketing.persistence.enumeration.BanquetApplicationType;
import com.sap.jnc.marketing.persistence.enumeration.BanquetScanType;
import com.sap.jnc.marketing.persistence.enumeration.BanquetStatus;
import com.sap.jnc.marketing.persistence.enumeration.BanquetTimePoint;
import com.sap.jnc.marketing.persistence.model.Banquet;

/**
 * @author I332242 Frank Zhu
 */
public class BanquetInfoResponse implements Serializable {
	
	private static final long serialVersionUID = 5871071606962975570L;

	private Long id;

	private BanquetTypeInfoResponse banquetType;

	private PlanQuantityInfoResponse planQuantity;

	private String customerName;

	private String customerPhone;

	private Calendar banquetTime;

	private BanquetStatus banquetStatus;

	private String banquetComment;

	private String approveComment;

	private String applyComment;

	private DealerInfoResponse dealer;

	private EmployeeInfoResponse cityManager;

	private TerminalInfoResponse terminal;

	private ProductInfoResponse product;

	private ChannelInfoResponse channel;
	
    private Integer tableCount;
	
	private BanquetScanType scanType;
	
	private BanquetApplicationType applicationType;
	
	private String hostPhone;
	
	private String hostAddress;
	
	private String reservationistName;

	private String reservationistPhone;
	
	private EmployeeInfoResponse creator;
	
	private EmployeeInfoResponse officeDirector;

	private OfficeInfoResponse office;
	
	private BanquetTimePoint timePoint;
	
	private EmployeeInfoResponse handler;
	
	private Calendar cashTime;

	public BanquetInfoResponse() {
	}

	public BanquetInfoResponse(Banquet banquet) {
		if(banquet == null) {
			return;
		}
		if (null != banquet.getId()) {
			this.setId(banquet.getId());
		}
		if (null != banquet.getBanquetTime()) {
			this.setBanquetTime(banquet.getBanquetTime());
		}
		if (null != banquet.getStatus()) {
			this.setBanquetStatus(banquet.getStatus());
		}
		if (StringUtils.isNotBlank(banquet.getApplyComment())) {
			this.setApplyComment(banquet.getApplyComment());
		}
        if (null != banquet.getCityManager()) {
        	this.setCityManager(new EmployeeInfoResponse(banquet.getCityManager()));
        }
        if (StringUtils.isNotBlank(banquet.getCustomerName())) {
        	this.setCustomerName(banquet.getCustomerName());
        }
        if (StringUtils.isNotBlank(banquet.getCustomerPhone())) {
        	this.setCustomerPhone(banquet.getCustomerPhone());
        }
        if (banquet.getMaterialChannel() != null) {
        	this.setChannel(new ChannelInfoResponse(banquet.getMaterialChannel()));
        }
        if (banquet.getProduct() != null) {
        	this.setProduct(new ProductInfoResponse(banquet.getProduct()));
        }
        if (StringUtils.isNotBlank(banquet.getBanquetComment())) {
        	this.setBanquetComment(banquet.getBanquetComment());
        }
        if (StringUtils.isNotBlank(banquet.getApproveComment())) {
        	this.setApproveComment(banquet.getApproveComment());
        }
        if (StringUtils.isNotBlank(banquet.getHostAddress())) {
        	this.setHostAddress(banquet.getHostAddress());
        }
        if (StringUtils.isNotBlank(banquet.getHostPhone())) {
        	this.setHostPhone(banquet.getHostPhone());
        }
        if (banquet.getTableCount() != null) {
        	this.setTableCount(banquet.getTableCount());
        }
        if (banquet.getPlanQuantity() != null) {
        	this.setPlanQuantity(new PlanQuantityInfoResponse(banquet.getPlanQuantity()));
        }
        if (banquet.getApplicationType() != null) {
        	this.setApplicationType(banquet.getApplicationType());
        }
        if (banquet.getScanType() != null) {
        	this.setScanType(banquet.getScanType());
        }
        if (StringUtils.isNotBlank(banquet.getReservationistName())) {
        	this.setReservationistName(banquet.getReservationistName());
        }
        if (StringUtils.isNotBlank(banquet.getReservationistPhone())) {
        	this.setReservationistPhone(banquet.getReservationistPhone());
        }
        if (banquet.getTerminal() != null) {
        	this.setTerminal(new TerminalInfoResponse(banquet.getTerminal()));
        }
        if (banquet.getDealer() != null) {
        	this.setDealer(new DealerInfoResponse(banquet.getDealer()));
        }
        if (banquet.getType() != null) {
        	this.setBanquetType(new BanquetTypeInfoResponse(banquet.getType()));
        }
        if (banquet.getCreator() != null) {
        	this.setCreator(new EmployeeInfoResponse(banquet.getCreator()));
        }
        if (banquet.getApproveComment() != null) {
        	this.setApproveComment(banquet.getApproveComment());
        }
		if (banquet.getOffice() != null) {
			this.setOffice(new OfficeInfoResponse(banquet.getOffice()));
        }
        if (banquet.getBanquetTimePoint() != null) {
        	this.setTimePoint(banquet.getBanquetTimePoint());
        }
        if (banquet.getHandler() != null) {
        	this.setHandler(new EmployeeInfoResponse(banquet.getHandler()));
        }
		if (banquet.getCashTime() != null) {
			this.setCashTime(banquet.getCashTime());
		}
		if (banquet.getOfficeDirector() != null) {
			this.setOfficeDirector(new EmployeeInfoResponse(banquet.getOfficeDirector()));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Calendar getBanquetTime() {
		return banquetTime;
	}

	public void setBanquetTime(Calendar banquetTime) {
		this.banquetTime = banquetTime;
	}

	public String getBanquetComment() {
		return banquetComment;
	}

	public void setBanquetComment(String banquetComment) {
		this.banquetComment = banquetComment;
	}

	public String getApproveComment() {
		return approveComment;
	}

	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
	}

	public String getApplyComment() {
		return applyComment;
	}

	public void setApplyComment(String applyComment) {
		this.applyComment = applyComment;
	}

	public Integer getTableCount() {
		return tableCount;
	}

	public void setTableCount(Integer tableCount) {
		this.tableCount = tableCount;
	}

	public String getHostPhone() {
		return hostPhone;
	}

	public void setHostPhone(String hostPhone) {
		this.hostPhone = hostPhone;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
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

	public DealerInfoResponse getDealer() {
		return dealer;
	}

	public void setDealer(DealerInfoResponse dealer) {
		this.dealer = dealer;
	}

	public EmployeeInfoResponse getCityManager() {
		return cityManager;
	}

	public void setCityManager(EmployeeInfoResponse cityManager) {
		this.cityManager = cityManager;
	}

	public TerminalInfoResponse getTerminal() {
		return terminal;
	}

	public void setTerminal(TerminalInfoResponse terminal) {
		this.terminal = terminal;
	}

	public ProductInfoResponse getProduct() {
		return product;
	}

	public void setProduct(ProductInfoResponse product) {
		this.product = product;
	}

	public ChannelInfoResponse getChannel() {
		return channel;
	}

	public void setChannel(ChannelInfoResponse channel) {
		this.channel = channel;
	}

	public PlanQuantityInfoResponse getPlanQuantity() {
		return planQuantity;
	}

	public void setPlanQuantity(PlanQuantityInfoResponse planQuantity) {
		this.planQuantity = planQuantity;
	}

	public BanquetTypeInfoResponse getBanquetType() {
		return banquetType;
	}

	public void setBanquetType(BanquetTypeInfoResponse banquetType) {
		this.banquetType = banquetType;
	}

	public EmployeeInfoResponse getCreator() {
		return creator;
	}

	public void setCreator(EmployeeInfoResponse creator) {
		this.creator = creator;
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

	public BanquetTimePoint getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(BanquetTimePoint timePoint) {
		this.timePoint = timePoint;
	}

	public EmployeeInfoResponse getHandler() {
		return handler;
	}

	public void setHandler(EmployeeInfoResponse handler) {
		this.handler = handler;
	}

	public Calendar getCashTime() {
		return cashTime;
	}

	public void setCashTime(Calendar cashTime) {
		this.cashTime = cashTime;
	}

	public EmployeeInfoResponse getOfficeDirector() {
		return officeDirector;
	}

	public void setOfficeDirector(EmployeeInfoResponse officeDirector) {
		this.officeDirector = officeDirector;
	}

	public OfficeInfoResponse getOffice() {
		return office;
	}

	public void setOffice(OfficeInfoResponse office) {
		this.office = office;
	}
}
