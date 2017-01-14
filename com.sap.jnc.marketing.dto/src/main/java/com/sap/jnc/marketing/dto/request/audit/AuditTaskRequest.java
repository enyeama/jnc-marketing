package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditTaskRequest implements Serializable {

	private static final long serialVersionUID = 2468811464497767092L;

	private List<AuditTaskInfoRequest> audits = new ArrayList<AuditTaskInfoRequest>();

	public List<AuditTaskInfoRequest> getAudits() {
		return audits;
	}

	public void setAudits(List<AuditTaskInfoRequest> audits) {
		this.audits = audits;
	}

}
