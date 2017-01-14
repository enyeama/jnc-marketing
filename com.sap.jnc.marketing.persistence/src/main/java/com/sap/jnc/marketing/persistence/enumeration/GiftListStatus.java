package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum GiftListStatus implements DisplayableEnumModel {

	EXPORTED(0, "已导出"), NOT_EXPORTED(1, "未导出");

	private final int value;
	private final String label;

	GiftListStatus(int value, String label) {
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

	public static GiftListStatus valueOf(int value) {
		for (GiftListStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
