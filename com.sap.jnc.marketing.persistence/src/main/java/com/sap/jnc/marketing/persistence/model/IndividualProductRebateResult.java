package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.enumeration.IndividualProductLogisticIntegrityStatus;
import com.sap.jnc.marketing.persistence.enumeration.IndividualProductRegionValidityStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalType;

@Entity
@Table(name = "cdp.mmp.db.models/RebateReport", schema = "_SYS_BIC")
public class IndividualProductRebateResult implements Serializable {

	private static final long serialVersionUID = 829367412638773345L;

	@Id
	@Column(name = "capInnerCode")
	private String capInnerCode;

	@Column(name = "categoryId")
	private String categoryId;
	
	@Column(name = "categoryName")
	private String categoryName;
	
	@Column(name = "levelId")
	private String levelId;
	
	@Column(name = "levelName")
	private String levelName;
	
	@Column(name = "brandManagerPositionId")
	private String brandManagerPositionId;
	
	@Column(name = "brandManagerPositionName")
	private String brandManagerPositionName;

	@Column(name = "brandManagerId")
	private String brandManagerId;
	
	@Column(name = "brandManagerName")
	private String brandManagerName;
	
	@Column(name = "leaderPositionId")
	private String leaderPositionId;
	
	@Column(name = "leaderPositionName")
	private String leaderPositionName;

	@Column(name = "leaderId")
	private String leaderId;
	
	@Column(name = "leaderName")
	private String leaderName;
	
	@Column(name = "salesManPositionId")
	private String salesManPositionId;

	@Column(name = "salesManPositionName")
	private String salesManPositionName;
	
	@Column(name = "salesManId")
	private String salesManId;

	@Column(name = "salesManName")
	private String salesManName;
	
	@Column(name = "dealerId")
	private String dealerId;
	
	@Column(name = "dealerName")
	private String dealerName;

	@Column(name = "terminalId")
	private String terminalId;
	
	@Column(name = "terminalName")
	private String terminalName;
	
	@Column(name = "terminalType")
	@Enumerated(EnumType.STRING)
	private TerminalType terminalType;

	@Column(name = "regionValid")
	@Enumerated(EnumType.STRING)
	private IndividualProductRegionValidityStatus regionValid;

	@Column(name = "salesLinkIntegrity")
	@Enumerated(EnumType.STRING)
	private IndividualProductLogisticIntegrityStatus salesLinkIntegrity;

	@Column(name = "leaderOutDate")
	private Calendar leaderOutDate;
	
	@Column(name = "terminalInDate")
	private Calendar terminalInDate;
	
	@Column(name = "consumerScanDate")
	private Calendar consumerScanDate;
	
	@Column(name = "isPlatformDealer")
	private Boolean isPlatformDealer;
	
	@Column(name = "legalOperationCity")
	private String  legalOperationCity;
	
	@Column(name = "counsumerScanCity")
	private String  counsumerScanCity;
	
	@Column(name = "counsumerScanCounty")
	private String  counsumerScanCounty;
	
	@Column(name = "counsumerScanLatitude")
	private String  counsumerScanLatitude;
	
	@Column(name = "counsumerScanLongitude")
	private String  counsumerScanLongitude;
	
	@Column(name = "counsumerScanAddress")
	private String  counsumerScanAddress;
	
	@Column(name = "terminalProvince")
	private String  terminalProvince;
	
	@Column(name = "terminalCity")
	private String  terminalCity;
	
	@Column(name = "terminalCounty")
	private String  terminalCounty;
	
	@Column(name = "wechatBillNumber")
	private String wechatBillNumber;

	@Column(name = "accountManagerEmployeeId")
	private String accountManagerEmployeeId;

	@Column(name = "accountManagerEmployeeName")
	private String accountManagerEmployeeName;

	@Column(name = "accountManagerPositionId")
	private String accountManagerPositionId;

	@Column(name = "accountManagerPositionName")
	private String accountManagerPositionName;

	@Column(name = "salesCategoryExternalId")
	private String salesCategoryExternalId;

	@Column(name = "salesCategoryName")
	private String salesCategoryName;

	public String getCapInnerCode() {
		return capInnerCode;
	}

	public void setCapInnerCode(String capInnerCode) {
		this.capInnerCode = capInnerCode;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getBrandManagerPositionId() {
		return brandManagerPositionId;
	}

	public void setBrandManagerPositionId(String brandManagerPositionId) {
		this.brandManagerPositionId = brandManagerPositionId;
	}

	public String getBrandManagerPositionName() {
		return brandManagerPositionName;
	}

	public void setBrandManagerPositionName(String brandManagerPositionName) {
		this.brandManagerPositionName = brandManagerPositionName;
	}

	public String getBrandManagerId() {
		return brandManagerId;
	}

	public void setBrandManagerId(String brandManagerId) {
		this.brandManagerId = brandManagerId;
	}

	public String getBrandManagerName() {
		return brandManagerName;
	}

	public void setBrandManagerName(String brandManagerName) {
		this.brandManagerName = brandManagerName;
	}

	public String getLeaderPositionId() {
		return leaderPositionId;
	}

	public void setLeaderPositionId(String leaderPositionId) {
		this.leaderPositionId = leaderPositionId;
	}

	public String getLeaderPositionName() {
		return leaderPositionName;
	}

	public void setLeaderPositionName(String leaderPositionName) {
		this.leaderPositionName = leaderPositionName;
	}

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getSalesManPositionId() {
		return salesManPositionId;
	}

	public void setSalesManPositionId(String salesManPositionId) {
		this.salesManPositionId = salesManPositionId;
	}

	public String getSalesManPositionName() {
		return salesManPositionName;
	}

	public void setSalesManPositionName(String salesManPositionName) {
		this.salesManPositionName = salesManPositionName;
	}

	public String getSalesManId() {
		return salesManId;
	}

	public void setSalesManId(String salesManId) {
		this.salesManId = salesManId;
	}

	public String getSalesManName() {
		return salesManName;
	}

	public void setSalesManName(String salesManName) {
		this.salesManName = salesManName;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public IndividualProductRegionValidityStatus getRegionValid() {
		return regionValid;
	}

	public void setRegionValid(IndividualProductRegionValidityStatus regionValid) {
		this.regionValid = regionValid;
	}

	public IndividualProductLogisticIntegrityStatus getSalesLinkIntegrity() {
		return salesLinkIntegrity;
	}

	public void setSalesLinkIntegrity(IndividualProductLogisticIntegrityStatus salesLinkIntegrity) {
		this.salesLinkIntegrity = salesLinkIntegrity;
	}

	public Calendar getLeaderOutDate() {
		return leaderOutDate;
	}

	public void setLeaderOutDate(Calendar leaderOutDate) {
		this.leaderOutDate = leaderOutDate;
	}

	public Calendar getTerminalInDate() {
		return terminalInDate;
	}

	public void setTerminalInDate(Calendar terminalInDate) {
		this.terminalInDate = terminalInDate;
	}

	public Calendar getConsumerScanDate() {
		return consumerScanDate;
	}

	public void setConsumerScanDate(Calendar consumerScanDate) {
		this.consumerScanDate = consumerScanDate;
	}

	public Boolean getIsPlatformDealer() {
		return isPlatformDealer;
	}

	public void setIsPlatformDealer(Boolean isPlatformDealer) {
		this.isPlatformDealer = isPlatformDealer;
	}

	public String getLegalOperationCity() {
		return legalOperationCity;
	}

	public void setLegalOperationCity(String legalOperationCity) {
		this.legalOperationCity = legalOperationCity;
	}

	public String getCounsumerScanCity() {
		return counsumerScanCity;
	}

	public void setCounsumerScanCity(String counsumerScanCity) {
		this.counsumerScanCity = counsumerScanCity;
	}

	public String getCounsumerScanCounty() {
		return counsumerScanCounty;
	}

	public void setCounsumerScanCounty(String counsumerScanCounty) {
		this.counsumerScanCounty = counsumerScanCounty;
	}

	public String getCounsumerScanLatitude() {
		return counsumerScanLatitude;
	}

	public void setCounsumerScanLatitude(String counsumerScanLatitude) {
		this.counsumerScanLatitude = counsumerScanLatitude;
	}

	public String getCounsumerScanLongitude() {
		return counsumerScanLongitude;
	}

	public void setCounsumerScanLongitude(String counsumerScanLongitude) {
		this.counsumerScanLongitude = counsumerScanLongitude;
	}

	public String getCounsumerScanAddress() {
		return counsumerScanAddress;
	}

	public void setCounsumerScanAddress(String counsumerScanAddress) {
		this.counsumerScanAddress = counsumerScanAddress;
	}

	public String getTerminalProvince() {
		return terminalProvince;
	}

	public void setTerminalProvince(String terminalProvince) {
		this.terminalProvince = terminalProvince;
	}

	public String getTerminalCity() {
		return terminalCity;
	}

	public void setTerminalCity(String terminalCity) {
		this.terminalCity = terminalCity;
	}

	public String getTerminalCounty() {
		return terminalCounty;
	}

	public void setTerminalCounty(String terminalCounty) {
		this.terminalCounty = terminalCounty;
	}

	public String getWechatBillNumber() {
		return wechatBillNumber;
	}

	public void setWechatBillNumber(String wechatBillNumber) {
		this.wechatBillNumber = wechatBillNumber;
	}

	public String getAccountManagerEmployeeId() {
		return accountManagerEmployeeId;
	}

	public void setAccountManagerEmployeeId(String accountManagerEmployeeId) {
		this.accountManagerEmployeeId = accountManagerEmployeeId;
	}

	public String getAccountManagerEmployeeName() {
		return accountManagerEmployeeName;
	}

	public void setAccountManagerEmployeeName(String accountManagerEmployeeName) {
		this.accountManagerEmployeeName = accountManagerEmployeeName;
	}

	public String getAccountManagerPositionId() {
		return accountManagerPositionId;
	}

	public void setAccountManagerPositionId(String accountManagerPositionId) {
		this.accountManagerPositionId = accountManagerPositionId;
	}

	public String getAccountManagerPositionName() {
		return accountManagerPositionName;
	}

	public void setAccountManagerPositionName(String accountManagerPositionName) {
		this.accountManagerPositionName = accountManagerPositionName;
	}

	public String getSalesCategoryExternalId() {
		return salesCategoryExternalId;
	}

	public void setSalesCategoryExternalId(String salesCategoryExternalId) {
		this.salesCategoryExternalId = salesCategoryExternalId;
	}

	public String getSalesCategoryName() {
		return salesCategoryName;
	}

	public void setSalesCategoryName(String salesCategoryName) {
		this.salesCategoryName = salesCategoryName;
	}

	public TerminalType getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(TerminalType terminalType) {
		this.terminalType = terminalType;
	}
}
