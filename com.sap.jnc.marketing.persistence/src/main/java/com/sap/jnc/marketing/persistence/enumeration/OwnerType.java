package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum OwnerType implements DisplayableEnumModel {

	JNC(0, "剑南春"), DEALER(1, "经销商"), LEADER(2, "组长"), VTERMINAL(3, "虚拟终端"), HOTEL(4, "酒店"), SHOP(5, "名烟名酒店"), KA(6, "KA");

	private final int value;
	private final String label;

	OwnerType(int value, String label) {
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

	public static OwnerType valueOf(int value) {
		for (OwnerType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
