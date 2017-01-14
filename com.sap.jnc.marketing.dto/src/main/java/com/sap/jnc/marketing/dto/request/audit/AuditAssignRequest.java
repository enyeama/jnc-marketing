package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditAssignRequest implements Serializable {

	private static final long serialVersionUID = 8565916010373771266L;

	List<AuditAssignInfoRequest> audits = new ArrayList<AuditAssignInfoRequest>();

	public List<AuditAssignInfoRequest> getAudits() {
		return audits;
	}

	public void setAudits(List<AuditAssignInfoRequest> audits) {
		this.audits = audits;
	}

}
