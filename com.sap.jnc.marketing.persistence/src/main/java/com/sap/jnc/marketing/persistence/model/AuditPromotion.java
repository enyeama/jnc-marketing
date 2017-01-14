package com.sap.jnc.marketing.persistence.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("PROMOTION")
public class AuditPromotion extends Audit {

	private static final long serialVersionUID = 3882077701214693238L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "targetId")
	private TerminalOrder terminalOrder;

	public TerminalOrder getTerminalOrder() {
		return terminalOrder;
	}

	public void setTerminalOrder(TerminalOrder terminalOrder) {
		this.terminalOrder = terminalOrder;
	}
}
