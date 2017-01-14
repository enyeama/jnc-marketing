package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum BanquetTimePoint implements DisplayableEnumModel {

	NOON(0, "中午"), NIGHT(1, "晚上"), NOON_AND_NIGHT(2, "中午和晚上");

	private final int value;
	private final String label;

	BanquetTimePoint(int value, String label) {
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

	public static BanquetTimePoint valueOf(int value) {
		for (BanquetTimePoint candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
