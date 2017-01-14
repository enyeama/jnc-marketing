package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum TerminalOrderStatus implements DisplayableEnumModel {

	CANCEL(0, "取消"), WAITFORDELIVERY(1, "待发货"), INDELIVERY(2, "配送中"), FINISH(3, "完成");

	private final int value;
	private final String label;

	TerminalOrderStatus(int value, String label) {
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

	public static TerminalOrderStatus valueOf(int value) {
		for (TerminalOrderStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
