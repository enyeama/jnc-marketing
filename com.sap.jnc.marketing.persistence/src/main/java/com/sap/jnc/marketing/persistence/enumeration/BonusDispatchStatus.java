package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum BonusDispatchStatus implements DisplayableEnumModel {

	SENDING(0, "发放中"), SENT(1, "已发放待领取"), FAILED(2, "发放失败"), RECEIVED(3, "已领取"), REFUND(4, "已退款 ");

	private final int value;
	private final String label;

	BonusDispatchStatus(int value, String label) {
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

	public static BonusDispatchStatus valueOf(int value) {
		for (BonusDispatchStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
