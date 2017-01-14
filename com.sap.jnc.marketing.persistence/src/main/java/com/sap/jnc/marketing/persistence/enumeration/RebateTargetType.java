package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum RebateTargetType implements DisplayableEnumModel {

	UNKNOWN(0, "未知"),
	CONSUMER(1, "消费者"),
	AGENT(2, "拉单人"),
	AGENTSHOP(3, "终端"),
	AGENTHOTEL(4, "酒店关键人"),
	MERCHANDISER(9, "跟单员");

	private final int value;
	private final String label;

	RebateTargetType(int value, String label) {
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

	public static RebateTargetType valueOf(int value) {
		for (RebateTargetType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
