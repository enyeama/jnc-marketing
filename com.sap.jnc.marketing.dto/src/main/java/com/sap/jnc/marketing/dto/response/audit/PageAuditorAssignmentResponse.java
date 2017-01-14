package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment;

/**
 * @author C5231393 Xu Xiaolei
 */
public class PageAuditorAssignmentResponse extends PageImpl<PageAuditorAssignmentResponse.Item> implements Serializable {

	private static final long serialVersionUID = 2494146401481976335L;

	private static final List<PageAuditorAssignmentResponse.Item> EMPTY_LIST = new ArrayList<>(0);

	public PageAuditorAssignmentResponse(PageRequest pageRequest) {
		super(
			// Content
			EMPTY_LIST,
			// Page Request
			pageRequest,
			// Total
			0);
	}

	public PageAuditorAssignmentResponse(Page<ProvinceManagerOfficeAssignment> pages, PageRequest pageRequest) {
		super(
			// Content
			pages.getContent().stream().map(emp -> new Item(emp)).collect(Collectors.toList()),
			// Page Request
			pageRequest,
			// Total
			pages.getTotalElements());
	}

	public static class Item implements Serializable {

		private static final long serialVersionUID = -1032155442920939597L;

		// main columns
		private Long id;

		private String name;

		private String positionExternalId;

		private String positionName;

		private String jobName;

		private String officeExternalId;

		private Long officeId;

		private String officeName;

		private Calendar validFrom;

		private Calendar validTo;

		public Item(ProvinceManagerOfficeAssignment emp) {
			if (emp == null) {
				return;
			}
			if (emp.getProvinceManager() != null && emp.getProvinceManager().getEmployee() != null && emp.getProvinceManager().getEmployee()
				.getId() != null) {
				this.setId(emp.getProvinceManager().getEmployee().getId());
			}
			if (emp.getProvinceManager() != null && emp.getProvinceManager().getEmployee() != null && !StringUtils.isEmpty(emp.getProvinceManager()
				.getEmployee().getName())) {
				this.setName(emp.getProvinceManager().getEmployee().getName());
			}
			if (emp.getProvinceManager() != null && !StringUtils.isEmpty(emp.getProvinceManager().getExternalId())) {
				this.setPositionExternalId(emp.getProvinceManager().getExternalId());
			}
			if (emp.getProvinceManager() != null && !StringUtils.isEmpty(emp.getProvinceManager().getName())) {
				this.setPositionName(emp.getProvinceManager().getName());
			}
			if (emp.getProvinceManager() != null && emp.getProvinceManager().getJob() != null & !StringUtils.isEmpty(emp.getProvinceManager().getJob()
				.getName())) {
				this.setJobName(emp.getProvinceManager().getJob().getName());
			}
			if (emp.getProvinceManager() != null && emp.getProvinceManager().getDepartment() != null && emp.getProvinceManager().getDepartment()
				.getExternalId() != null) {
				this.setOfficeExternalId(emp.getProvinceManager().getDepartment().getExternalId());
			}
			if (emp.getProvinceManager() != null && emp.getProvinceManager().getDepartment() != null && emp.getProvinceManager().getDepartment()
				.getId() != null) {
				this.setOfficeId(emp.getProvinceManager().getDepartment().getId());
			}
			if (emp.getProvinceManager() != null && emp.getProvinceManager().getDepartment() != null && !StringUtils.isEmpty(emp.getProvinceManager()
				.getDepartment().getName())) {
				this.setOfficeName(emp.getProvinceManager().getDepartment().getName());
			}
			if (emp.getValidityPeriod() != null && emp.getValidityPeriod().getValidFrom() != null) {
				this.setValidFrom(emp.getValidityPeriod().getValidFrom());
			}
			if (emp.getValidityPeriod() != null && emp.getValidityPeriod().getValidTo() != null) {
				this.setValidTo(emp.getValidityPeriod().getValidTo());
			}
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPositionName() {
			return positionName;
		}

		public void setPositionName(String positionName) {
			this.positionName = positionName;
		}

		public String getJobName() {
			return jobName;
		}

		public void setJobName(String jobName) {
			this.jobName = jobName;
		}

		public Long getOfficeId() {
			return officeId;
		}

		public void setOfficeId(Long officeId) {
			this.officeId = officeId;
		}

		public String getOfficeName() {
			return officeName;
		}

		public void setOfficeName(String officeName) {
			this.officeName = officeName;
		}

		public Calendar getValidFrom() {
			return validFrom;
		}

		public void setValidFrom(Calendar validFrom) {
			this.validFrom = validFrom;
		}

		public Calendar getValidTo() {
			return validTo;
		}

		public void setValidTo(Calendar validTo) {
			this.validTo = validTo;
		}

		public String getPositionExternalId() {
			return positionExternalId;
		}

		public void setPositionExternalId(String positionExternalId) {
			this.positionExternalId = positionExternalId;
		}

		public String getOfficeExternalId() {
			return officeExternalId;
		}

		public void setOfficeExternalId(String officeExternalId) {
			this.officeExternalId = officeExternalId;
		}

	}

}
