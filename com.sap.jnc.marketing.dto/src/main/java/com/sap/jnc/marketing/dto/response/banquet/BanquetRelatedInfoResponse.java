package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sap.jnc.marketing.persistence.enumeration.BanquetApplicationType;
import com.sap.jnc.marketing.persistence.enumeration.BanquetScanType;
import com.sap.jnc.marketing.persistence.enumeration.BanquetTimePoint;
import com.sap.jnc.marketing.persistence.enumeration.TerminalType;

/**
 * @author I332242 Zhu Qiang
 */
public class BanquetRelatedInfoResponse implements Serializable {

	private static final long serialVersionUID = -3592294644560730917L;
	
	private DealerInfoResponse dealerInfoResponse;
	
	private Set<ProductInfoResponse> productInfoResponse;
	
	private List<EmployeeInfoResponse> cityManagerInfoResponse;
	
	private List<EmployeeInfoResponse> salesManInfoResponse;
	
	private List<TerminalInfoResponse> terminalInfoResponse;
	
	private Set<ChannelInfoResponse> channel;
	
	private List<BanquetTypeInfoResponse> banquetTypes;
	
	private List<BanquetApplicationType> banquetApplyTypes;
	
	private List<BanquetScanType> banquetScanTypes;
	
	private List<TerminalType> terminalTypes;
	
	private List<BanquetTimePoint> timePointTypes;
	
	public BanquetRelatedInfoResponse () {
		
	}
	
	public DealerInfoResponse getDealerInfoResponse() {
		if (null == dealerInfoResponse) {
			dealerInfoResponse = new DealerInfoResponse();
		}
		return dealerInfoResponse;
	}

	public void setDealerInfoResponse(DealerInfoResponse dealerInfoResponse) {
		if (null != dealerInfoResponse) {
			this.dealerInfoResponse = dealerInfoResponse;
		}
	}

	public Set<ProductInfoResponse> getProductInfoResponse() {
		if (null == productInfoResponse) {
			productInfoResponse = new HashSet<ProductInfoResponse>();
		}
		return productInfoResponse;
	}

	public void setProductInfoResponse(Set<ProductInfoResponse> productInfoResponse) {
		if (null != productInfoResponse) {
			this.productInfoResponse = productInfoResponse;
		}
	}

	public List<TerminalInfoResponse> getTerminalInfoResponse() {
		if (null == terminalInfoResponse) {
			terminalInfoResponse = new ArrayList<TerminalInfoResponse>();
		}
		return terminalInfoResponse;
	}

	public void setTerminalInfoResponse(List<TerminalInfoResponse> terminalInfoResponse) {
		if (null != terminalInfoResponse) {
			this.terminalInfoResponse = terminalInfoResponse;
		}
	}

	public List<EmployeeInfoResponse> getCityManagerInfoResponse() {
		if (null == cityManagerInfoResponse) {
			cityManagerInfoResponse= new ArrayList<EmployeeInfoResponse>();
		}
		return cityManagerInfoResponse;
	}

	public void setCityManagerInfoResponse(List<EmployeeInfoResponse> cityManagerInfoResponse) {
		if (null != cityManagerInfoResponse) {
			this.cityManagerInfoResponse = cityManagerInfoResponse;
		}
	}

	public List<EmployeeInfoResponse> getSalesManInfoResponse() {
		if (null == salesManInfoResponse) {
			salesManInfoResponse= new ArrayList<EmployeeInfoResponse>();
		}
		return salesManInfoResponse;
	}

	public void setSalesManInfoResponse(List<EmployeeInfoResponse> salesManInfoResponse) {
		if (null != salesManInfoResponse) {
			this.salesManInfoResponse = salesManInfoResponse;
		}
	}

	public Set<ChannelInfoResponse> getChannel() {
		if (null == channel) {
			channel = new HashSet<ChannelInfoResponse>();
		}
		return channel;
	}

	public void setChannel(Set<ChannelInfoResponse> channel) {
		if (null != channel) {
			this.channel = channel;
		}
	}

	public List<BanquetTypeInfoResponse> getBanquetTypes() {
		return banquetTypes;
	}

	public void setBanquetTypes(List<BanquetTypeInfoResponse> banquetTypes) {
		this.banquetTypes = banquetTypes;
	}

	public List<BanquetApplicationType> getBanquetApplyTypes() {
		return banquetApplyTypes;
	}

	public void setBanquetApplyTypes(List<BanquetApplicationType> banquetApplyTypes) {
		this.banquetApplyTypes = banquetApplyTypes;
	}

	public List<BanquetScanType> getBanquetScanTypes() {
		return banquetScanTypes;
	}

	public void setBanquetScanTypes(List<BanquetScanType> banquetScanTypes) {
		this.banquetScanTypes = banquetScanTypes;
	}

	public List<TerminalType> getTerminalTypes() {
		return terminalTypes;
	}

	public void setTerminalTypes(List<TerminalType> terminalTypes) {
		this.terminalTypes = terminalTypes;
	}

	public List<BanquetTimePoint> getTimePointTypes() {
		return timePointTypes;
	}

	public void setTimePointTypes(List<BanquetTimePoint> timePointTypes) {
		this.timePointTypes = timePointTypes;
	}
}
