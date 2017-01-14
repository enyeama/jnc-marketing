package com.sap.jnc.marketing.persistence.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("TERMINAL")
public class UserReferenceToTerminal extends UserReference {

	private static final long serialVersionUID = 5522519799131224670L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "referenceId")
	private Terminal terminal;

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
}
