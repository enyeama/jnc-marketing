/**
 * 
 */
package com.sap.jnc.marketing.dto.shared.ka;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.BranchLevel;
import com.sap.jnc.marketing.persistence.enumeration.PromoterType;
import com.sap.jnc.marketing.persistence.model.Contact;
import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Region;
import com.sap.jnc.marketing.persistence.model.Terminal;

/**
 * @author Quansheng Liu I075496
 */
public class KAImportRecord implements Serializable {

	private static final long serialVersionUID = -6002763406493677573L;
	private String externalId;
	private String kaOfficeDescription;
	private String branchName;
	private String address;
	private String storeNumber;
	private String kaSystemName;
	private String kaProducts;
	private String accountManagerPositionExternalId;
	private String accountManagerEmployeeName;
	private String specialistPositionExternalId;
	private String specialistEmployeeName;
	private String maintainerPositionExternalId;
	private String maintainerEmployeeName;
	private String province;
	private String city;
	private String county;
	private String countyId;
	private String businessStatus;
	private String branchLevelStr;
	private String systemPropertyNumber;
	private String systemPropertyName;
	private String keyUserContactName;
	private String keyUserContactPhone;
	private String keyUserContactWechat;
	private String purchaseContactName;
	private String purchaseContactPhone;
	private String purchaseContactWechat;
	private String kaPurchaseContactName;
	private String kaPurchaseContactPhone;
	private String kaPurchaseContactWechat;
	private String promoterContactName;
	private String promoterTypeStr;
	private String promoterContactPhone;
	private String promoterContactWechat;

	public KAImportRecord() {

	}

	public KAImportRecord(Terminal terminal) {
		this.setExternalId(terminal.getExternalId());
		DepartmentView kaOffice = terminal.getKAOffice();
		if (kaOffice != null) {
			this.setKaOfficeDescription(kaOffice.getName());
		}
		this.setBranchName(terminal.getBranchName());
		this.setAddress(terminal.getAddress());
		this.setStoreNumber(terminal.getStoreNumber());
		this.setKaSystemName(terminal.getKASystemName());
		this.setKaProducts(terminal.getKAProducts());
		PositionView kaAccountManagerPosition = terminal.getKAAcountManager();
		if (kaAccountManagerPosition != null) {
			this.setAccountManagerPositionExternalId(kaAccountManagerPosition.getExternalId());
			EmployeeView kaAccountManagerEmployee = kaAccountManagerPosition.getEmployee();
			if (kaAccountManagerEmployee != null) {
				this.setAccountManagerEmployeeName(kaAccountManagerEmployee.getName());
			}
		}
		PositionView kaSpecialistPosition = terminal.getKASpecialist();
		if (kaSpecialistPosition != null) {
			this.setSpecialistPositionExternalId(kaSpecialistPosition.getExternalId());
			EmployeeView kaSpecialistEmployee = kaSpecialistPosition.getEmployee();
			if (kaSpecialistEmployee != null) {
				this.setSpecialistEmployeeName(kaSpecialistEmployee.getName());
			}
		}
		PositionView maintainerPosition = terminal.getMaintainer();
		if (maintainerPosition != null) {
			this.setMaintainerPositionExternalId(maintainerPosition.getExternalId());
			EmployeeView maintainerEmployee = maintainerPosition.getEmployee();
			if (maintainerEmployee != null) {
				this.setMaintainerEmployeeName(maintainerEmployee.getName());
			}
		}
		Region region = terminal.getRegion();
		if (region != null) {
			this.setProvince(region.getProvinceName());
			this.setCity(region.getCityName());
			this.setCounty(region.getCountyName());
			this.setCountyId(region.getCountyId());
		}
		this.setBusinessStatus(terminal.getBusinessStatus());
		BranchLevel branchLevel = terminal.getBranchLevel();
		if (branchLevel != null) {
			this.setBranchLevelStr(branchLevel.getLabel());
		}
		this.setSystemPropertyNumber(terminal.getKASystemPropertyNumber());
		this.setSystemPropertyName(terminal.getKASystemPropertyName());
		Contact keyUserContact = terminal.getKeyUserContact();
		if (keyUserContact != null) {
			this.setKeyUserContactName(keyUserContact.getName());
			this.setKeyUserContactPhone(keyUserContact.getPhone());
			this.setKeyUserContactWechat(keyUserContact.getWechat());
		}
		Contact purchaseContact = terminal.getPurchaserContact();
		if (purchaseContact != null) {
			this.setPurchaseContactName(purchaseContact.getName());
			this.setPurchaseContactPhone(purchaseContact.getPhone());
			this.setPurchaseContactWechat(purchaseContact.getWechat());
		}
		Contact kaPurchaseContact = terminal.getKaPurchaserContact();
		if (kaPurchaseContact != null) {
			this.setKaPurchaseContactName(kaPurchaseContact.getName());
			this.setKaPurchaseContactPhone(kaPurchaseContact.getPhone());
			this.setKaPurchaseContactWechat(kaPurchaseContact.getWechat());
		}
		PromoterType promoterType = terminal.getPromoterType();
		if (promoterType != null) {
			this.setPromoterTypeStr(promoterType.getLabel());
		}
		Contact promoterContact = terminal.getPromoterContact();
		if (promoterContact != null) {
			this.setPromoterContactName(promoterContact.getName());
			this.setPromoterContactPhone(promoterContact.getPhone());
			this.setPromoterContactWechat(promoterContact.getWechat());
		}
	}

	public Object[] getValues() {
		Object[] objects = { this.getExternalId(), this.getKaOfficeDescription(), this.getBranchName(), this.getAddress(), this.getStoreNumber(), this
			.getKaSystemName(), this.getKaProducts(), this.getAccountManagerPositionExternalId(), this.getAccountManagerEmployeeName(), this
				.getSpecialistPositionExternalId(), this.getSpecialistEmployeeName(), this.getMaintainerPositionExternalId(), this
					.getMaintainerEmployeeName(), this.getProvince(), this.getCity(), this.getCounty(), this.getCountyId(), this.getBusinessStatus(),
			this.getBranchLevelStr(), this.getSystemPropertyNumber(), this.getSystemPropertyName(), this.getKeyUserContactName(), this
				.getKeyUserContactPhone(), this.getKeyUserContactWechat(), this.getPurchaseContactName(), this.getPurchaseContactPhone(), this
					.getPurchaseContactWechat(), this.getKaPurchaseContactName(), this.getKaPurchaseContactPhone(), this.getKaPurchaseContactWechat(),
			this.getPromoterContactName(), this.getPromoterTypeStr(), this.getPromoterContactPhone(), this.getPromoterContactWechat() };
		return objects;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getKaOfficeDescription() {
		return kaOfficeDescription;
	}

	public void setKaOfficeDescription(String kaOfficeDescription) {
		this.kaOfficeDescription = kaOfficeDescription;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getKaSystemName() {
		return kaSystemName;
	}

	public void setKaSystemName(String kaSystemName) {
		this.kaSystemName = kaSystemName;
	}

	public String getKaProducts() {
		return kaProducts;
	}

	public void setKaProducts(String kaProducts) {
		this.kaProducts = kaProducts;
	}

	public String getAccountManagerPositionExternalId() {
		return accountManagerPositionExternalId;
	}

	public void setAccountManagerPositionExternalId(String accountManagerPositionExternalId) {
		this.accountManagerPositionExternalId = accountManagerPositionExternalId;
	}

	public String getAccountManagerEmployeeName() {
		return accountManagerEmployeeName;
	}

	public void setAccountManagerEmployeeName(String accountManagerEmployeeName) {
		this.accountManagerEmployeeName = accountManagerEmployeeName;
	}

	public String getSpecialistPositionExternalId() {
		return specialistPositionExternalId;
	}

	public void setSpecialistPositionExternalId(String specialistPositionExternalId) {
		this.specialistPositionExternalId = specialistPositionExternalId;
	}

	public String getSpecialistEmployeeName() {
		return specialistEmployeeName;
	}

	public void setSpecialistEmployeeName(String specialistEmployeeName) {
		this.specialistEmployeeName = specialistEmployeeName;
	}

	public String getMaintainerPositionExternalId() {
		return maintainerPositionExternalId;
	}

	public void setMaintainerPositionExternalId(String maintainerPositionExternalId) {
		this.maintainerPositionExternalId = maintainerPositionExternalId;
	}

	public String getMaintainerEmployeeName() {
		return maintainerEmployeeName;
	}

	public void setMaintainerEmployeeName(String maintainerEmployeeName) {
		this.maintainerEmployeeName = maintainerEmployeeName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public String getBranchLevelStr() {
		return branchLevelStr;
	}

	public void setBranchLevelStr(String branchLevelStr) {
		this.branchLevelStr = branchLevelStr;
	}

	public String getSystemPropertyNumber() {
		return systemPropertyNumber;
	}

	public void setSystemPropertyNumber(String systemPropertyNumber) {
		this.systemPropertyNumber = systemPropertyNumber;
	}

	public String getSystemPropertyName() {
		return systemPropertyName;
	}

	public void setSystemPropertyName(String systemPropertyName) {
		this.systemPropertyName = systemPropertyName;
	}

	public String getKeyUserContactName() {
		return keyUserContactName;
	}

	public void setKeyUserContactName(String keyUserContactName) {
		this.keyUserContactName = keyUserContactName;
	}

	public String getKeyUserContactPhone() {
		return keyUserContactPhone;
	}

	public void setKeyUserContactPhone(String keyUserContactPhone) {
		this.keyUserContactPhone = keyUserContactPhone;
	}

	public String getKeyUserContactWechat() {
		return keyUserContactWechat;
	}

	public void setKeyUserContactWechat(String keyUserContactWechat) {
		this.keyUserContactWechat = keyUserContactWechat;
	}

	public String getPurchaseContactName() {
		return purchaseContactName;
	}

	public void setPurchaseContactName(String purchaseContactName) {
		this.purchaseContactName = purchaseContactName;
	}

	public String getPurchaseContactPhone() {
		return purchaseContactPhone;
	}

	public void setPurchaseContactPhone(String purchaseContactPhone) {
		this.purchaseContactPhone = purchaseContactPhone;
	}

	public String getPurchaseContactWechat() {
		return purchaseContactWechat;
	}

	public void setPurchaseContactWechat(String purchaseContactWechat) {
		this.purchaseContactWechat = purchaseContactWechat;
	}

	public String getKaPurchaseContactName() {
		return kaPurchaseContactName;
	}

	public void setKaPurchaseContactName(String kaPurchaseContactName) {
		this.kaPurchaseContactName = kaPurchaseContactName;
	}

	public String getKaPurchaseContactPhone() {
		return kaPurchaseContactPhone;
	}

	public void setKaPurchaseContactPhone(String kaPurchaseContactPhone) {
		this.kaPurchaseContactPhone = kaPurchaseContactPhone;
	}

	public String getKaPurchaseContactWechat() {
		return kaPurchaseContactWechat;
	}

	public void setKaPurchaseContactWechat(String kaPurchaseContactWechat) {
		this.kaPurchaseContactWechat = kaPurchaseContactWechat;
	}

	public String getPromoterContactName() {
		return promoterContactName;
	}

	public void setPromoterContactName(String promoterContactName) {
		this.promoterContactName = promoterContactName;
	}

	public String getPromoterTypeStr() {
		return promoterTypeStr;
	}

	public void setPromoterTypeStr(String promoterTypeStr) {
		this.promoterTypeStr = promoterTypeStr;
	}

	public String getPromoterContactPhone() {
		return promoterContactPhone;
	}

	public void setPromoterContactPhone(String promoterContactPhone) {
		this.promoterContactPhone = promoterContactPhone;
	}

	public String getPromoterContactWechat() {
		return promoterContactWechat;
	}

	public void setPromoterContactWechat(String promoterContactWechat) {
		this.promoterContactWechat = promoterContactWechat;
	}

}
