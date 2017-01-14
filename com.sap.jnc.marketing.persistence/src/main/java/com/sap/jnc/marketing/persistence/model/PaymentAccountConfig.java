package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_PAYMENT_ACCOUNT_CONFIG", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "PaymentAccountConfigSeq", sequenceName = "SEQ_PAYMENTACCOUNTCONFIG")
public class PaymentAccountConfig extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -4289008443775069560L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PaymentAccountConfigSeq")
	private Long id;

	@Column(name = "defaultAccountId")
	private Long defaultAccountId;

	@Column(name = "defaultAccountOpenId")
	private String defaultAccountOpenId;

	private ValidityPeriod validityPeriod;

	@OneToMany(mappedBy = "config")
	private List<PaymentAccountConfigItem> items;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDefaultAccountId() {
		return this.defaultAccountId;
	}

	public void setDefaultAccountId(Long defaultAccountId) {
		this.defaultAccountId = defaultAccountId;
	}

	public String getDefaultAccountOpenId() {
		return this.defaultAccountOpenId;
	}

	public void setDefaultAccountOpenId(String defaultAccountOpenId) {
		this.defaultAccountOpenId = defaultAccountOpenId;
	}

	public ValidityPeriod getValidityPeriod() {
		return this.validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public List<PaymentAccountConfigItem> getItems() {
		return this.items;
	}

	public void setItems(List<PaymentAccountConfigItem> items) {
		this.items = items;
	}
}
