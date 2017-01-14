package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_DMS_ORDER_ITEM", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "DmsOrderItemSeq", sequenceName = "SEQ_DMSORDERITEM")
public class DmsOrderItem extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -784780525524748145L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DmsOrderItemSeq")
	private Long id;

	@Column(name = "erpOrderId")
	private String erpOrderId;

	@Column(name = "dmsOrderId")
	private String dmsOrderId;

	@Column(name = "dmsOrderItemId")
	private String dmsOrderItemId;

	private Quantity quantity;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getErpOrderId() {
		return this.erpOrderId;
	}

	public void setErpOrderId(String erpOrderId) {
		this.erpOrderId = erpOrderId;
	}

	public String getDmsOrderId() {
		return this.dmsOrderId;
	}

	public void setDmsOrderId(String dmsOrderId) {
		this.dmsOrderId = dmsOrderId;
	}

	public String getDmsOrderItemId() {
		return this.dmsOrderItemId;
	}

	public void setDmsOrderItemId(String dmsOrderItemId) {
		this.dmsOrderItemId = dmsOrderItemId;
	}

	public Quantity getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}
}
