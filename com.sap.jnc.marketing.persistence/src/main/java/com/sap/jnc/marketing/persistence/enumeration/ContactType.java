package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum ContactType implements DisplayableEnumModel {

	BUSSINESS_CONTACT(0, "业务联系人"), GOODS_RECEIVER(1, "收货联系人"), FIRST_CONTACT(2, "第一联系人"), SECOND_CONTACT(3, "第二联系人"), THIRD_CONTACT(4, "第三联系人");

	private final int value;
	private final String label;

	ContactType(int value, String label) {
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

	public static ContactType valueOf(int value) {
		for (ContactType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
