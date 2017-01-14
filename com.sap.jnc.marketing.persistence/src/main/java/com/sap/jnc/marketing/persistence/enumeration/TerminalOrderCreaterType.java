package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum TerminalOrderCreaterType implements DisplayableEnumModel {

	SALESMAN(0, "业务员"), KASALESMAN(1, "KA专员");

	private final int value;
	private final String label;

	TerminalOrderCreaterType(int value, String label) {
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

	public static TerminalOrderCreaterType valueOf(int value) {
		for (TerminalOrderCreaterType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
