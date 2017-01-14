package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum LogisticOperatorType implements DisplayableEnumModel {

	// 业务员
	SALESMAN(0, "业务员"),
	// 经销商
	DEALER(1, "经销商"),
	// 组长
	LEADER(2, "组长"),
	// 终端
	TERMINAL(3, "终端"),
	// 剑南春
	JNC(4, "剑南春"),
	// 虚拟终端
	VTERMINAL(5, "虚拟终端"),
	// 酒店
	HOTEL(6, "酒店"),
	// 名烟名酒店
	SHOP(7, "名烟名酒店"),
	// KA
	KA(8, "KA");

	private final int value;
	private final String label;

	LogisticOperatorType(int value, String label) {
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

	public static LogisticOperatorType valueOf(int value) {
		for (LogisticOperatorType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
