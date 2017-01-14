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
import com.sap.jnc.marketing.persistence.enumeration.DealerOrderStatus;

@Entity
@Table(name = "T_DEALER_ORDER", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "DealerOrderSeq", sequenceName = "SEQ_DEALERORDER")
public class DealerOrder extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -3120540356238193717L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DealerOrderSeq")
	private Long id;

	@Column(name = "status")
	private DealerOrderStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dealerId")
	private Dealer dealer;

	@OneToMany(mappedBy = "order")
	private List<DealerOrderItem> items;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DealerOrderStatus getStatus() {
		return this.status;
	}

	public void setStatus(DealerOrderStatus status) {
		this.status = status;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public List<DealerOrderItem> getItems() {
		return this.items;
	}

	public void setItems(List<DealerOrderItem> items) {
		this.items = items;
	}

}
