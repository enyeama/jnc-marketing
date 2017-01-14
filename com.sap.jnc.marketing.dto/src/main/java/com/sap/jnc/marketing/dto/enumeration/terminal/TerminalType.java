package com.sap.jnc.marketing.dto.enumeration.terminal;

public enum TerminalType {

	CIGARETTE_AND_HOTEL(0, "名烟名酒店"), HOTEL(1, "酒店"), KA(2, "KA");

	private Integer value;

	private String label;

	TerminalType(Integer value, String label) {
		this.value = value;
		this.label = label;
	}

	public static String getLabel(Integer value) {
		for (TerminalType c : TerminalType.values()) {
			if (c.getValue().equals(value)) {
				return c.label;
			}
		}
		return null;
	}

	public Integer getValue() {

		return value;
	}

	public String getLabel() {
		return label;
	}

}
