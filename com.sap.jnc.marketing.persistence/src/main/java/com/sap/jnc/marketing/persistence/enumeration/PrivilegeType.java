package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum PrivilegeType implements DisplayableEnumModel {

	UNKNOWN(0, "未知"), MODULE(-1, "模块"), WECHAT(1, "微信"), ADMIN(2, "后台管理");

	private final int value;
	private final String label;

	PrivilegeType(int value, String label) {
		this.value = value;
		this.label = label;
	}

	@Override
	public int getIntValue() {
		return this.value;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	public static PrivilegeType valueOf(int value) {
		for (final PrivilegeType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
