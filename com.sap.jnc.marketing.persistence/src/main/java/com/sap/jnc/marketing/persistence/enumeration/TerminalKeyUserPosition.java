package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum TerminalKeyUserPosition implements DisplayableEnumModel {

	BARSUPERVISOR(0, "酒店-吧台主管"), PROMOTER(1, "酒店-促销员"), BUSINESSCONTACT(2, "酒店-业务联系人"), PURCHASER(3, "酒店-采购人");

	private final int value;
	private final String label;

	TerminalKeyUserPosition(int value, String label) {
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

	public static TerminalKeyUserPosition valueOf(int value) {
		for (TerminalKeyUserPosition candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

	public static TerminalKeyUserPosition labelOf(String str) {
		for (TerminalKeyUserPosition candidate : values()) {
			if (candidate.getLabel().equals(str) || candidate.getLabel().equals("酒店-" + str)) {
				return candidate;
			}
		}
		return null;
	}

}
