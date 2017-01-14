package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.sap.jnc.marketing.persistence.model.DepartmentView;

/**
 * @author C5231393 Xu Xiaolei
 */
public class OfficesAllResponse implements Serializable {

	private static final long serialVersionUID = 8404961316847919876L;

	private Long officeId;

	private String officeName;

	private String officeExternalId;

	public OfficesAllResponse(DepartmentView departmentView) {
		if (departmentView == null) {
			return;
		}
		if (departmentView.getId() != null) {
			this.setOfficeId(departmentView.getId());
		}
		if (!StringUtils.isEmpty(departmentView.getName())) {
			this.setOfficeName(departmentView.getName());
		}
		if (!StringUtils.isEmpty(departmentView.getExternalId())) {
			this.setOfficeExternalId(departmentView.getExternalId());
		}
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

	public String getOfficeExternalId() {
		return officeExternalId;
	}

	public void setOfficeExternalId(String officeExternalId) {
		this.officeExternalId = officeExternalId;
	}

}
