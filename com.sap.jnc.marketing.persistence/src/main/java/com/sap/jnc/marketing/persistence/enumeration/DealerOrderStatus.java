package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum DealerOrderStatus implements DisplayableEnumModel {

	UNKNOWN(0, "未知");

	private final int value;
	private final String label;

	DealerOrderStatus(int value, String label) {
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

	public static DealerOrderStatus valueOf(int value) {
		for (DealerOrderStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
