package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.Calendar;

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
import com.sap.jnc.marketing.persistence.enumeration.GiftListStatus;
import com.sap.jnc.marketing.persistence.enumeration.SpareMaterialDeliveryStatus;

@Entity
@Table(name = "T_SPARE_MAT_DELIVERY", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "SpareMaterialDeliverySeq", sequenceName = "SEQ_SPAREMATERIALDELIVERY")
public class SpareMaterialDelivery extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -409925207599477777L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SpareMaterialDeliverySeq")
	private Long id;

	@Column(name = "deliveryId")
	private String deliveryId;

	@Column(name = "deliveryStatus")
	@Enumerated(EnumType.STRING)
	private SpareMaterialDeliveryStatus deliveryStatus;

	@Column(name = "giftListStatus")
	@Enumerated(EnumType.STRING)
	private GiftListStatus giftListStatus;

	private DeliveryQuantity deliveryQuantity;

	@Column(name = "deliveryDate")
	private Calendar deliveryDate;

	@Column(name = "giftListExportDate")
	private Calendar giftListExportDate;

	@Column(name = "acknowledgementDate")
	private Calendar acknowledgementDate;

	@Column(name = "comment")
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materialId")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeId")
	private Employee employee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "positionId")
	private Position position;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeliveryId() {
		return this.deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public SpareMaterialDeliveryStatus getDeliveryStatus() {
		return this.deliveryStatus;
	}

	public void setDeliveryStatus(SpareMaterialDeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public GiftListStatus getGiftListStatus() {
		return this.giftListStatus;
	}

	public void setGiftListStatus(GiftListStatus giftListStatus) {
		this.giftListStatus = giftListStatus;
	}

	public Calendar getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Calendar deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Calendar getGiftListExportDate() {
		return this.giftListExportDate;
	}

	public void setGiftListExportDate(Calendar giftListExportDate) {
		this.giftListExportDate = giftListExportDate;
	}

	public Calendar getAcknowledgementDate() {
		return this.acknowledgementDate;
	}

	public void setAcknowledgementDate(Calendar acknowledgementDate) {
		this.acknowledgementDate = acknowledgementDate;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public DeliveryQuantity getDeliveryQuantity() {
		return this.deliveryQuantity;
	}

	public void setDeliveryQuantity(DeliveryQuantity deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}
}
