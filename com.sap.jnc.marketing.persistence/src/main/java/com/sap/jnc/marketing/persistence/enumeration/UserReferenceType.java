package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum UserReferenceType implements DisplayableEnumModel {

	EMPLOYEE(0, "员工"), DEALER(1, "经销商"), TERMINAL(2, "终端"), BANQUET(3, "宴会");

	private final int value;
	private final String label;

	UserReferenceType(int value, String label) {
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

	public static UserReferenceType valueOf(int value) {
		for (UserReferenceType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
