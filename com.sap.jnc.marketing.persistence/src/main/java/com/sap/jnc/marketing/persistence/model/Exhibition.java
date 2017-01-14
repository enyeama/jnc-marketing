package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.Calendar;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.ExhibitionStatus;

@Entity
@Table(name = "T_EXHIBITION", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "ExhibitionSeq", sequenceName = "SEQ_EXHIBITIONRULE")
public class Exhibition extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 7292753546876787755L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ExhibitionSeq")
	private Long id;

	@Column(name = "isGridClothCovered")
	private String isGridClothCovered;

	@Column(name = "isEncloseBoxCovered")
	private String isEncloseBoxCovered;

	@Column(name = "isCardCovered")
	private String isCardCovered;

	@Column(name = "isStack")
	private String isStack;

	@Column(name = "comment")
	private String comment;

	@Column(name = "signTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar signTime;

	@Column(name = "approvalTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar approvalTime;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private ExhibitionStatus status;

	private ValidityPeriod validityPeriod;

	private AvailableAmount availableAmount;

	private ExhibitionAmount exhibitionAmount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityManagerId")
	private Employee cityManager;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dealerId")
	private Dealer dealer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salesmanId")
	private Employee salesman;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "terminalId")
	private Terminal terminal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approverId")
	private Employee approver;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exhibitionTypeId")
	private ExhibitionType exhibitionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exhibitionSpecificationId")
	private ExhibitionSpecification exhibitionSpecification;

	@OneToMany(mappedBy = "exhibition")
	private List<ExhibitionPicture> pictures;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Terminal getCorporate() {
		return this.terminal;
	}

	public void setCorporate(Terminal terminal) {
		this.terminal = terminal;
	}

	public ExhibitionType getDisplayType() {
		return this.exhibitionType;
	}

	public void setDisplayType(ExhibitionType exhibitionType) {
		this.exhibitionType = exhibitionType;
	}

	public Calendar getApprovalTime() {
		return this.approvalTime;
	}

	public void setApprovalTime(Calendar approvalTime) {
		this.approvalTime = approvalTime;
	}

	public Calendar getSignTime() {
		return this.signTime;
	}

	public void setSignTime(Calendar signTime) {
		this.signTime = signTime;
	}

	public ExhibitionSpecification getDisplayArea() {
		return this.exhibitionSpecification;
	}

	public void setDisplayArea(ExhibitionSpecification exhibitionSpecification) {
		this.exhibitionSpecification = exhibitionSpecification;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<ExhibitionPicture> getPictures() {
		return this.pictures;
	}

	public void setPictures(List<ExhibitionPicture> pictures) {
		this.pictures = pictures;
	}

	public Employee getCityManager() {
		return this.cityManager;
	}

	public void setCityManager(Employee cityManager) {
		this.cityManager = cityManager;
	}

	public Employee getSalesman() {
		return this.salesman;
	}

	public void setSalesman(Employee salesman) {
		this.salesman = salesman;
	}

	public Terminal getTerminal() {
		return this.terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public AvailableAmount getAvailableAmount() {
		return this.availableAmount;
	}

	public void setAvailableAmount(AvailableAmount availableAmount) {
		this.availableAmount = availableAmount;
	}

	public ExhibitionAmount getExhibitionAmount() {
		return this.exhibitionAmount;
	}

	public void setExhibitionAmount(ExhibitionAmount exhibitionAmount) {
		this.exhibitionAmount = exhibitionAmount;
	}

	public ExhibitionStatus getStatus() {
		return this.status;
	}

	public void setStatus(ExhibitionStatus status) {
		this.status = status;
	}

	public Employee getApprover() {
		return this.approver;
	}

	public void setApprover(Employee approver) {
		this.approver = approver;
	}

	public String getIsGridClothCovered() {
		return this.isGridClothCovered;
	}

	public void setIsGridClothCovered(String isGridClothCovered) {
		this.isGridClothCovered = isGridClothCovered;
	}

	public String getIsEncloseBoxCovered() {
		return this.isEncloseBoxCovered;
	}

	public void setIsEncloseBoxCovered(String isEncloseBoxCovered) {
		this.isEncloseBoxCovered = isEncloseBoxCovered;
	}

	public String getIsCardCovered() {
		return this.isCardCovered;
	}

	public void setIsCardCovered(String isCardCovered) {
		this.isCardCovered = isCardCovered;
	}

	public String getIsStack() {
		return this.isStack;
	}

	public void setIsStack(String isStack) {
		this.isStack = isStack;
	}

	public ExhibitionType getExhibitionType() {
		return this.exhibitionType;
	}

	public void setExhibitionType(ExhibitionType exhibitionType) {
		this.exhibitionType = exhibitionType;
	}

	public ExhibitionSpecification getExhibitionSpecification() {
		return this.exhibitionSpecification;
	}

	public void setExhibitionSpecification(ExhibitionSpecification exhibitionSpecification) {
		this.exhibitionSpecification = exhibitionSpecification;
	}
}
