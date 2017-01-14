package com.sap.jnc.marketing.persistence.enumeration;

import com.sap.jnc.marketing.infrastructure.shared.enumeration.DisplayableEnumModel;

public enum TerminalAllocationType implements DisplayableEnumModel{
	UNALLOCATED(0,"未分配"),ALLOCATED(1,"已分配");

	private final int value;
	private final String label;

	TerminalAllocationType(int value, String label) {
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
	
	public static TerminalAllocationType valueOf(int value) {
		for (TerminalAllocationType candidate : values()) {
			if (candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}

	public static TerminalAllocationType labelOf(String str) {
		for (TerminalAllocationType candidate : values()) {
			if (candidate.getLabel().equals(str)) {
				return candidate;
			}
		}
		return null;
	}

}
