package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;

/**
 * @author I332242 Zhu Qiang
 */
public class BanquetApplyInfoResponse implements Serializable {

	private static final long serialVersionUID = -1116698575667500463L;

    private int value;
	
	private String label;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
