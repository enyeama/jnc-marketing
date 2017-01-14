package com.sap.jnc.marketing.dto.response.dealer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;

public class DealerOrderPageResponse extends PageImpl<DealerOrderPageResponse.Item> implements Serializable {

	private static final long serialVersionUID = 7771641261755102740L;
	
	private static final List<DealerOrderPageResponse.Item> EMPTY_LIST = new ArrayList<>(0);
	
	public DealerOrderPageResponse(PageRequest pageRequest) {
		super(
			// Content
			EMPTY_LIST,
			// Page Request
			pageRequest,
			// Total,
			0);
	}
	
	public DealerOrderPageResponse(Page<TerminalOrder> pages, PageRequest pageRequest) {
		super(
			// Content
			pages.getContent().stream().map(order -> new Item(order)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}
	
	public static class Item implements Serializable {

		private static final long serialVersionUID = -1946599519731198199L;
		
		private String orderId;
		
		private String statusText;
		
		private TerminalOrderStatus status;
		
		private String terminal;
		
		private String product;
		
		private int cnt;
		
		private String address;
		
		private String tel;
		
		public Item() {
		}
		
		public Item(TerminalOrder entity) {
			this.orderId = entity.getId().toString();
			this.status = entity.getStatus();
			this.statusText = entity.getStatus().getLabel();
			this.cnt = entity.getQuantity();
			if (entity.getTerminal() != null) {
				this.terminal = entity.getTerminal().getTitle();
				this.address = entity.getTerminal().getAddress();
				this.tel = entity.getTerminal().getPhone();
			}
			if (entity.getProduct() != null) {
				this.product = entity.getProduct().getName();
			}
		}
		
		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public TerminalOrderStatus getStatus() {
			return status;
		}

		public void setStatus(TerminalOrderStatus status) {
			this.status = status;
		}

		public String getTerminal() {
			return terminal;
		}

		public void setTerminal(String terminal) {
			this.terminal = terminal;
		}

		public String getProduct() {
			return product;
		}

		public void setProduct(String product) {
			this.product = product;
		}

		public int getCnt() {
			return cnt;
		}

		public void setCnt(int cnt) {
			this.cnt = cnt;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getStatusText() {
			return statusText;
		}

		public void setStatusText(String statusText) {
			this.statusText = statusText;
		}
	}
}
