package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum ProductDmsCategoryLevel implements DisplayableEnumModel {

	FIRST_LEVEL(0, "第一级"), SECOND_LEVEL(1, "第二级"), THIRD_LEVEL(2, "第三级");

	private final int value;
	private final String label;

	ProductDmsCategoryLevel(int value, String label) {
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

	public static ProductDmsCategoryLevel valueOf(int value) {
		for (ProductDmsCategoryLevel candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
