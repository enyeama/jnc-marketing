package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.BanquetVerification;

/**
 * @author Joel.Cheng I310645
 */
public class BanquetVerificationResponse implements Serializable {

	private static final long serialVersionUID = 3068019368209868896L;

	private Long id;

	public BanquetVerificationResponse() {
	}

	public BanquetVerificationResponse(BanquetVerification entity) {
		if (entity.getId() != null) {
			this.setId(entity.getId());
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
