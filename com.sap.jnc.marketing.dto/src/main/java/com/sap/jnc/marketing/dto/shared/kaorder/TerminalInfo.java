package com.sap.jnc.marketing.dto.shared.kaorder;

import java.io.Serializable;

import com.sap.jnc.marketing.dto.response.department.DepartmentResponse;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Terminal;

/**
 * @author Joel.Cheng I310645
 */
public class TerminalInfo implements Serializable {

	private static final long serialVersionUID = 200444102002996928L;

	private DepartmentResponse kaOffice;

	private EmployeeInfo maintainerEmployee;
	private PositionInfo maintainerPosition;

	private Long id;

	private String branchName;

	private String address;

	// 商超系统描述
	private String kaSystemName;

	// 商超系统性质描述
	private String kaSystemPropertyName;

	public TerminalInfo() {
	}

	public TerminalInfo(Terminal terminal) {
		this.setId(terminal.getId());
		this.setBranchName(terminal.getBranchName());
		DepartmentView kaOffice = terminal.getKAOffice();
		if (kaOffice != null) {
			this.setKaOffice(new DepartmentResponse(kaOffice));
		}
		PositionView maintainerPosition = terminal.getMaintainer();
		if (maintainerPosition != null) {
			this.setMaintainerPosition(new PositionInfo(maintainerPosition));
			EmployeeView maintainerEmployee = maintainerPosition.getEmployee();
			if (maintainerEmployee != null) {
				this.setMaintainerEmployee(new EmployeeInfo(maintainerEmployee));
			}
		}
		this.setKaSystemPropertyName(terminal.getKASystemPropertyName());
		this.setKaSystemName(terminal.getKASystemName());
		this.setAddress(terminal.getAddress());
	}

	public DepartmentResponse getKaOffice() {
		return kaOffice;
	}

	public void setKaOffice(DepartmentResponse kaOffice) {
		this.kaOffice = kaOffice;
	}

	public EmployeeInfo getMaintainerEmployee() {
		return maintainerEmployee;
	}

	public void setMaintainerEmployee(EmployeeInfo maintainerEmployee) {
		this.maintainerEmployee = maintainerEmployee;
	}

	public PositionInfo getMaintainerPosition() {
		return maintainerPosition;
	}

	public void setMaintainerPosition(PositionInfo maintainerPosition) {
		this.maintainerPosition = maintainerPosition;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getKaSystemName() {
		return kaSystemName;
	}

	public void setKaSystemName(String kaSystemName) {
		this.kaSystemName = kaSystemName;
	}

	public String getKaSystemPropertyName() {
		return kaSystemPropertyName;
	}

	public void setKaSystemPropertyName(String kaSystemPropertyName) {
		this.kaSystemPropertyName = kaSystemPropertyName;
	}

}
