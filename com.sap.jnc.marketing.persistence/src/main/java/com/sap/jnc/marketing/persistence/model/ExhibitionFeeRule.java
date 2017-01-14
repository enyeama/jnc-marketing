package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;

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
@Table(name = "T_EXHIBITION_FEE_RULE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "ExhibitionFeeRuleSeq", sequenceName = "SEQ_EXHIBITIONFEERULE")
public class ExhibitionFeeRule extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 8897964341277502426L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ExhibitionFeeRuleSeq")
	private Long id;

	private Amount amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exhibitionType")
	private ExhibitionType exhibitionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "specificationId")
	private ExhibitionSpecification specification;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Amount getAmount() {
		return this.amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public ExhibitionType getExhibitionType() {
		return this.exhibitionType;
	}

	public void setExhibitionType(ExhibitionType exhibitionType) {
		this.exhibitionType = exhibitionType;
	}

	public ExhibitionSpecification getSpecification() {
		return this.specification;
	}

	public void setSpecification(ExhibitionSpecification specification) {
		this.specification = specification;
	}
}
