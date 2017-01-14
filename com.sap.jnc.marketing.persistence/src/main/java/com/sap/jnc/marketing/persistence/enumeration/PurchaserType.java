package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum PurchaserType implements DisplayableEnumModel {

	GENERALPURCHASE(0, "总采购"), SINGLEPURCHASE(1, "单店采购 ");

	private final int value;
	private final String label;

	PurchaserType(int value, String label) {
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

	public static PurchaserType valueOf(int value) {
		for (PurchaserType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

	public static PurchaserType labelOf(String purchaserType) {
		for (PurchaserType candidate : values()) {
			if (candidate.getLabel().equals(purchaserType)) {
				return candidate;
			}
		}
		return null;
	}
}
