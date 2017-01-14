package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum IndividualProductLogisticIntegrityStatus implements DisplayableEnumModel {

	UNKNOWN(0, "未知"), INTEGRATED(1, "是"), UNINTEGRATED(2, "否"), NA(3, "未计算"), ERROR(4, "错误");

	private final int value;
	private final String label;

	IndividualProductLogisticIntegrityStatus(int value, String label) {
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

	public static IndividualProductLogisticIntegrityStatus valueOf(int value) {
		for (IndividualProductLogisticIntegrityStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
