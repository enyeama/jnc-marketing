package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum BanquetItemVerificationStatus implements DisplayableEnumModel {

	QUALIFIED(0, "合格"), DISQUALIFIED(1, "不合格");

	private final int value;
	private final String label;

	BanquetItemVerificationStatus(int value, String label) {
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

	public static BanquetItemVerificationStatus valueOf(int value) {
		for (BanquetItemVerificationStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
