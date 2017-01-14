package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum MovementType implements DisplayableEnumModel {

	// 剑南春到经销商 - 剑南春发货
	JNC_OUT(0, "剑南春发货"),
	// 剑南春到经销商 - 经销商收货
	DEALER_IN(1, "经销商收货"),
	// 经销商到组长 - 经销商发货
	DEALER_OUT(2, "经销商发货到组长"),
	// 组长收货
	LEADER_IN(3, "组长收货"),
	// 组长发货
	LEADER_OUT(4, "组长发货到终端"),
	// 终端从组长收货
	TL_IN(5, "终端从组长收货"),
	// 经销商发货到终端
	DEALERTOTERMINAL_DEALER_OUT(6, "经销商发货到终端"),
	// 终端从经销商收货
	DEALERTOTERMINAL_TERMINAL_IN(7, "终端从经销商收货");

	private final int value;
	private final String label;

	MovementType(int value, String label) {
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

	public static MovementType valueOf(int value) {
		for (MovementType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
