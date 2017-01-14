package com.sap.jnc.marketing.persistence.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("JNC_OUT")
public class LogisticJncOut extends Logistic {

	private static final long serialVersionUID = 6870376083753157292L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiverId")
	private Dealer dealer;

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}
}
