package com.sap.jnc.marketing.persistence.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("TERMINAL")
public class AuditTerminal extends Audit {

	private static final long serialVersionUID = 2606901280394337113L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "targetId")
	private Terminal terminal;

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
}
