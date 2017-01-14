package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.LogisticOperatorType;
import com.sap.jnc.marketing.persistence.enumeration.MovementType;
import com.sap.jnc.marketing.persistence.enumeration.OrderType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "movementType")
@DiscriminatorValue("GENERAL")
@Table(name = "T_LOGISTIC", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "LogisticSeq", sequenceName = "SEQ_LOGISTIC")
public class Logistic extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 6212881606786526930L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LogisticSeq")
	private Long id;

	@Column(name = "year")
	private String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

	@Column(name = "month")
	private String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH));

	@Column(name = "time")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar timestamp;

	@Column(name = "movementType", insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private MovementType movementType;

	@Column(name = "operatorType")
	@Enumerated(EnumType.STRING)
	private LogisticOperatorType operatorType;

	@Column(name = "orderType")
	@Enumerated(EnumType.STRING)
	private OrderType orderType;

	@Column(name = "operatorId", insertable = false, updatable = false)
	private Long operatorId;

	@Column(name = "operatorPositionId", insertable = false, updatable = false)
	private Long operatorPositionId;

	@Column(name = "orderId", insertable = false, updatable = false)
	private Long orderId;

	@Column(name = "orderExternalERPId")
	private String orderExternalERPId;

	@Column(name = "orderItemId", insertable = false, updatable = false)
	private Long orderItemId;

	@Column(name = "orderExternalERPItemId")
	private String orderExternalERPItemId;

	@Column(name = "orderExternalDMSId")
	private String orderExternalDMSId;

	@Column(name = "orderExternalDMSItemId")
	private String orderExternalDMSItemId;

	@Column(name = "goodReceiverName")
	private String goodReceiverName;

	@Column(name = "receiverId", insertable = false, updatable = false)
	private Long receiverId;

	@Column(name = "receiverPositionId", insertable = false, updatable = false)
	private Long receiverPositionId;

	@Column(name = "isCancelled")
	private Boolean isCancelled;

	@Column(name = "comment")
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "capInnerCode")
	private IndividualProduct individualProduct;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}

	public MovementType getMovementType() {
		return this.movementType;
	}

	public void setMovementType(MovementType movementType) {
		this.movementType = movementType;
	}

	public LogisticOperatorType getOperatorType() {
		return this.operatorType;
	}

	public void setOperatorType(LogisticOperatorType operatorType) {
		this.operatorType = operatorType;
	}

	public OrderType getOrderType() {
		return this.orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderExternalERPId() {
		return this.orderExternalERPId;
	}

	public void setOrderExternalERPId(String orderExternalERPId) {
		this.orderExternalERPId = orderExternalERPId;
	}

	public Long getOrderItemId() {
		return this.orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getOrderExternalERPItemId() {
		return this.orderExternalERPItemId;
	}

	public void setOrderExternalERPItemId(String orderExternalERPItemId) {
		this.orderExternalERPItemId = orderExternalERPItemId;
	}

	public String getGoodReceiverName() {
		return this.goodReceiverName;
	}

	public void setGoodReceiverName(String goodReceiverName) {
		this.goodReceiverName = goodReceiverName;
	}

	public Boolean getIsCancelled() {
		return this.isCancelled;
	}

	public void setIsCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public IndividualProduct getIndividualProduct() {
		return this.individualProduct;
	}

	public void setIndividualProduct(IndividualProduct individualProduct) {
		this.individualProduct = individualProduct;
	}

	public Long getOperatorPositionId() {
		return operatorPositionId;
	}

	public void setOperatorPositionId(Long operatorPositionId) {
		this.operatorPositionId = operatorPositionId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Long getReceiverPositionId() {
		return receiverPositionId;
	}

	public void setReceiverPositionId(Long receiverPositionId) {
		this.receiverPositionId = receiverPositionId;
	}

	public String getOrderExternalDMSId() {
		return orderExternalDMSId;
	}

	public void setOrderExternalDMSId(String orderExternalDMSId) {
		this.orderExternalDMSId = orderExternalDMSId;
	}

	public String getOrderExternalDMSItemId() {
		return orderExternalDMSItemId;
	}

	public void setOrderExternalDMSItemId(String orderExternalDMSItemId) {
		this.orderExternalDMSItemId = orderExternalDMSItemId;
	}
}
