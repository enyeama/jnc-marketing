package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum AuditType implements DisplayableEnumModel {

	UNKNOWN(0, "未知"),

	BANQUET(1, "宴会稽核"),

	EXHIBITION(2, "陈列稽核"),

	TERMINAL(3, "终端稽核"),

	PROMOTION(4, "访销稽核");

	private final int value;
	private final String label;

	AuditType(int value, String label) {
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

	public static AuditType valueOf(int value) {
		for (AuditType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
