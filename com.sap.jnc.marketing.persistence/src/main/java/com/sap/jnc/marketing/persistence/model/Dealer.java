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
import com.sap.jnc.marketing.persistence.enumeration.DealerStatus;

@Entity
@Table(name = "T_DEALER", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "DealerSeq", sequenceName = "SEQ_DEALER")
public class Dealer extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 7262998894692989060L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DealerSeq")
	private Long id;

	@Column(name = "externalId")
	private String externalId;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private DealerStatus status;

	@Column(name = "name")
	private String name;

	@Column(name = "isPlatformDealer")
	private Boolean isPlatformDealer;

	@Column(name = "address")
	private String address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legalContactId")
	private Contact legalContact;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityManagerId")
	private PositionView cityManager;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityManagerEmployeeId")
	private EmployeeView cityManagerEmployee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "officeId")
	private Department office;

	@Column(name = "isParent")
	private Boolean isParent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private Dealer parent;

	@OneToMany(mappedBy = "parent")
	private List<Dealer> subordinates;

	@OneToMany(mappedBy = "dealer")
	private List<Contact> contacts;

	@OneToMany(mappedBy = "dealer")
	private List<DealerOrder> orders;

	@OneToMany(mappedBy = "dealer")
	private List<IndividualProductBonus> bonuses;

	@OneToMany(mappedBy = "dealer")
	private List<Exhibition> exhibitions;

	@OneToMany(mappedBy = "dealer")
	private List<Banquet> banquets;

	@OneToMany(mappedBy = "dealer")
	private List<Contract> contracts;

	@OneToMany(mappedBy = "dealer")
	protected List<PositionView> leaders;

	@OneToMany(mappedBy = "dealer")
	protected List<UserReferenceToDealer> userReferences;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public DealerStatus getStatus() {
		return this.status;
	}

	public void setStatus(DealerStatus status) {
		this.status = status;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsPlatformDealer() {
		return this.isPlatformDealer;
	}

	public void setIsPlatformDealer(Boolean isPlatformDealer) {
		this.isPlatformDealer = isPlatformDealer;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Contact getLegalContact() {
		return this.legalContact;
	}

	public void setLegalContact(Contact legalContact) {
		this.legalContact = legalContact;
	}

	public PositionView getCityManager() {
		return this.cityManager;
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

	public Dealer getParent() {
		return this.parent;
	}

	public void setParent(Dealer parent) {
		this.parent = parent;
	}

	public List<Dealer> getSubordinates() {
		return this.subordinates;
	}

	public void setSubordinates(List<Dealer> subordinates) {
		this.subordinates = subordinates;
	}

	public List<DealerOrder> getOrders() {
		return this.orders;
	}

	public void setOrders(List<DealerOrder> orders) {
		this.orders = orders;
	}

	public List<IndividualProductBonus> getBonuses() {
		return this.bonuses;
	}

	public void setBonuses(List<IndividualProductBonus> bonuses) {
		this.bonuses = bonuses;
	}

	public List<Exhibition> getExhibitions() {
		return this.exhibitions;
	}

	public void setExhibitions(List<Exhibition> exhibitions) {
		this.exhibitions = exhibitions;
	}

	public List<Banquet> getBanquets() {
		return this.banquets;
	}

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public void setBanquets(List<Banquet> banquets) {
		this.banquets = banquets;
	}

	public List<Contract> getContracts() {
		return this.contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public List<PositionView> getLeaders() {
		return this.leaders;
	}

	public void setLeaders(List<PositionView> leaders) {
		this.leaders = leaders;
	}

	public EmployeeView getCityManagerEmployee() {
		return this.cityManagerEmployee;
	}

	public void setCityManagerEmployee(EmployeeView cityManagerEmployee) {
		this.cityManagerEmployee = cityManagerEmployee;
	}

	public Boolean getIsParent() {
		return this.isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public List<UserReferenceToDealer> getUserReferences() {
		return this.userReferences;
	}

	public void setUserReferences(List<UserReferenceToDealer> userReferences) {
		this.userReferences = userReferences;
	}
}
