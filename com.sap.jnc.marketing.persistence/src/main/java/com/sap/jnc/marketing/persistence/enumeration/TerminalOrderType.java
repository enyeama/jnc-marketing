package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum TerminalOrderType implements DisplayableEnumModel {

	TERMINALORDER(0, "终端订单"), VTERMINALORDER(1, "虚拟终端订单"), KAORDER(2, "KA订单");

	private final int value;
	private final String label;

	TerminalOrderType(int value, String label) {
		this.value = value;
		this.label = label;
	}

	@Override
	public int getIntValue() {
		return value;
	}

	@Override
	public String getLabel() {
		return label;
	}

	public static TerminalOrderType valueOf(int value) {
		for (TerminalOrderType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
