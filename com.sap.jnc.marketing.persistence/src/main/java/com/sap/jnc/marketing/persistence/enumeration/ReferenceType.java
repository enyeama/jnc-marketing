package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum ReferenceType implements DisplayableEnumModel {

	DEALER(0, "经销商"), HOTEL(1, "酒店"), SHOP(2, "名烟名酒店"), KA(3, "KA"), BANQUET(4, "宴会"), EXHIBITION(5, "陈列");

	private final int value;
	private final String label;

	ReferenceType(int value, String label) {
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
	public static ReferenceType valueOf(int value) {
		for (ReferenceType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
