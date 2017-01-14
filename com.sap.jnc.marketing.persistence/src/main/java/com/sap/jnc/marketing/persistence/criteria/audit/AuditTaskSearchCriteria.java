package com.sap.jnc.marketing.persistence.criteria.audit;

import java.util.Calendar;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.AuditResult;
import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;
import com.sap.jnc.marketing.persistence.enumeration.AuditType;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditTaskSearchCriteria implements SearchKeywordNode {

	private static final long serialVersionUID = 3661692823936273679L;

	private String keyword;

	private Long provinceManagerId;

	private AuditType type;

	private Long auditorId;

	private AuditStatus status;

	private AuditResult result;

	private Long auditorPositionId;

	private String provinceRegion;

	private String cityRegion;

	private String countyRegion;

	private Calendar createDateFrom;

	private Calendar createDateTo;

	private Calendar assignDateFrom;

	private Calendar assignDateTo;

	private Calendar auditDateFrom;

	private Calendar auditDateTo;

	private String queryType;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public AuditType getType() {
		return type;
	}

	public void setType(AuditType type) {
		this.type = type;
	}

	public Long getProvinceManagerId() {
		return provinceManagerId;
	}

	public void setProvinceManagerId(Long provinceManagerId) {
		this.provinceManagerId = provinceManagerId;
	}

	public Long getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Long auditorId) {
		this.auditorId = auditorId;
	}

	public AuditStatus getStatus() {
		return status;
	}

	public void setStatus(AuditStatus status) {
		this.status = status;
	}

	public AuditResult getResult() {
		return result;
	}

	public void setResult(AuditResult result) {
		this.result = result;
	}

	public Long getAuditorPositionId() {
		return auditorPositionId;
	}

	public void setAuditorPositionId(Long auditorPositionId) {
		this.auditorPositionId = auditorPositionId;
	}

	public Calendar getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(Calendar createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public Calendar getCreateDateTo() {
		return createDateTo;
	}

	public void setCreateDateTo(Calendar createDateTo) {
		this.createDateTo = createDateTo;
	}

	public Calendar getAssignDateFrom() {
		return assignDateFrom;
	}

	public void setAssignDateFrom(Calendar assignDateFrom) {
		this.assignDateFrom = assignDateFrom;
	}

	public Calendar getAssignDateTo() {
		return assignDateTo;
	}

	public void setAssignDateTo(Calendar assignDateTo) {
		this.assignDateTo = assignDateTo;
	}

	public Calendar getAuditDateFrom() {
		return auditDateFrom;
	}

	public void setAuditDateFrom(Calendar auditDateFrom) {
		this.auditDateFrom = auditDateFrom;
	}

	public Calendar getAuditDateTo() {
		return auditDateTo;
	}

	public void setAuditDateTo(Calendar auditDateTo) {
		this.auditDateTo = auditDateTo;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getProvinceRegion() {
		return provinceRegion;
	}

	public void setProvinceRegion(String provinceRegion) {
		this.provinceRegion = provinceRegion;
	}

	public String getCityRegion() {
		return cityRegion;
	}

	public void setCityRegion(String cityRegion) {
		this.cityRegion = cityRegion;
	}

	public String getCountyRegion() {
		return countyRegion;
	}

	public void setCountyRegion(String countyRegion) {
		this.countyRegion = countyRegion;
	}

}
