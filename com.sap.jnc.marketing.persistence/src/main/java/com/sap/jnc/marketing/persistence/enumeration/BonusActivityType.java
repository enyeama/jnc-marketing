package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum BonusActivityType implements DisplayableEnumModel {

	CONSUMER_BONUS(0, "扫码红包"), BANQUET_BONUS(1, "宴会红包");

	private final int value;
	private final String label;

	BonusActivityType(int value, String label) {
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

	public static BonusActivityType valueOf(int value) {
		for (BonusActivityType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
