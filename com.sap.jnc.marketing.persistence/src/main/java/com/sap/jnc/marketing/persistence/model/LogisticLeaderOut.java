package com.sap.jnc.marketing.persistence.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("LEADER_OUT")
public class LogisticLeaderOut extends Logistic {

	private static final long serialVersionUID = 5430678406444093514L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operatorId")
	private Employee leaderEmployee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operatorPositionId")
	private Position leaderPosition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	private TerminalOrder terminalOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderItemId")
	private TerminalOrderItem terminalOrderItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiverId")
	private Terminal terminal;

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
