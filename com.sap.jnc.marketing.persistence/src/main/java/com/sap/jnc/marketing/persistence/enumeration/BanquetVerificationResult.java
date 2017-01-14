package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum BanquetVerificationResult implements DisplayableEnumModel {

	VALID(0, "合格"), BROKEN(1, "残码"), NOT_REPORTED(2,"未报备"), NOT_REBATED(3,"未兑付"), 
	INVALID_REBATE(4,"非兑付版本"), INVALID_REPORT(5,"非本报备单"), UNCONVENTIONAL(6,"非常规不合格"),
	NOT_FOUND(7,"未找到");

	private final int value;
	private final String label;

	BanquetVerificationResult(int value, String label) {
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

	public static BanquetVerificationResult valueOf(int value) {
		for (BanquetVerificationResult candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
