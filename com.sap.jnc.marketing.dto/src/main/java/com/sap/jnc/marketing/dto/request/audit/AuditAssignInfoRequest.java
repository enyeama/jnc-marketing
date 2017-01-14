package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditAssignInfoRequest implements Serializable {

	private static final long serialVersionUID = 8098323254839847401L;

	private Long id;

	private Long auditorId;

	private Long auditorPositionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Long auditorId) {
		this.auditorId = auditorId;
	}

	public Long getAuditorPositionId() {
		return auditorPositionId;
	}

	public void setAuditorPositionId(Long auditorPositionId) {
		this.auditorPositionId = auditorPositionId;
	}

}
