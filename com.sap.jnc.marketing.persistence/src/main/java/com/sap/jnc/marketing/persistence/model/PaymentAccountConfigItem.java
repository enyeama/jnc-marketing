package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.PanymentAccountConfigItemType;

@Entity
@Table(name = "T_PAYMENT_ACCOUNT_CONFIG_ITEM", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "PaymentAccountConfigItemSeq", sequenceName = "SEQ_PAYMENTACCOUNTCONFIGITEM")
public class PaymentAccountConfigItem extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -2207939172371589968L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PaymentAccountConfigItemSeq")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "accountId")
	private String accountId;

	@Column(name = "accountOpenId")
	private String accountOpenId;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private PanymentAccountConfigItemType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paymentAccountConfigId")
	private PaymentAccountConfig config;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountId() {
		return this.accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountOpenId() {
		return this.accountOpenId;
	}

	public void setAccountOpenId(String accountOpenId) {
		this.accountOpenId = accountOpenId;
	}

	public PanymentAccountConfigItemType getType() {
		return this.type;
	}

	public void setType(PanymentAccountConfigItemType type) {
		this.type = type;
	}

	public PaymentAccountConfig getConfig() {
		return this.config;
	}

	public void setConfig(PaymentAccountConfig config) {
		this.config = config;
	}
}
