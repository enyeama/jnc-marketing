package com.sap.jnc.marketing.infrastructure.shared.utils.sms;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.Displayable;

public enum SmsType implements Displayable {

	REGISTER_SMS(0, "REGISTER_SMS"), OTHER(1, "OTHER_SMS");

	private final int value;
	private final String label;

	SmsType(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public Integer getValue() {
		return value;
	}

	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * 根据值获取对应枚举
	 *
	 * @param value
	 *            枚举中的值
	 * @return 对应枚举
	 */
	public static SmsType valueOf(int value) {
		for (SmsType ps : SmsType.values()) {
			if (ps.value == value) {
				return ps;
			}
		}
		return null;
	}

}
