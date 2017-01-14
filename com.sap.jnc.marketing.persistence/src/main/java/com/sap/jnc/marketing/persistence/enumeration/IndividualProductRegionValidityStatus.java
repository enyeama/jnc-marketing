package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum IndividualProductRegionValidityStatus implements DisplayableEnumModel {

	UNKNOWN(0, "未知"), VALID(1, "是"), INVALID(2, "否"), NA(3, "未计算"), ERROR(4, "错误");

	private final int value;
	private final String label;

	IndividualProductRegionValidityStatus(int value, String label) {
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

	public static IndividualProductRegionValidityStatus valueOf(int value) {
		for (IndividualProductRegionValidityStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
