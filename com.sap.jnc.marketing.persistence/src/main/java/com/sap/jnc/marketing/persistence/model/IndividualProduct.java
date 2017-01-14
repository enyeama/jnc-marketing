package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.collections4.CollectionUtils;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.IndividualProductLogisticIntegrityStatus;
import com.sap.jnc.marketing.persistence.enumeration.IndividualProductRegionValidityStatus;
import com.sap.jnc.marketing.persistence.enumeration.OwnerType;

@Cacheable(false)
@Entity
@Table(name = "T_INDIVIDUAL_PRODUCT", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
public class IndividualProduct extends ModificatoryEntity implements Serializable, BasicProduct {

	private static final long serialVersionUID = 1384607600205414399L;

	@Id
	@Column(name = "capInnerCode")
	private String capInnerCode;

	@Column(name = "year")
	private String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

	@Column(name = "month")
	private String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH));

	@Column(name = "capOuterCode", unique = true)
	private String capOuterCode;

	@Column(name = "boxId", unique = true)
	private String boxId;

	@Column(name = "caseId")
	private String caseId;

	@Column(name = "producationPatchCode")
	private String producationPatchCode;

	@Column(name = "ownerType")
	@Enumerated(EnumType.STRING)
	private OwnerType ownerType;

	@Column(name = "ownerId")
	private String ownerId;

	@Column(name = "productionDate")
	private Calendar productionDate;

	// 物料环节完整性
	@Column(name = "logisticIntegrityStatus")
	@Enumerated(EnumType.STRING)
	private IndividualProductLogisticIntegrityStatus logisticIntegrityStatus;

	@Column(name = "isRebated")
	private Boolean isRebated;

	// 区域有效性
	@Column(name = "regionValidityStatus")
	@Enumerated(EnumType.STRING)
	private IndividualProductRegionValidityStatus regionValidityStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banquetId")
	private Banquet banquet;

	@OneToOne(mappedBy = "individualProduct")
	private BanquetItem banquetItem;

	@OneToMany(mappedBy = "individualProduct")
	private List<IndividualProductBonus> bonuses;

	@OneToMany(mappedBy = "individualProduct")
	private List<IndividualProductRebate> rebates;

	@OneToMany(mappedBy = "individualProduct")
	private List<Logistic> logistics;

	@ManyToMany
	@JoinTable(
		// Join Table
		name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "DEALER_ORDER_ITEM_INDIVIDUAL_PRODUCT",
		// Columns - Self
		joinColumns = @JoinColumn(name = "individualProductId"),
		// Columns - Inverse
		inverseJoinColumns = @JoinColumn(name = "dealerOrderItemId"))
	private List<DealerOrderItem> dealerOrderItems;

	@ManyToMany
	@JoinTable(
		// Join Table
		name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "TERMINAL_ORDER_ITEM_INDIVIDUAL_PRODUCT",
		// Columns - Self
		joinColumns = @JoinColumn(name = "individualProductId"),
		// Columns - Inverse
		inverseJoinColumns = @JoinColumn(name = "terminalOrderItemId"))
	private List<TerminalOrderItem> terminalOrderItems;

	public IndividualProductBonus getBonus() {
		if (CollectionUtils.isEmpty(this.bonuses)) {
			return null;
		}
		return this.bonuses.get(0);
	}

	public void setBonus(IndividualProductBonus bonus) {
		if (CollectionUtils.isEmpty(this.bonuses)) {
			this.bonuses = new ArrayList<>(1);
			this.bonuses.add(bonus);
		}
		else {
			this.bonuses.set(0, bonus);
		}
	}

	public String getCapInnerCode() {
		return this.capInnerCode;
	}

	public void setCapInnerCode(String capInnerCode) {
		this.capInnerCode = capInnerCode;
	}

	public String getCapOuterCode() {
		return this.capOuterCode;
	}

	public void setCapOuterCode(String capOuterCode) {
		this.capOuterCode = capOuterCode;
	}

	public String getBoxId() {
		return this.boxId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public String getCaseId() {
		return this.caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getProducationPatchCode() {
		return this.producationPatchCode;
	}

	public void setProducationPatchCode(String producationPatchCode) {
		this.producationPatchCode = producationPatchCode;
	}

	public OwnerType getOwnerType() {
		return this.ownerType;
	}

	public void setOwnerType(OwnerType ownerType) {
		this.ownerType = ownerType;
	}

	public String getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Calendar getProductionDate() {
		return this.productionDate;
	}

	public void setProductionDate(Calendar productionDate) {
		this.productionDate = productionDate;
	}

	public Boolean getIsRebated() {
		return this.isRebated;
	}

	public void setIsRebated(Boolean isRebated) {
		this.isRebated = isRebated;
	}

	public IndividualProductLogisticIntegrityStatus getLogisticIntegrityStatus() {
		return logisticIntegrityStatus;
	}

	public void setLogisticIntegrityStatus(IndividualProductLogisticIntegrityStatus logisticIntegrityStatus) {
		this.logisticIntegrityStatus = logisticIntegrityStatus;
	}

	public IndividualProductRegionValidityStatus getRegionValidityStatus() {
		return regionValidityStatus;
	}

	public void setRegionValidityStatus(IndividualProductRegionValidityStatus regionValidityStatus) {
		this.regionValidityStatus = regionValidityStatus;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return this.month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Banquet getBanquet() {
		return this.banquet;
	}

	public void setBanquet(Banquet banquet) {
		this.banquet = banquet;
	}

	public BanquetItem getBanquetItem() {
		return this.banquetItem;
	}

	public void setBanquetItem(BanquetItem banquetItem) {
		this.banquetItem = banquetItem;
	}

	public List<IndividualProductBonus> getBonuses() {
		return this.bonuses;
	}

	public void setBonuses(List<IndividualProductBonus> bonuses) {
		this.bonuses = bonuses;
	}

	public List<IndividualProductRebate> getRebates() {
		return this.rebates;
	}

	public void setRebates(List<IndividualProductRebate> rebates) {
		this.rebates = rebates;
	}

	public List<Logistic> getLogistics() {
		return this.logistics;
	}

	public void setLogistics(List<Logistic> logistics) {
		this.logistics = logistics;
	}

	public List<DealerOrderItem> getDealerOrderItems() {
		return this.dealerOrderItems;
	}

	public void setDealerOrderItems(List<DealerOrderItem> dealerOrderItems) {
		this.dealerOrderItems = dealerOrderItems;
	}

	public List<TerminalOrderItem> getTerminalOrderItems() {
		return this.terminalOrderItems;
	}

	public void setTerminalOrderItems(List<TerminalOrderItem> terminalOrderItems) {
		this.terminalOrderItems = terminalOrderItems;
	}
}
