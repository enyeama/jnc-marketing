package com.sap.jnc.marketing.dto.request.wechat.salesman;

import java.io.Serializable;

public class SalesTerminalsRequest implements Serializable {

	private static final long serialVersionUID = -3370600981575900039L;

	private String createrId;

	private String titleKey;

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getTitleKey() {
		return titleKey;
	}

	public void setTitleKey(String titleKey) {
		this.titleKey = titleKey;
	}
}
