package com.sap.jnc.marketing.dto.response.payment;

import java.io.Serializable;


public class PaymentAccountValidityResponse implements Serializable {

	private static final long serialVersionUID = 2584975830590006044L;

	PaymentAccountValidityType paymentAccountValidityType;

	public PaymentAccountValidityType getPaymentAccountValidityType() {
		return paymentAccountValidityType;
	}

	public void setPaymentAccountValidityType(PaymentAccountValidityType paymentAccountValidityType) {
		this.paymentAccountValidityType = paymentAccountValidityType;
	}

	public static enum PaymentAccountValidityType {

		ERROR_EMPTY_VALIDFROM(0, "未填写生效日期，验证错误！"), //
		FAILURE_LT_LAST_VALIDFROM(1, "早于上一次生效时间，验证失败！"), //
		FAILURE_LT_CURRENT_TIME(2, "早于当前系统时间，验证失败！"), //
		OK_GT_CURRENT_TIME_AND_LAST_VALIDFROM(2, "晚于当前系统时间和上一次生效时间，验证成功！"), //
		OK_GT_CURRENT_TIME_AND_NO_HISTORY(3, "晚于当前系统时间且同一产品没有相关历史记录，验证成功！");

		private int value;
		private String label;

		PaymentAccountValidityType(int value, String label) {
			this.value = value;
			this.label = label;
		}

		public int getIntValue() {
			return value;
		}

		public String getLabel() {
			return label;
		}

		public static PaymentAccountValidityType valueOf(int value) {
			for (PaymentAccountValidityType candidate : values()) {
				if (candidate.value == value) {
					return candidate;
				}
			}
			return null;
		}
	}
}