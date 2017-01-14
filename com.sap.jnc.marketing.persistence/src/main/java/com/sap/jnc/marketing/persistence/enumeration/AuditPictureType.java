package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum AuditPictureType implements DisplayableEnumModel {

	// 门头照片
	HEAD(0, "门头照片"),
	// 营业执照照片
	BUSINESS_LICEN(1, "营业执照照片"),
	// 张贴网格布
	GRID_CLOTH(2, "张贴网格布照片"),
	// 摆放盒围
	ENCLOSE_BOX(3, "摆放盒围照片"),
	// 摆放台卡
	CARD(4, "摆放盒围照片"),
	// 陈列照片
	EXHIBITION(5, "陈列照片"),
	// BANQUET
	BANQUET(6, "宴会照片");

	private final int value;
	private final String label;

	AuditPictureType(int value, String label) {
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

	public static AuditPictureType valueOf(int value) {
		for (AuditPictureType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
