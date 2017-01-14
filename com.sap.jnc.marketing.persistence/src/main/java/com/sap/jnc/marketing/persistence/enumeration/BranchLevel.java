package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum BranchLevel implements DisplayableEnumModel {

	A(0, "A"), B(1, "B"), C(2, "C");

	private final int value;
	private final String label;

	BranchLevel(int value, String label) {
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

	public static BranchLevel valueOf(int value) {
		for (BranchLevel candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

	public static BranchLevel labelOf(String branchLevel) {
		for (BranchLevel candidate : values()) {
			if (candidate.getLabel().equals(branchLevel)) {
				return candidate;
			}
		}
		return null;
	}
}
