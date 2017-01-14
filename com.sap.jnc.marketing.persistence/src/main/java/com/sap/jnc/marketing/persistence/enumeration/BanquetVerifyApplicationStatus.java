package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum BanquetVerifyApplicationStatus implements DisplayableEnumModel {

	WAITFORAPPLY(0, "待提报"), DELIVERED(1, "已邮寄待签收"), WAITFORVERIFY(2, "待核数"), WAITFORRECORD(3, "待录码"), WAITFOR2NDVERIFY(4, "待二次核对"), WAITFORTOVERIFY(5,
		"待转入核销"), TOVERIFY(6, "已转入核销");

	private final int value;
	private final String label;

	BanquetVerifyApplicationStatus(int value, String label) {
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

	public static BanquetVerifyApplicationStatus valueOf(int value) {
		for (BanquetVerifyApplicationStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
