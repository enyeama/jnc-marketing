package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum ContractStatus implements DisplayableEnumModel {

UNKNOWN(0, "未知"), ACTIVE(1, "可用"), INACTIVE(2, "不可用");

	private final int value;
	private final String label;

	ContractStatus(int value, String label) {
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

	public static ContractStatus valueOf(int value) {
		for (ContractStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
