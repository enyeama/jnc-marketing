package com.sap.jnc.marketing.persistence.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("DEALER_OUT")
public class LogisticDealerOutToLeader extends Logistic {

	private static final long serialVersionUID = -1509568768615276675L;

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
	private Employee leaderEmployee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiverPositionId")
	private Position leaderPosition;

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

	public Employee getLeaderEmployee() {
		return leaderEmployee;
	}

	public void setLeaderEmployee(Employee leaderEmployee) {
		this.leaderEmployee = leaderEmployee;
	}

	public Position getLeaderPosition() {
		return leaderPosition;
	}

	public void setLeaderPosition(Position leaderPosition) {
		this.leaderPosition = leaderPosition;
	}
}
