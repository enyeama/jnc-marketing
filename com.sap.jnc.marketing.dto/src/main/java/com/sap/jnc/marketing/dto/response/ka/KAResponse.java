/**
 * 
 */
package com.sap.jnc.marketing.dto.response.ka;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.sap.jnc.marketing.persistence.enumeration.BranchLevel;
import com.sap.jnc.marketing.persistence.enumeration.TerminalType;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.GPS;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Region;
import com.sap.jnc.marketing.persistence.model.Terminal;

/**
 * @author Quansheng Liu I075496
 */
public class KAResponse implements Serializable {

	private static final long serialVersionUID = -8961902003338156477L;

	private Long id;
	private KAOfficeResponse kaOffice;
	private String branchName;
	private String address;
	private String storeNumber;
	private String kaSystemNumber;
	private String kaSystemName;
	private PositionNodeResponse accountManagerPosition;
	private EmployeeNodeResponse accountManagerEmployee;
	private PositionNodeResponse specialistPosition;
	private EmployeeNodeResponse specialistEmployee;
	private PositionNodeResponse maintainerPosition;
	private EmployeeNodeResponse maintainerEmployee;
	private String country;
	private String province;
	private String city;
	private RegionNodeResponse region;
	private String businessStatus;
	private String channelNumber;
	private String channelName;
	private BranchLevel branchLevel;
	private String systemPropertyNumber;
	private String systemPropertyName;

	public KAResponse() {

	}

	public KAResponse(Terminal terminal) {
		if (terminal == null || terminal.getType() != TerminalType.KA) {
			return;
		}
		this.setId(terminal.getId());
		this.setAddress(terminal.getAddress());
		this.setBranchLevel(terminal.getBranchLevel());
		this.setBranchName(terminal.getBranchName());
		this.setBusinessStatus(terminal.getBusinessStatus());
		this.setChannelNumber(terminal.getChannelNumber());
		this.setChannelName(terminal.getChannelName());
		GPS gps = terminal.getGps();
		if (gps != null) {
			this.setCity(gps.getCity());
			this.setCountry(gps.getCounty());
			this.setProvince(gps.getProvince());
		}
		Region region = terminal.getRegion();
		if (region != null) {
			this.setRegion(new RegionNodeResponse(region));
		}
		this.setKaSystemName(terminal.getKASystemName());
		this.setKaSystemNumber(terminal.getKASystemNumber());
		this.setStoreNumber(terminal.getStoreNumber());
		this.setSystemPropertyName(terminal.getKASystemPropertyName());
		this.setKaSystemNumber(terminal.getKASystemPropertyNumber());
		DepartmentView departmentView = terminal.getKAOffice();
		if (departmentView != null) {
			this.setKaOffice(new KAOfficeResponse(departmentView));
		}
		PositionView accountManagerPositionView = terminal.getKAAcountManager();
		if (accountManagerPositionView != null) {
			this.setAccountManagerPosition(new PositionNodeResponse(accountManagerPositionView));
			List<EmployeeView> employeeList = accountManagerPositionView.getEmployees();
			if (!CollectionUtils.isEmpty(employeeList)) {
				this.setAccountManagerEmployee(new EmployeeNodeResponse(employeeList.get(0)));
			}
		}
		PositionView specialistPositionView = terminal.getKASpecialist();
		if (specialistPositionView != null) {
			this.setSpecialistPosition(new PositionNodeResponse(specialistPositionView));
			List<EmployeeView> employeeList = specialistPositionView.getEmployees();
			if (!CollectionUtils.isEmpty(employeeList)) {
				this.setSpecialistEmployee(new EmployeeNodeResponse(employeeList.get(0)));
			}
		}
		PositionView maintainerPositionView = terminal.getMaintainer();
		if (maintainerPositionView != null) {
			this.setMaintainerPosition(new PositionNodeResponse(maintainerPositionView));
			List<EmployeeView> employeeList = maintainerPositionView.getEmployees();
			if (!CollectionUtils.isEmpty(employeeList)) {
				this.setMaintainerEmployee(new EmployeeNodeResponse(employeeList.get(0)));
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public KAOfficeResponse getKaOffice() {
		return kaOffice;
	}

	public void setKaOffice(KAOfficeResponse kaOffice) {
		this.kaOffice = kaOffice;
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

	public PositionNodeResponse getAccountManagerPosition() {
		return accountManagerPosition;
	}

	public void setAccountManagerPosition(PositionNodeResponse accountManagerPosition) {
		this.accountManagerPosition = accountManagerPosition;
	}

	public EmployeeNodeResponse getAccountManagerEmployee() {
		return accountManagerEmployee;
	}

	public void setAccountManagerEmployee(EmployeeNodeResponse accountManagerEmployee) {
		this.accountManagerEmployee = accountManagerEmployee;
	}

	public PositionNodeResponse getSpecialistPosition() {
		return specialistPosition;
	}

	public void setSpecialistPosition(PositionNodeResponse specialistPosition) {
		this.specialistPosition = specialistPosition;
	}

	public EmployeeNodeResponse getSpecialistEmployee() {
		return specialistEmployee;
	}

	public void setSpecialistEmployee(EmployeeNodeResponse specialistEmployee) {
		this.specialistEmployee = specialistEmployee;
	}

	public PositionNodeResponse getMaintainerPosition() {
		return maintainerPosition;
	}

	public void setMaintainerPosition(PositionNodeResponse maintainerPosition) {
		this.maintainerPosition = maintainerPosition;
	}

	public EmployeeNodeResponse getMaintainerEmployee() {
		return maintainerEmployee;
	}

	public void setMaintainerEmployee(EmployeeNodeResponse maintainerEmployee) {
		this.maintainerEmployee = maintainerEmployee;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public RegionNodeResponse getRegion() {
		return region;
	}

	public void setRegion(RegionNodeResponse region) {
		this.region = region;
	}

	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
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

}
