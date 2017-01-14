package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum ConsumerFollowStatus implements DisplayableEnumModel {

	UNKNOWN(0, "未知");

	private final int value;
	private final String label;

	ConsumerFollowStatus(int value, String label) {
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

	public static ConsumerFollowStatus valueOf(int value) {
		for (ConsumerFollowStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
