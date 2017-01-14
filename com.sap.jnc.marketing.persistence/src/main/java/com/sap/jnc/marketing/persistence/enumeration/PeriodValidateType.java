/**
 * 
 */
package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

/**
 * @author Quansheng Liu I075496
 */
public enum PeriodValidateType implements DisplayableEnumModel {
	GREATERTHAN(0, "greaterThan"), LESSTHAN(1, "lessThan"), EQUAlTO(2, "equalTo"), BETWEEN(3, "between");

	private final int value;
	private final String label;

	PeriodValidateType(int value, String label) {
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

	public static PeriodValidateType valueOf(int value) {
		for (PeriodValidateType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}
}
