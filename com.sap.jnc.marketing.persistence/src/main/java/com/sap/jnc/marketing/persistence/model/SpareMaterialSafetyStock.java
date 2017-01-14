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
@Table(name = "T_SPARE_MAT_SAFETY_STOCK", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "SpareMaterialSafetyStockSeq", sequenceName = "SEQ_SPAREMATERIALSAFETYSTOCK")
public class SpareMaterialSafetyStock extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 2768532618206055443L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SpareMaterialSafetyStockSeq")
	private Long id;

	private SafetyStockQuantity safetyStockQuantity;

	private InTransitStockQuantity inTransitStockQuantity;

	private TotalDeliveredQuantity totalDeliveredQuantity;

	private TotalPaidQuantity totalPaidQuantity;

	private TotalVerifiedQuantity totalVerifiedQuantity;

	private TotalDifferenceQuantity totalDifferenceQuantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "positionId")
	private PositionView position;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materialId")
	private Product product;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SafetyStockQuantity getSafetyStockQuantity() {
		return this.safetyStockQuantity;
	}

	public void setSafetyStockQuantity(SafetyStockQuantity safetyStockQuantity) {
		this.safetyStockQuantity = safetyStockQuantity;
	}

	public InTransitStockQuantity getInTransitStockQuantity() {
		return this.inTransitStockQuantity;
	}

	public void setInTransitStockQuantity(InTransitStockQuantity inTransitStockQuantity) {
		this.inTransitStockQuantity = inTransitStockQuantity;
	}

	public TotalDeliveredQuantity getTotalDeliveredQuantity() {
		return this.totalDeliveredQuantity;
	}

	public void setTotalDeliveredQuantity(TotalDeliveredQuantity totalDeliveredQuantity) {
		this.totalDeliveredQuantity = totalDeliveredQuantity;
	}

	public TotalPaidQuantity getTotalPaidQuantity() {
		return this.totalPaidQuantity;
	}

	public void setTotalPaidQuantity(TotalPaidQuantity totalPaidQuantity) {
		this.totalPaidQuantity = totalPaidQuantity;
	}

	public TotalVerifiedQuantity getTotalVerifiedQuantity() {
		return this.totalVerifiedQuantity;
	}

	public void setTotalVerifiedQuantity(TotalVerifiedQuantity totalVerifiedQuantity) {
		this.totalVerifiedQuantity = totalVerifiedQuantity;
	}

	public TotalDifferenceQuantity getTotalDifferenceQuantity() {
		return this.totalDifferenceQuantity;
	}

	public void setTotalDifferenceQuantity(TotalDifferenceQuantity totalDifferenceQuantity) {
		this.totalDifferenceQuantity = totalDifferenceQuantity;
	}

	public PositionView getPosition() {
		return this.position;
	}

	public void setPosition(PositionView position) {
		this.position = position;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
