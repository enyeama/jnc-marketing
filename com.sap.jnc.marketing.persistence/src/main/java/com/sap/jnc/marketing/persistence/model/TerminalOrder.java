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
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderCreaterType;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderType;

@Entity
@Table(name = "T_TERMINAL_ORDER", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "TerminalOrderSeq", sequenceName = "SEQ_TERMINALORDER")
public class TerminalOrder extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 4337216834746709508L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TerminalOrderSeq")
	private Long id;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private TerminalOrderType type;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private TerminalOrderStatus status;

	@Column(name = "createrType")
	@Enumerated(EnumType.STRING)
	private TerminalOrderCreaterType createrType;

	@Column(name = "createrId")
	private String createrId;

	@Column(name = "complaint")
	private String complaint;

	@Column(name = "comment")
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materialId")
	private Product product;

	@Column(name = "quantity")
	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createrEmployeeId")
	private Employee createrEmployee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createrPositionId")
	private Position createrPosition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "terminalId")
	private Terminal terminal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "responsibleDealerId")
	private Dealer dealer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "responsibleLeaderId")
	private Employee responsibleLeader;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "responsibleLeaderPositionId")
	private Position responsibleLeaderPosition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityManagerEmployeeId")
	private Employee cityManagerEmployee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityManagerPositionId")
	private Position cityManagerPosition;

	@OneToMany(mappedBy = "order")
	private List<TerminalOrderItem> items;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TerminalOrderType getType() {
		return this.type;
	}

	public void setType(TerminalOrderType type) {
		this.type = type;
	}

	public TerminalOrderStatus getStatus() {
		return this.status;
	}

	public void setStatus(TerminalOrderStatus status) {
		this.status = status;
	}

	public TerminalOrderCreaterType getCreaterType() {
		return this.createrType;
	}

	public void setCreaterType(TerminalOrderCreaterType createrType) {
		this.createrType = createrType;
	}

	public String getCreaterId() {
		return this.createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getComplaint() {
		return this.complaint;
	}

	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Terminal getTerminal() {
		return this.terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public Employee getResponsibleLeader() {
		return this.responsibleLeader;
	}

	public void setResponsibleLeader(Employee responsibleLeader) {
		this.responsibleLeader = responsibleLeader;
	}

	public Position getResponsibleLeaderPosition() {
		return this.responsibleLeaderPosition;
	}

	public void setResponsibleLeaderPosition(Position responsibleLeaderPosition) {
		this.responsibleLeaderPosition = responsibleLeaderPosition;
	}

	public List<TerminalOrderItem> getItems() {
		return this.items;
	}

	public void setItems(List<TerminalOrderItem> items) {
		this.items = items;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Employee getCityManagerEmployee() {
		return cityManagerEmployee;
	}

	public void setCityManagerEmployee(Employee cityManagerEmployee) {
		this.cityManagerEmployee = cityManagerEmployee;
	}

	public Position getCityManagerPosition() {
		return cityManagerPosition;
	}

	public void setCityManagerPosition(Position cityManagerPosition) {
		this.cityManagerPosition = cityManagerPosition;
	}

	public Employee getCreaterEmployee() {
		return createrEmployee;
	}

	public void setCreaterEmployee(Employee createrEmployee) {
		this.createrEmployee = createrEmployee;
	}

	public Position getCreaterPosition() {
		return createrPosition;
	}

	public void setCreaterPosition(Position createrPosition) {
		this.createrPosition = createrPosition;
	}
}
