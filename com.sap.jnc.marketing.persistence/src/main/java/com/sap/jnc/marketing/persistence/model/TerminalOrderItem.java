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
@Table(name = "T_TERMINAL_ORDER_ITEM", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "TerminalOrderItemSeq", sequenceName = "SEQ_TERMINALORDERITEM")
public class TerminalOrderItem extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -3196957220029806524L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TerminalOrderItemSeq")
	private Long id;

	@Column(name = "comment")
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId", referencedColumnName = "Id")
	private TerminalOrder order;

	@ManyToMany(mappedBy = "terminalOrderItems")
	private List<IndividualProduct> individualProducts;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public TerminalOrder getOrder() {
		return this.order;
	}

	public void setOrder(TerminalOrder order) {
		this.order = order;
	}

	public List<IndividualProduct> getIndividualProducts() {
		return this.individualProducts;
	}

	public void setIndividualProducts(List<IndividualProduct> individualProducts) {
		this.individualProducts = individualProducts;
	}

}
