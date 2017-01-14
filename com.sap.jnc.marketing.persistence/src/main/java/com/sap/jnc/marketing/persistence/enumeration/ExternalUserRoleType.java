package com.sap.jnc.marketing.persistence.enumeration;


import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum ExternalUserRoleType implements DisplayableEnumModel {

	REFERRER(0, "宴会-拉单人"),
	//
	BENEFICIARY(1, "宴会-受益人"),
	//
	DRAWEE(2, "名烟名酒店-资源兑付人"),
	//
	TERMINALSUBACCOUNT(3, "名烟名酒店-终端子账号"),
	//
	KEYUSER(4, "酒店关键人");

	private final int value;
	private final String label;

	ExternalUserRoleType(int value, String label) {
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
	 * @param value 枚举中的值
	 * @return 对应枚举
	 */
	public static ExternalUserRoleType valueOf(int value) {
		for (ExternalUserRoleType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
