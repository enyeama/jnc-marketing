/**
 * 
 */
package com.sap.jnc.marketing.persistence.criteria.kaorder;

import java.util.Calendar;

import com.sap.jnc.marketing.persistence.criteria.SearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.PeriodValidateType;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;

/**
 * @author Quansheng Liu I075496
 */
public class KAOrderAdvancedSearchKeywordNode implements SearchKeywordNode {

	private static final long serialVersionUID = 5305493268037148743L;

	private String branchName;
	private String kaSystemNumber;
	private TerminalOrderStatus terminalOrderStatus;
	private PeriodValidateType validType;
	private Calendar dateFrom;
	private Calendar dateTo;
	private Calendar date;
	private Long kaSpecialistEmployeeId;
	private Long kaSpecialistPositionId;
	private Long cityManagerEmployeeId;

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getKaSystemNumber() {
		return kaSystemNumber;
	}

	public void setKaSystemNumber(String kaSystemNumber) {
		this.kaSystemNumber = kaSystemNumber;
	}

	public TerminalOrderStatus getTerminalOrderStatus() {
		return terminalOrderStatus;
	}

	public void setTerminalOrderStatus(TerminalOrderStatus terminalOrderStatus) {
		this.terminalOrderStatus = terminalOrderStatus;
	}

	public PeriodValidateType getValidType() {
		return validType;
	}

	public void setValidType(PeriodValidateType validType) {
		this.validType = validType;
	}

	public Calendar getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Calendar dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Calendar getDateTo() {
		return dateTo;
	}

	public void setDateTo(Calendar dateTo) {
		this.dateTo = dateTo;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Long getKaSpecialistEmployeeId() {
		return kaSpecialistEmployeeId;
	}

	public void setKaSpecialistEmployeeId(Long kaSpecialistEmployeeId) {
		this.kaSpecialistEmployeeId = kaSpecialistEmployeeId;
	}

	public Long getKaSpecialistPositionId() {
		return kaSpecialistPositionId;
	}

	public void setKaSpecialistPositionId(Long kaSpecialistPositionId) {
		this.kaSpecialistPositionId = kaSpecialistPositionId;
	}

	public Long getCityManagerEmployeeId() {
		return cityManagerEmployeeId;
	}

	public void setCityManagerEmployeeId(Long cityManagerEmployeeId) {
		this.cityManagerEmployeeId = cityManagerEmployeeId;
	}

}
