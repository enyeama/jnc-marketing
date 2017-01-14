package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum TerminalType implements DisplayableEnumModel {

	HOTEL(0, "酒店"), SHOP(1, "名烟名酒店"), KA(2, "KA");

	private final int value;
	private final String label;

	TerminalType(int value, String label) {
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

	public static TerminalType valueOf(int value) {
		for (TerminalType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

	public static TerminalType labelOf(String str) {
		for (TerminalType candidate : values()) {
			if (candidate.getLabel().equals(str)) {
				return candidate;
			}
		}
		return null;
	}

}
