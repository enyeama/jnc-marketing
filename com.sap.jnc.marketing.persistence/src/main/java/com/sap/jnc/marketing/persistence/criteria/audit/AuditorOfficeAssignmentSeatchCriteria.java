package com.sap.jnc.marketing.persistence.criteria.audit;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditorOfficeAssignmentSeatchCriteria implements Serializable {

	private static final long serialVersionUID = -3709348280075262469L;

	private Long auditorId;

	private String jobId;

	private Long officeId;

	private boolean assigned;

	private Calendar validFrom;

	public Long getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Long auditorId) {
		this.auditorId = auditorId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}

	public Calendar getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Calendar validFrom) {
		this.validFrom = validFrom;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

}
