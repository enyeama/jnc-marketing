package com.sap.jnc.marketing.persistence.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("EXHIBITION")
public class AuditExhibition extends Audit {

	private static final long serialVersionUID = 2685658216036530618L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "targetId")
	private Exhibition exhibition;

	public Exhibition getExhibition() {
		return exhibition;
	}

	public void setExhibition(Exhibition exhibition) {
		this.exhibition = exhibition;
	}
}
