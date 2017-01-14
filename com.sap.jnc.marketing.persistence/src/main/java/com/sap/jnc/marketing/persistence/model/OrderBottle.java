package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.OrderType;

@Entity
@Table(name = "T_ORDER_BOTTLE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "OrderBottleSeq", sequenceName = "SEQ_ORDERBOTTLE")
public class OrderBottle extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 7834637836130128041L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderBottleSeq")
	private Long id;

	@Column(name = "capOuterCode")
	private String capOuterCode;

	@Column(name = "orderType")
	@Enumerated(EnumType.STRING)
	private OrderType orderType;

	@Column(name = "orderItemId")
	private String orderItemId;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCapOuterCode() {
		return this.capOuterCode;
	}

	public void setCapOuterCode(String capOuterCode) {
		this.capOuterCode = capOuterCode;
	}

	public OrderType getOrderType() {
		return this.orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public String getOrderItemId() {
		return this.orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

}
