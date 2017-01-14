package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.AuditType;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditTaskGenerateRequest implements Serializable {

	private static final long serialVersionUID = 2680515217114826937L;

	private AuditType type;

	private Long targetId;

	public AuditType getType() {
		return type;
	}

	public void setType(AuditType type) {
		this.type = type;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

}
