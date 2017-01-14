package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum RegisterType implements DisplayableEnumModel {

	TERMINAL(0, "TERMINAL_REG"), TERMINAL_ACCOUNT(1, "TERMINAL_ACCOUNT_REG"), SALESMAN(3, "SALESMAN_REG");

	private final int value;
	private final String label;

	RegisterType(int value, String label) {
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

	public static RegisterType valueOf(int value) {
		for (RegisterType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
