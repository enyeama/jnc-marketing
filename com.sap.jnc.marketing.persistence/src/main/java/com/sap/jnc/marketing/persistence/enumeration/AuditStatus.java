package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum AuditStatus implements DisplayableEnumModel {

	PENDING(0, "待办稽核"), ASSIGNED(1, "已分配"), RETURNED(2, "稽核员退回"), Audited(3, "已稽核"), CONFIRMED(5, "稽核结果已确认"), EXPIRED(6, "稽核失效");

	private final int value;
	private final String label;

	AuditStatus(int value, String label) {
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

	public static AuditStatus valueOf(int value) {
		for (AuditStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
