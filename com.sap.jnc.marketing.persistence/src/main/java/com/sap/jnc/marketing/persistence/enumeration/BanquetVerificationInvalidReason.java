package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum BanquetVerificationInvalidReason implements DisplayableEnumModel {
	
	VERIFICATION_NOT_FOUND(0,"回收流转单未找到"), 
	BANQUET_NOT_FOUND(1,"回收流转单所相应的宴会报备单不存在"),
	APPLICATION_NOT_FOUND(2,"回收流转单所相应的核销申请单不存在"),
	VERIFICATION_NUMBER_INVALID(3,"核销单号错误"),
	INVALID_LOGISTIC_CODE(4,"物流码未找到"),
	NOT_REPORTED(5,"未报备，不属于已报备货品"),
	INVALID_REPORT(6,"非本报备单号，不属于本宴会"),
	NOT_REBATED(7,"未兑付"),
	INVALID_REQUEST(8,"请求参数无效");

	private final int value;
	private final String label;

	BanquetVerificationInvalidReason(int value, String label) {
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

	public static BanquetVerificationInvalidReason valueOf(int value) {
		for (BanquetVerificationInvalidReason candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

}
