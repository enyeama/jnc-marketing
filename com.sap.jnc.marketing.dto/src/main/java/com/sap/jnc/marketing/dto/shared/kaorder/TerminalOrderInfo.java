package com.sap.jnc.marketing.dto.shared.kaorder;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.collections.CollectionUtils;

import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderCreaterType;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderType;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;

/**
 * @author Joel.Cheng I310645
 */
public class TerminalOrderInfo implements Serializable {

	private static final long serialVersionUID = -2267064398420240661L;

	private Calendar orderTime;

	private String productDmsCategory;

	private Long id;

	private TerminalOrderType type;

	private TerminalOrderStatus status;

	private TerminalOrderCreaterType createrType;

	private String createrId;

	private String complaint;

	private String comment;

	private ProductInfo productInfo;

	private Integer quantity;

	private EmployeeInfo createrEmployeeInfo;

	private PositionInfo createrPositionInfo;

	private TerminalInfo terminalInfo;

	private DealerInfo dealerInfo;

	private EmployeeInfo responsibleLeaderInfo;

	private PositionInfo responsibleLeaderPositionInfo;

	private EmployeeInfo cityManagerEmployeeInfo;

	private PositionInfo cityManagerPositionInfo;

	public TerminalOrderInfo() {

	}

	public TerminalOrderInfo(TerminalOrder terminalOrder) {
		if (terminalOrder == null) {
			return;
		}
		this.setId(terminalOrder.getId());
		this.setType(terminalOrder.getType());
		this.setStatus(terminalOrder.getStatus());
		this.setCreaterType(terminalOrder.getCreaterType());
		this.setCreaterId(terminalOrder.getCreaterId());
		this.setComplaint(terminalOrder.getComplaint());
		Product product = terminalOrder.getProduct();
		if (product != null) {
			ProductInfo productInfo = new ProductInfo(product);
			this.setProductInfo(productInfo);
			if (!CollectionUtils.isEmpty(product.getProductDmsCategories())) {
				this.setProductDmsCategory(product.getProductDmsCategories().get(0).getName());
			}
		}

		this.setQuantity(terminalOrder.getQuantity());
		this.setOrderTime(terminalOrder.getCreateOn());

		Terminal terminal = terminalOrder.getTerminal();
		if (terminal != null) {
			this.setTerminalInfo(new TerminalInfo(terminal));
		}

		Dealer dealer = terminalOrder.getDealer();
		if (dealer != null) {
			DealerInfo dealerInfo = new DealerInfo(dealer);
			this.setDealerInfo(dealerInfo);
		}

		Employee responsibleLeaderEmployee = terminalOrder.getResponsibleLeader();
		if (responsibleLeaderEmployee != null) {
			EmployeeInfo responsibleLeaderInfo = new EmployeeInfo(responsibleLeaderEmployee);
			this.setResponsibleLeaderInfo(responsibleLeaderInfo);
		}
		Position responsibleLeaderPosition = terminalOrder.getResponsibleLeaderPosition();
		if (responsibleLeaderPosition != null) {
			this.setResponsibleLeaderPositionInfo(new PositionInfo(responsibleLeaderPosition));
		}
		Employee cityManagerEmployee = terminalOrder.getCityManagerEmployee();
		if (cityManagerEmployee != null) {
			this.setCityManagerEmployeeInfo(new EmployeeInfo(cityManagerEmployee));
		}
		Position cityManagerPosition = terminalOrder.getCityManagerPosition();
		if (cityManagerPosition != null) {
			this.setCityManagerPositionInfo(new PositionInfo(cityManagerPosition));
		}

		Employee createrEmployee = terminalOrder.getCreaterEmployee();
		if (createrEmployee != null) {
			this.setCreaterEmployeeInfo(new EmployeeInfo(createrEmployee));
		}
		Position createrPosition = terminalOrder.getCreaterPosition();
		if (createrPosition != null) {
			this.setCreaterPositionInfo(new PositionInfo(createrPosition));
		}

		this.setComment(terminalOrder.getComment());

	}

	public Calendar getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Calendar orderTime) {
		this.orderTime = orderTime;
	}

	public String getProductDmsCategory() {
		return productDmsCategory;
	}

	public void setProductDmsCategory(String productDmsCategory) {
		this.productDmsCategory = productDmsCategory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TerminalOrderType getType() {
		return type;
	}

	public void setType(TerminalOrderType type) {
		this.type = type;
	}

	public TerminalOrderStatus getStatus() {
		return status;
	}

	public void setStatus(TerminalOrderStatus status) {
		this.status = status;
	}

	public TerminalOrderCreaterType getCreaterType() {
		return createrType;
	}

	public void setCreaterType(TerminalOrderCreaterType createrType) {
		this.createrType = createrType;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getComplaint() {
		return complaint;
	}

	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public EmployeeInfo getCreaterEmployeeInfo() {
		return createrEmployeeInfo;
	}

	public void setCreaterEmployeeInfo(EmployeeInfo createrEmployeeInfo) {
		this.createrEmployeeInfo = createrEmployeeInfo;
	}

	public PositionInfo getCreaterPositionInfo() {
		return createrPositionInfo;
	}

	public void setCreaterPositionInfo(PositionInfo createrPositionInfo) {
		this.createrPositionInfo = createrPositionInfo;
	}

	public TerminalInfo getTerminalInfo() {
		return terminalInfo;
	}

	public void setTerminalInfo(TerminalInfo terminalInfo) {
		this.terminalInfo = terminalInfo;
	}

	public DealerInfo getDealerInfo() {
		return dealerInfo;
	}

	public void setDealerInfo(DealerInfo dealerInfo) {
		this.dealerInfo = dealerInfo;
	}

	public EmployeeInfo getResponsibleLeaderInfo() {
		return responsibleLeaderInfo;
	}

	public void setResponsibleLeaderInfo(EmployeeInfo responsibleLeaderInfo) {
		this.responsibleLeaderInfo = responsibleLeaderInfo;
	}

	public PositionInfo getResponsibleLeaderPositionInfo() {
		return responsibleLeaderPositionInfo;
	}

	public void setResponsibleLeaderPositionInfo(PositionInfo responsibleLeaderPositionInfo) {
		this.responsibleLeaderPositionInfo = responsibleLeaderPositionInfo;
	}

	public EmployeeInfo getCityManagerEmployeeInfo() {
		return cityManagerEmployeeInfo;
	}

	public void setCityManagerEmployeeInfo(EmployeeInfo cityManagerEmployeeInfo) {
		this.cityManagerEmployeeInfo = cityManagerEmployeeInfo;
	}

	public PositionInfo getCityManagerPositionInfo() {
		return cityManagerPositionInfo;
	}

	public void setCityManagerPositionInfo(PositionInfo cityManagerPositionInfo) {
		this.cityManagerPositionInfo = cityManagerPositionInfo;
	}

}
