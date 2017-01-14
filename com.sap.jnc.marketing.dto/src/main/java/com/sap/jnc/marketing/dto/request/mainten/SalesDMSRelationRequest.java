package com.sap.jnc.marketing.dto.request.mainten;

import java.io.Serializable;

public class SalesDMSRelationRequest implements Serializable {
	private static final long serialVersionUID = 8333087297831528013L;
	String salesExternalId;
	String dmsId;
	public String getSalesExternalId() {
		return salesExternalId;
	}
	public void setSalesExternalId(String salesExternalId) {
		this.salesExternalId = salesExternalId;
	}
	public String getDmsId() {
		return dmsId;
	}
	public void setDmsId(String dmsId) {
		this.dmsId = dmsId;
	}
	
	
}
