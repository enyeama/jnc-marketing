package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum BanquetStatus implements DisplayableEnumModel {

	APPLIED(0, "已报备待审核"),
	//
	APPROVED(1, "已审核待兑付"),
	//
	NOT_APPROVED(2, "审核未通过"),
	//
	PAID(3, "已兑付"),
	//
	CANCELLED(4, "已取消"),
	//
	EXPIRED(5, "已过期");

	private final int value;
	private final String label;

	BanquetStatus(int value, String label) {
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

	public static BanquetStatus valueOf(int value) {
		for (BanquetStatus candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
