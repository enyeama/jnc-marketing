package com.sap.jnc.marketing.persistence.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("DEALERTOTERMINAL_TERMINAL_IN")
public class LogisticTerminalInFromDealer extends Logistic {

	private static final long serialVersionUID = 2183378413198058400L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operatorId")
	private Terminal terminal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	private TerminalOrder terminalOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderItemId")
	private TerminalOrderItem terminalOrderItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delegatorId")
	private Employee dalegatorEmployee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delegatorPositionId")
	private Position dalegatorPosition;

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public TerminalOrder getTerminalOrder() {
		return terminalOrder;
	}

	public void setTerminalOrder(TerminalOrder terminalOrder) {
		this.terminalOrder = terminalOrder;
	}

	public TerminalOrderItem getTerminalOrderItem() {
		return terminalOrderItem;
	}

	public void setTerminalOrderItem(TerminalOrderItem terminalOrderItem) {
		this.terminalOrderItem = terminalOrderItem;
	}

	public Employee getDalegatorEmployee() {
		return dalegatorEmployee;
	}

	public void setDalegatorEmployee(Employee dalegatorEmployee) {
		this.dalegatorEmployee = dalegatorEmployee;
	}

	public Position getDalegatorPosition() {
		return dalegatorPosition;
	}

	public void setDalegatorPosition(Position dalegatorPosition) {
		this.dalegatorPosition = dalegatorPosition;
	}
}
