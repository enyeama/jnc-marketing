package com.sap.jnc.marketing.persistence.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("BANQUET")
public class AuditBanquet extends Audit {

	private static final long serialVersionUID = -3461667065781762406L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "targetId")
	private Banquet banquet;

	public Banquet getBanquet() {
		return banquet;
	}

	public void setBanquet(Banquet banquet) {
		this.banquet = banquet;
	}
}
