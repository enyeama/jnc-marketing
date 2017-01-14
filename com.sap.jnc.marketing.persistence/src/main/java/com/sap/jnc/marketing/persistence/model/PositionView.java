package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.collections4.CollectionUtils;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "V_POSITION", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
public class PositionView implements Serializable {

	private static final long serialVersionUID = -6205190343872097693L;

	@Id
	@Column(name = "externalId")
	private String externalId;

	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "internalId")
	private Position position;

	@Column(name = "name")
	private String name;

	@Column(name = "isHead")
	private Boolean isHead;

	private ValidityPeriod validityPeriod;

	@OneToOne
	@JoinTable(
	// Join Table
	name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".V_" + "JOB_POSITION",
	// Columns - Self
	joinColumns = @JoinColumn(name = "positionExternalId", referencedColumnName = "externalId") ,
	// Columns - Inverse
	inverseJoinColumns = @JoinColumn(name = "jobExternalId", referencedColumnName = "externalId") )
	private JobView job;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(
	// Join Table
	name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".V_" + "DEPARTMENT_POSITION",
	// Columns - Self
	joinColumns = @JoinColumn(name = "positionExternalId", referencedColumnName = "externalId") ,
	// Columns - Inverse
	inverseJoinColumns = @JoinColumn(name = "departmentExternalID", referencedColumnName = "externalId") )
	private DepartmentView department;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(
	// Join Table
	name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "POSITION_DEALER",
	// Columns - Self
	joinColumns = @JoinColumn(name = "positionId") ,
	// Columns - Inverse
	inverseJoinColumns = @JoinColumn(name = "dealerId") )
	private Dealer dealer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(
	// Join Table
	name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "SALES_CATEGORY_POSITION",
	// Columns - Self
	joinColumns = @JoinColumn(name = "positionId") ,
	// Columns - Inverse
	inverseJoinColumns = @JoinColumn(name = "productSalesCategoryId") )
	private ProductSalesCategory productSalesCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(
	// Join Table
	name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".V_" + "PROVINCE_MANAGER_OFFICE",
	// Columns - Self
	joinColumns = @JoinColumn(name = "provinceManagerId") ,
	// Columns - Inverse
	inverseJoinColumns = @JoinColumn(name = "officeId") )
	protected DepartmentView office;

	@ManyToMany
	@JoinTable(
	// Join Table
	name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".V_" + "EMPLOYEE_POSITION",
	// Columns - Self
	joinColumns = @JoinColumn(name = "positionExternalId", referencedColumnName = "externalId") ,
	// Columns - Inverse
	inverseJoinColumns = @JoinColumn(name = "employeeExternalId", referencedColumnName = "externalId") )
	// @ManyToMany(mappedBy = "positions")
	private List<EmployeeView> employees;

	@ManyToMany
	@JoinTable(
	// Join Table
	name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "TERMINAL_SALESMAN",
	// Columns - Self
	joinColumns = @JoinColumn(name = "salesmanId") ,
	// Columns - Inverse
	inverseJoinColumns = @JoinColumn(name = "terminalId") )
	protected List<Terminal> terminals;

	@ManyToMany
	@JoinTable(
	// Join Table
	name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".M_" + "PRODUCT_POSITION",
	// Columns - Self
	joinColumns = @JoinColumn(name = "positionId") ,
	// Columns - Inverse
	inverseJoinColumns = @JoinColumn(name = "productId") )
	protected List<Product> products;

	@ManyToMany
	@JoinTable(
	// Join Table
	name = PersistenceConfig.ENTITY_SCHEMA_NAME + ".V_" + "REGION_POSITION",
	// Columns - Self
	joinColumns = @JoinColumn(name = "positionExternalId") ,
	// Columns - Inverse
	inverseJoinColumns = @JoinColumn(name = "regionId") )
	protected List<Region> regions;

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ValidityPeriod getValidityPeriod() {
		return this.validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public DepartmentView getDepartment() {
		return this.department;
	}

	public void setDepartment(DepartmentView department) {
		this.department = department;
	}

	public List<EmployeeView> getEmployees() {
		return this.employees;
	}

	public void setEmployees(List<EmployeeView> employees) {
		this.employees = employees;
	}

	public JobView getJob() {
		return this.job;
	}

	public void setJob(JobView job) {
		this.job = job;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public List<Terminal> getTerminals() {
		return this.terminals;
	}

	public void setTerminals(List<Terminal> terminals) {
		this.terminals = terminals;
	}

	public ProductSalesCategory getProductSalesCategory() {
		return this.productSalesCategory;
	}

	public void setProductSalesCategory(ProductSalesCategory productSalesCategory) {
		this.productSalesCategory = productSalesCategory;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Boolean getIsHead() {
		return this.isHead;
	}

	public void setIsHead(Boolean isHead) {
		this.isHead = isHead;
	}

	public List<Region> getRegions() {
		return this.regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public DepartmentView getOffice() {
		return this.office;
	}

	public void setOffice(DepartmentView office) {
		this.office = office;
	}

	public EmployeeView getEmployee() {
		if (CollectionUtils.isEmpty(employees)) {
			return null;
		}
		return employees.get(0);
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
