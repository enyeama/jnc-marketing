package com.sap.jnc.marketing.dto.respose.mainten;

import java.io.Serializable;

import org.apache.commons.collections4.CollectionUtils;

import com.sap.jnc.marketing.persistence.model.Terminal;

/**
 * @author Maggie Liu
 */
public class MaintenTernimalResponse implements Serializable {
	
	private static final long serialVersionUID = -951313183183491506L;
	private Long ternimalId;
	private String ternimalName="";
	private String channelName="";
	private String branchLevel="";
	private String province="";
	private String city="";
	private String county="";
	private String ternimalAddress="";
	private String terminalAllocationType="";
	
	public MaintenTernimalResponse(){
	
	}

	public MaintenTernimalResponse(Terminal terminal) {
		if(terminal==null){
			return;
		}
		if (terminal.getBranchLevel() != null) {
			this.setBranchLevel(terminal.getBranchLevel().getLabel());
		}
		if (terminal.getChannel() != null) {
			this.setChannelName(terminal.getChannel().getName()!=null?terminal.getChannel().getName():"");
		}
		if (terminal.getGps()!= null) {
			if(terminal.getGps().getCity()!=null){
				this.setCity(terminal.getGps().getCity());
			}
			if (terminal.getGps().getCity()!= null) {
				this.setCounty(terminal.getGps().getCounty());
			}
			if (terminal.getGps().getProvince() != null) {
				this.setProvince(terminal.getGps().getProvince());
			}
		}
		
		if (terminal.getAddress() != null) {
			this.setTernimalAddress(terminal.getAddress());
		}
		if (terminal.getId() != null) {
			this.setTernimalId(terminal.getId());
		}
		if (terminal.getBranchName() != null) {
			this.setTernimalName(terminal.getBranchName());
		}
		if(!CollectionUtils.isEmpty(terminal.getSalesmen())){
			this.terminalAllocationType= "ALLOCATED";
		}
		if(CollectionUtils.isEmpty(terminal.getSalesmen())){
			this.terminalAllocationType= "UNALLOCATED";
		}
	}

	public Long getTernimalId() {
		return ternimalId;
	}

	public void setTernimalId(Long ternimalId) {
		this.ternimalId = ternimalId;
	}

	public String getTernimalName() {
		return ternimalName;
	}

	public void setTernimalName(String ternimalName) {
		this.ternimalName = ternimalName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getBranchLevel() {
		return branchLevel;
	}

	public void setBranchLevel(String branchLevel) {
		this.branchLevel = branchLevel;
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

	public String getTernimalAddress() {
		return ternimalAddress;
	}

	public void setTernimalAddress(String ternimalAddress) {
		this.ternimalAddress = ternimalAddress;
	}

	public String getTerminalAllocationType() {
		return terminalAllocationType;
	}

	public void setTerminalAllocationType(String terminalAllocationType) {
		this.terminalAllocationType = terminalAllocationType;
	}

}
