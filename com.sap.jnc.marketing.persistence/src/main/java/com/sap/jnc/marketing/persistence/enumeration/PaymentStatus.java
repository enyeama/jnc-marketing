package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum PaymentStatus implements DisplayableEnumModel {

	PAID(0, "已兑付"), UNPAID(1, "未兑付");

	private final int value;
	private final String label;

	PaymentStatus(int value, String label) {
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

	public static PaymentStatus valueOf(int value) {
		for (PaymentStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
