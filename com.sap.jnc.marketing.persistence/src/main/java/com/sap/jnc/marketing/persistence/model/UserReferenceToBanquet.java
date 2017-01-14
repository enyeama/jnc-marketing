package com.sap.jnc.marketing.persistence.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@DiscriminatorValue("BANQUET")
public class UserReferenceToBanquet extends UserReference {

	private static final long serialVersionUID = -4757491495813219130L;

	@ManyToMany
	@JoinTable(
		// Join Table
		name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "USER_REF_BANQUET",
		// Columns - Self
		joinColumns = @JoinColumn(name = "userReferenceId"),
		// Columns - Inverse
		inverseJoinColumns = @JoinColumn(name = "banquetId"))
	private List<Banquet> banquets;

	public List<Banquet> getBanquets() {
		return banquets;
	}

	public void setBanquets(List<Banquet> banquets) {
		this.banquets = banquets;
	}
}
