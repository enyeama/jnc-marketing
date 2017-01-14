package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.ContractStatus;

@Entity
@Table(name = "T_CONTRACT", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "ContractSeq", sequenceName = "SEQ_CONTRACT")
public class Contract extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -4488618299174978404L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ContractSeq")
	private Long id;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private ContractStatus status;

	@Column(name = "externalId")
	private String externalId;

	// 模板名称
	@Column(name = "templateName")
	private String templateName;

	@Column(name = "salesOrganization")
	private String salesOrganization;

	@Column(name = "financialYear")
	private String financialYear;

	private ValidityPeriod validityPeriod;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dealerId")
	private Dealer dealer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityManager")
	private PositionView cityManager;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "officeId")
	private Department office;

	@OneToMany(mappedBy = "contract")
	private List<ContractItem> items;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ContractStatus getStatus() {
		return this.status;
	}

	public void setStatus(ContractStatus status) {
		this.status = status;
	}

	public String getSalesOrganization() {
		return this.salesOrganization;
	}

	public void setSalesOrganization(String salesOrganization) {
		this.salesOrganization = salesOrganization;
	}

	public String getFinancialYear() {
		return this.financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public ValidityPeriod getValidityPeriod() {
		return this.validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public PositionView getCityManager() {
		return cityManager;
	}

	public void setCityManager(PositionView cityManager) {
		this.cityManager = cityManager;
	}

	public Department getOffice() {
		return this.office;
	}

	public void setOffice(Department office) {
		this.office = office;
	}

	public List<ContractItem> getItems() {
		return this.items;
	}

	public void setItems(List<ContractItem> items) {
		this.items = items;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
}
