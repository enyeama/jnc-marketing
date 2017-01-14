package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.model.EmployeeView;

/**
 * @author C5231393 Xu Xiaolei
 */
public class PageAuditorUnassignmentResponse extends PageImpl<PageAuditorUnassignmentResponse.Item> implements Serializable {

	private static final long serialVersionUID = 2494146401481976335L;

	private static final List<PageAuditorUnassignmentResponse.Item> EMPTY_LIST = new ArrayList<>(0);

	public PageAuditorUnassignmentResponse(PageRequest pageRequest) {
		super(
			// Content
			EMPTY_LIST,
			// Page Request
			pageRequest,
			// Total
			0);
	}

	public PageAuditorUnassignmentResponse(Page<EmployeeView> pages, PageRequest pageRequest) {
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

		public Item(EmployeeView emp) {
			if (emp == null) {
				return;
			}
			if (emp.getId() != null) {
				this.setId(emp.getId());
			}
			if (!StringUtils.isEmpty(emp.getName())) {
				this.setName(emp.getName());
			}
			if (!CollectionUtils.isEmpty(emp.getPositions()) && !StringUtils.isEmpty(emp.getPositions().get(0).getExternalId())) {
				this.setPositionExternalId(emp.getPositions().get(0).getExternalId());
			}
			if (!CollectionUtils.isEmpty(emp.getPositions()) && !StringUtils.isEmpty(emp.getPositions().get(0).getName())) {
				this.setPositionName(emp.getPositions().get(0).getName());
			}
			if (!CollectionUtils.isEmpty(emp.getPositions()) && emp.getPositions().get(0).getJob() != null && !StringUtils.isEmpty(emp.getPositions()
				.get(0).getJob().getName())) {
				this.setJobName(emp.getPositions().get(0).getJob().getName());
			}
			if (!CollectionUtils.isEmpty(emp.getPositions()) && emp.getPositions().get(0).getOffice() != null && emp.getPositions().get(0).getOffice()
				.getExternalId() != null) {
				this.setOfficeExternalId(emp.getPositions().get(0).getOffice().getExternalId());
			}
			if (!CollectionUtils.isEmpty(emp.getPositions()) && emp.getPositions().get(0).getOffice() != null && emp.getPositions().get(0).getOffice()
				.getId() != null) {
				this.setOfficeId(emp.getPositions().get(0).getOffice().getId());
			}
			if (!CollectionUtils.isEmpty(emp.getPositions()) && emp.getPositions().get(0).getOffice() != null && !StringUtils.isEmpty(emp
				.getPositions().get(0).getOffice().getName())) {
				this.setOfficeName(emp.getPositions().get(0).getOffice().getName());
			}
			if (!CollectionUtils.isEmpty(emp.getPositions()) && emp.getPositions().get(0).getOffice() != null && emp.getPositions().get(0).getOffice()
				.getValidityPeriod().getValidFrom() != null) {
				this.setValidFrom(emp.getPositions().get(0).getOffice().getValidityPeriod().getValidFrom());
			}
			if (!CollectionUtils.isEmpty(emp.getPositions()) && emp.getPositions().get(0).getOffice() != null && emp.getPositions().get(0).getOffice()
				.getValidityPeriod().getValidTo() != null) {
				this.setValidTo(emp.getPositions().get(0).getOffice().getValidityPeriod().getValidTo());
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
