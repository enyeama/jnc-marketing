package com.sap.jnc.marketing.persistence.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("DEALER_IN")
public class LogisticDealerIn extends Logistic {

	private static final long serialVersionUID = 985767323708073341L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operatorId")
	private Dealer dealer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	private DealerOrder dealerOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderItemId")
	private DealerOrderItem dealerOrderItem;

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public DealerOrder getDealerOrder() {
		return dealerOrder;
	}

	public void setDealerOrder(DealerOrder dealerOrder) {
		this.dealerOrder = dealerOrder;
	}

	public DealerOrderItem getDealerOrderItem() {
		return dealerOrderItem;
	}

	public void setDealerOrderItem(DealerOrderItem dealerOrderItem) {
		this.dealerOrderItem = dealerOrderItem;
	}
}
