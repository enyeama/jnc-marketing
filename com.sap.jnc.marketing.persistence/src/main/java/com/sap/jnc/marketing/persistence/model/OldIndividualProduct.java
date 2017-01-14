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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_OLD_INDIVIDUAL_PRODUCT", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "OldIndividualProductSeq", sequenceName = "SEQ_OLDINDIVIDUALPRODUCT")
public class OldIndividualProduct extends ModificatoryEntity implements Serializable, BasicProduct {

	private static final long serialVersionUID = -7112923532489403495L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OldIndividualProductSeq")
	private Long id;

	@Column(name = "boxId", unique = true)
	private String boxId;

	@Column(name = "caseId")
	private String caseId;

	@Column(name = "dealerId")
	private String dealerId;

	@Column(name = "isConsumed")
	private Boolean isConsumed;

	@Column(name = "categoryId")
	private String categoryId;

	@OneToOne(mappedBy = "oldIndividualProduct")
	private BanquetItem banquetItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banquetId")
	private Banquet banquet;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channelId")
	private Channel channel;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDealerId() {
		return this.dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public Boolean getIsConsumed() {
		return this.isConsumed;
	}

	public void setIsConsumed(Boolean isConsumed) {
		this.isConsumed = isConsumed;
	}

	public String getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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

	public Channel getChannel() {
		return this.channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public BanquetItem getBanquetItem() {
		return this.banquetItem;
	}

	public void setBanquetItem(BanquetItem banquetItem) {
		this.banquetItem = banquetItem;
	}
}
