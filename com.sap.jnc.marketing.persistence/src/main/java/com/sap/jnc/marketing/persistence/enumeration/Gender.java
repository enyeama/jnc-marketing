package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum Gender implements DisplayableEnumModel {

	UNKNOWN(0, "未知"), MALE(1, "男"), FEMALE(2, "女"), SECRET(3, "保密");

	private final int value;
	private final String label;

	Gender(int value, String label) {
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

	public static Gender valueOf(int value) {
		for (Gender candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
