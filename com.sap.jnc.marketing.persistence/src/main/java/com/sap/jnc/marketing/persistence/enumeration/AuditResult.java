package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum AuditResult implements DisplayableEnumModel {

	CORRECT(1, "稽核通过"), FAILD(2, "稽核不通过");

	private final int value;
	private final String label;

	AuditResult(int value, String label) {
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

	public static AuditResult valueOf(int value) {
		for (AuditResult candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
