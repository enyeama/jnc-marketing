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

/**
 * 这个中间表是PO强烈要求建的，如果出现任何关系维护出错，不管我的事哈，我已经强烈建议不要暴露任何中间表了。 这个表是独立于HR之外的单独配置，需求从PO处得来，出问题了由PO负责
 *
 * @author I071053 Diouf Du
 * @author I300934 Ray Lv
 */
@Entity
@Table(name = "M_PROVINCE_MANAGER_OFFICE", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "ProvinceManagerOfficeAssignmentSeq", sequenceName = "SEQ_PROVINCEMANAGEROFFICEASSIGNMENT")
public class ProvinceManagerOfficeAssignment extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -8374639736442494164L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProvinceManagerOfficeAssignmentSeq")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provinceManagerId")
	private PositionView provinceManager;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "officeId")
	private DepartmentView office;

	private ValidityPeriod validityPeriod;

	public Long getId() {
		return this.id;
	}

	public PositionView getProvinceManager() {
		return provinceManager;
	}

	public void setProvinceManager(PositionView provinceManager) {
		this.provinceManager = provinceManager;
	}

	public DepartmentView getOffice() {
		return office;
	}

	public void setOffice(DepartmentView office) {
		this.office = office;
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
}
