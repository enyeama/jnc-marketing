package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_DEALER_ORDER_ITEM", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "DealerOrderItemSeq", sequenceName = "SEQ_DEALERORDERITEM")
public class DealerOrderItem extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 193480522502944785L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DealerOrderItemSeq")
	private Long id;

	@Column(name = "comment")
	private String comment;

	private Quantity quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dealerOrderId")
	private DealerOrder order;

	@ManyToMany(mappedBy = "dealerOrderItems")
	private List<IndividualProduct> individualProducts;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Quantity getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public DealerOrder getOrder() {
		return this.order;
	}

	public void setOrder(DealerOrder order) {
		this.order = order;
	}

	public List<IndividualProduct> getIndividualProducts() {
		return this.individualProducts;
	}

	public void setIndividualProducts(List<IndividualProduct> individualProducts) {
		this.individualProducts = individualProducts;
	}
}
