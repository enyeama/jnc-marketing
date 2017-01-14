package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;

/**
 * @author Joel.Cheng I310645
 *
 */
public class BanquetScanInfoResponse implements Serializable {

	private static final long serialVersionUID = 9077738191190411736L;

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
