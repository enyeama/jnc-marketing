package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.AuditResult;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditResultChangeRequest implements Serializable {

	private static final long serialVersionUID = 2503405716295041240L;

	private Long id;

	private AuditResult result;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AuditResult getResult() {
		return result;
	}

	public void setResult(AuditResult result) {
		this.result = result;
	}

}
