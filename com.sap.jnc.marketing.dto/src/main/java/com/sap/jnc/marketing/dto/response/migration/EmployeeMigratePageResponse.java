package com.sap.jnc.marketing.dto.response.migration;

import java.io.Serializable;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod;

public class EmployeeMigratePageResponse extends PageImpl<EmployeeMigratePageResponse.Item> implements Serializable{

	private static final long serialVersionUID = 6156612258598159177L;

	public EmployeeMigratePageResponse(Page<Employee> pages, PageRequest pageRequest) {
		super(
			// Content
			pages.getContent().stream().map(employee -> new Item(employee)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}

	public static class Item implements Serializable {

		private static final long serialVersionUID = -92302443597170000L;

		private Long id;

		private String externalId;

		private String name;

		private String phone;

		private String idCardNO;

		private ValidityPeriod validityPeriod;
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getExternalId() {
			return externalId;
		}

		public void setExternalId(String externalId) {
			this.externalId = externalId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getIdCardNO() {
			return idCardNO;
		}

		public void setIdCardNO(String idCardNO) {
			this.idCardNO = idCardNO;
		}

		public ValidityPeriod getValidityPeriod() {
			return validityPeriod;
		}

		public void setValidityPeriod(ValidityPeriod validityPeriod) {
			this.validityPeriod = validityPeriod;
		}

		public Item() {
		}

		public Item(Employee employee) {
			if (employee == null) {
				return;
			}
			this.setId(employee.getId());
			this.setExternalId(employee.getExternalId());
			this.setName(employee.getName());
			this.setPhone(employee.getPhone());
			this.setIdCardNO(employee.getIdCardNO());
			this.setValidityPeriod(employee.getValidityPeriod());
		}
	}

}
