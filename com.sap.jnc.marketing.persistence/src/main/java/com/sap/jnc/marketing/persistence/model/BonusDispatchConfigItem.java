package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_BONUS_DISPATCH_CONFIG_ITEM", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "BonusDispatchConfigItemSeq", sequenceName = "SEQ_BONUSDISPATCHCONFIGITEM")
public class BonusDispatchConfigItem extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 5676005056987942898L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BonusDispatchConfigItemSeq")
	private Long id;

	@Column(name = "percentage", precision = 34, scale = 10)
	private BigDecimal percentage;

	private Amount amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "configId")
	private BonusDispatchConfig config;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPercentage() {
		return this.percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public Amount getAmount() {
		return this.amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public BonusDispatchConfig getConfig() {
		return this.config;
	}

	public void setConfig(BonusDispatchConfig config) {
		this.config = config;
	}
}
