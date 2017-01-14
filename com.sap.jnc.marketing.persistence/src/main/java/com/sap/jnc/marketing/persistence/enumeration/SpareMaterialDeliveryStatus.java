package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum SpareMaterialDeliveryStatus implements DisplayableEnumModel {

	IN_TRANSIT(0, "在途"), RETURNED(1, "退单"), DELIVERED(2, "已收货");

	private final int value;
	private final String label;

	SpareMaterialDeliveryStatus(int value, String label) {
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

	public static SpareMaterialDeliveryStatus valueOf(int value) {
		for (SpareMaterialDeliveryStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
