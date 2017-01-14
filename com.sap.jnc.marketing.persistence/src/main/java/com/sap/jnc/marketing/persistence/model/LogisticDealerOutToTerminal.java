package com.sap.jnc.marketing.persistence.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("DEALERTOTERMINAL_DEALER_OUT")
public class LogisticDealerOutToTerminal extends Logistic {

	private static final long serialVersionUID = -7858052715820021452L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operatorId")
	private Dealer dealer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	private TerminalOrder terminalOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderItemId")
	private TerminalOrderItem terminalOrderItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiverId")
	private Terminal terminal;

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
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

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
}
