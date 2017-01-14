package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum KAType implements DisplayableEnumModel {

	UNKNOWN(0, "未知");

	private final int value;
	private final String label;

	KAType(int value, String label) {
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

	public static KAType valueOf(int value) {
		for (KAType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

	public static KAType labelOf(String kaType) {
		for (KAType candidate : values()) {
			if (candidate.getLabel().equals(kaType)) {
				return candidate;
			}
		}
		return null;
	}
}
