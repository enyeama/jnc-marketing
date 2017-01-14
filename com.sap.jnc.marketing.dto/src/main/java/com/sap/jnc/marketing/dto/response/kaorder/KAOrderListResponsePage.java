/**
 * 
 */
package com.sap.jnc.marketing.dto.response.kaorder;

import java.io.Serializable;
import java.util.Calendar;

import com.sap.jnc.marketing.dto.response.ka.EmployeeNodeResponse;
import com.sap.jnc.marketing.dto.response.terminal.PositionNode;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderType;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;

/**
 * @author Quansheng Liu I075496
 */
public class KAOrderListResponsePage implements Serializable {

	private static final long serialVersionUID = 5307377008038591334L;

	private Long id;
	private TerminalOrderStatus status;
	private Long terminalId;
	private String branchName;
	private String address;
	private Calendar createOn;
	private String productName;
	private String productDescription;
	private Integer quantity;
	private PositionNode citymanagerPosition;
	private EmployeeNodeResponse citymanager;
	private PositionNode responsibleLeaderPosition;
	private EmployeeNodeResponse responsibleLeader;

	public KAOrderListResponsePage() {

	}

	public KAOrderListResponsePage(TerminalOrder terminalOrder) {
		if (terminalOrder == null || terminalOrder.getType() != TerminalOrderType.KAORDER) {
			return;
		}
		this.setId(terminalOrder.getId());
		this.setStatus(terminalOrder.getStatus());
		Terminal terminal = terminalOrder.getTerminal();
		if (terminal != null) {
			this.setTerminalId(terminal.getId());
			this.setBranchName(terminal.getBranchName());
			this.setAddress(terminal.getAddress());
		}
		Product product = terminalOrder.getProduct();
		if (product != null) {
			this.setProductName(product.getName());
			this.setProductDescription(product.getDescription());
		}
		this.setCreateOn(terminalOrder.getCreateOn());
		this.setQuantity(terminalOrder.getQuantity());
		Employee citymanager = terminalOrder.getCityManagerEmployee();
		if (citymanager != null) {
			this.setCitymanager(new EmployeeNodeResponse(citymanager));
		}
		Position citymanagerPostion = terminalOrder.getCityManagerPosition();
		if (citymanagerPostion != null) {
			this.setCitymanagerPosition(new PositionNode(citymanagerPostion));
		}
		Employee responsibleLeader = terminalOrder.getResponsibleLeader();
		if (responsibleLeader != null) {
			this.setResponsibleLeader(new EmployeeNodeResponse(responsibleLeader));
		}
		Position responsibleLeaderPosition = terminalOrder.getResponsibleLeaderPosition();
		if (responsibleLeaderPosition != null) {
			this.setResponsibleLeaderPosition(new PositionNode(responsibleLeaderPosition));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TerminalOrderStatus getStatus() {
		return status;
	}

	public void setStatus(TerminalOrderStatus status) {
		this.status = status;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
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

	public Calendar getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Calendar createOn) {
		this.createOn = createOn;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public PositionNode getCitymanagerPosition() {
		return citymanagerPosition;
	}

	public void setCitymanagerPosition(PositionNode citymanagerPosition) {
		this.citymanagerPosition = citymanagerPosition;
	}

	public EmployeeNodeResponse getCitymanager() {
		return citymanager;
	}

	public void setCitymanager(EmployeeNodeResponse citymanager) {
		this.citymanager = citymanager;
	}

	public PositionNode getResponsibleLeaderPosition() {
		return responsibleLeaderPosition;
	}

	public void setResponsibleLeaderPosition(PositionNode responsibleLeaderPosition) {
		this.responsibleLeaderPosition = responsibleLeaderPosition;
	}

	public EmployeeNodeResponse getResponsibleLeader() {
		return responsibleLeader;
	}

	public void setResponsibleLeader(EmployeeNodeResponse responsibleLeader) {
		this.responsibleLeader = responsibleLeader;
	}

}
