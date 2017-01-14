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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_BONUS_DISPATCH_CONFIG", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "BonusDispatchConfigSeq", sequenceName = "SEQ_BONUSDISPATCHCONFIG")
public class BonusDispatchConfig extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -8813278567814717377L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BonusDispatchConfigSeq")
	private Long id;

	private AverageAmount averageAmount;

	private VarianceAmount varianceAmount;

	private ValidityPeriod validityPeriod;

	@Column(name = "calculatedBaseNumber")
	private Integer calculatedBaseNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dmsCategoryId")
	private ProductDmsCategory dmsCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "erpCategoryId")
	private ProductErpCategory erpCategory;

	@OneToMany(mappedBy = "config", fetch = FetchType.EAGER)
	private List<BonusDispatchConfigItem> items;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AverageAmount getAverageAmount() {
		return this.averageAmount;
	}

	public void setAverageAmount(AverageAmount averageAmount) {
		this.averageAmount = averageAmount;
	}

	public VarianceAmount getVarianceAmount() {
		return this.varianceAmount;
	}

	public void setVarianceAmount(VarianceAmount varianceAmount) {
		this.varianceAmount = varianceAmount;
	}

	public ValidityPeriod getValidityPeriod() {
		return this.validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public ProductDmsCategory getDmsCategory() {
		return this.dmsCategory;
	}

	public void setDmsCategory(ProductDmsCategory dmsCategory) {
		this.dmsCategory = dmsCategory;
	}

	public ProductErpCategory getErpCategory() {
		return this.erpCategory;
	}

	public void setErpCategory(ProductErpCategory erpCategory) {
		this.erpCategory = erpCategory;
	}

	public List<BonusDispatchConfigItem> getItems() {
		return this.items;
	}

	public void setItems(List<BonusDispatchConfigItem> items) {
		this.items = items;
	}

	public Integer getCalculatedBaseNumber() {
		return this.calculatedBaseNumber;
	}

	public void setCalculatedBaseNumber(Integer calculatedBaseNumber) {
		this.calculatedBaseNumber = calculatedBaseNumber;
	}
}
