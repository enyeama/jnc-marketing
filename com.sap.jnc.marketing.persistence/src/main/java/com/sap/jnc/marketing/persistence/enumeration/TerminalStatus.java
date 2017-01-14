package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum TerminalStatus implements DisplayableEnumModel {

	DELETED(0, "删除"), INACTIVE(1, "未激活"), ACTIVE(2, "激活");

	private final int value;
	private final String label;

	TerminalStatus(int value, String label) {
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

	public static TerminalStatus valueOf(int value) {
		for (TerminalStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

	public static TerminalStatus labelOf(String status) {
		for (TerminalStatus candidate : values()) {
			if (candidate.getLabel().equals(status)) {
				return candidate;
			}
		}
		return null;
	}
}
