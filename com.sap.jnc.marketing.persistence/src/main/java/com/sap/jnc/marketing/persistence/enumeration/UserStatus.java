package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum UserStatus implements DisplayableEnumModel {

	ACTIVE(0, "active"), INACTIVE(1, "inactive"), OBSOLETE(3, "obsolete");

	private final int value;
	private final String label;

	UserStatus(int value, String label) {
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

	/**
	 * 根据值获取对应枚举
	 *
	 * @param value
	 *            枚举中的值
	 * @return 对应枚举
	 */
	public static UserStatus valueOf(int value) {
		for (UserStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
