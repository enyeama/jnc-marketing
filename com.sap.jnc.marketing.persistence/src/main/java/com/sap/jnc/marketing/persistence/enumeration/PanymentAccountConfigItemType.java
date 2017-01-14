package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum PanymentAccountConfigItemType implements DisplayableEnumModel {

	DEALER(0, "经销商"), REGION(1, "区域");

	private final int value;
	private final String label;

	PanymentAccountConfigItemType(int value, String label) {
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

	public static PanymentAccountConfigItemType valueOf(int value) {
		for (PanymentAccountConfigItemType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
