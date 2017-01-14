package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

/**
 * @author I300934 Ray Lv
 */
public enum RegionLevel implements DisplayableEnumModel {

	PROVINCE(0, "省"), CITY(1, "市"), COUNTY(2, "区县");

	private final int value;
	private final String label;

	RegionLevel(int value, String label) {
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

	public static RegionLevel valueOf(int value) {
		for (RegionLevel candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
