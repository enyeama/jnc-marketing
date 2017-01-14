package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum BanquetScanType implements DisplayableEnumModel {

	QRCODE(0, "二维码"), LOGISCODE(1, "物流码");

	private final int value;
	private final String label;

	BanquetScanType(int value, String label) {
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

	public static BanquetScanType valueOf(int value) {
		for (BanquetScanType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
