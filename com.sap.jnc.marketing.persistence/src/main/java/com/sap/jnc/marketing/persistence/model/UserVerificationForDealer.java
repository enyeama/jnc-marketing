package com.sap.jnc.marketing.persistence.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("DEALER")
public class UserVerificationForDealer extends UserVerification {

	private static final long serialVersionUID = -1762376197099366972L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "referenceId")
	private Dealer dealer;

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}
}
