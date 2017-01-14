package com.sap.jnc.marketing.dto.request.wechat.ka;

import java.io.Serializable;

public class KaTerminalsRequest implements Serializable {

	private static final long serialVersionUID = 9113723131794660015L;

	private String kaSalesId;

	private String titleKey;

	public String getKaSalesId() {
		return kaSalesId;
	}

	public void setKaSalesId(String kaSalesId) {
		this.kaSalesId = kaSalesId;
	}

	public String getTitleKey() {
		return titleKey;
	}

	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}
}
