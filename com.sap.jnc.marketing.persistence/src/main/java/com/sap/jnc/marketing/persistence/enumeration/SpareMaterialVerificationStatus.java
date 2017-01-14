package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum SpareMaterialVerificationStatus implements DisplayableEnumModel {

	NOT_VERIFIED(0, "未核销"), VERIFIED(1, "已核销");

	private final int value;
	private final String label;

	SpareMaterialVerificationStatus(int value, String label) {
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

	public static SpareMaterialVerificationStatus valueOf(int value) {
		for (SpareMaterialVerificationStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
