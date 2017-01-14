package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum PromoterType implements DisplayableEnumModel {

	ANCU(0, "暗促"), PROMOTE(1, "促销");

	private final int value;
	private final String label;

	PromoterType(int value, String label) {
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

	public static PromoterType valueOf(int value) {
		for (PromoterType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

	public static PromoterType labelOf(String promoterType) {
		for (PromoterType candidate : values()) {
			if (candidate.getLabel().equals(promoterType)) {
				return candidate;
			}
		}
		return null;
	}
}
