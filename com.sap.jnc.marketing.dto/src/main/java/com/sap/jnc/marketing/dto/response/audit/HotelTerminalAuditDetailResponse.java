package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;
import java.util.Calendar;

import com.sap.jnc.marketing.persistence.model.AuditTerminal;

/**
 * @author I330182 Vodka Li
 *
 */
public class HotelTerminalAuditDetailResponse implements Serializable {

	private static final long serialVersionUID = -4094506791679755814L;

	private Calendar createOn;
	private Calendar assignTime;
	private String branchName;
	private String address;
	private String provinceManagerName;
	private String provinceManagerId;
	private String pictureURL;
	private boolean isBanquetHotel;
	private String title;
	private String warehouseAddres;
	private String businessLicenseName;
	private String registrantName;
	private String phone;
	private String keyUserContactPosition;
	private String purchaseContactName;
	private String purchaseContactPhone;
	private String purchaseContactWechat;
	private String supervisorContactName;
	private String supervisorContactPhone;
	private String supervisorContactWechat;
	private String businessContactName;
	private String businessContactPhone;
	private String businessContactPosition;
	private String businessContactWechat;
	private String promoterName;
	private String promoterPhone;
	private String promoterWechat;
	private String licensePictureURL;

	public HotelTerminalAuditDetailResponse(AuditTerminal auditTerminal) {
		if (auditTerminal == null) {
			return;
		}
		this.setCreateOn(auditTerminal.getCreateOn());
		this.setAssignTime(auditTerminal.getAssignTime());

		if (null != auditTerminal.getTerminal()) {
			this.setBranchName(auditTerminal.getTerminal().getBranchName());
			this.setAddress(auditTerminal.getTerminal().getAddress());
			this.setPictureURL(auditTerminal.getTerminal().getPictureURL());
			this.setBanquetHotel(auditTerminal.getTerminal().getIsBanquetHotel());
			this.setWarehouseAddres(auditTerminal.getTerminal().getWarehouseAddress());
			this.setBusinessLicenseName(auditTerminal.getTerminal().getBusinessLicenseName());
			this.setTitle(auditTerminal.getTerminal().getTitle());
			this.setRegistrantName(auditTerminal.getTerminal().getRegistrantName());
			this.setPhone(auditTerminal.getTerminal().getPhone());
			this.setLicensePictureURL(auditTerminal.getTerminal().getLicensePictureURL());
			if (null != auditTerminal.getTerminal().getPurchaserContact()) {
				this.setPurchaseContactName(auditTerminal.getTerminal().getPurchaserContact().getName());
				this.setPromoterPhone(auditTerminal.getTerminal().getPurchaserContact().getPhone());
				this.setPurchaseContactWechat(auditTerminal.getTerminal().getPurchaserContact().getWechat());
			}
			if (null != auditTerminal.getTerminal().getSupervisorContact()) {
				this.setSupervisorContactName(auditTerminal.getTerminal().getSupervisorContact().getName());
				this.setSupervisorContactPhone(auditTerminal.getTerminal().getSupervisorContact().getWechat());
				this.setSupervisorContactWechat(auditTerminal.getTerminal().getSupervisorContact().getWechat());
				this.setBusinessContactName(auditTerminal.getTerminal().getSupervisorContact().getName());
				this.setBusinessContactPhone(auditTerminal.getTerminal().getSupervisorContact().getPhone());
				this.setBusinessContactPosition(auditTerminal.getTerminal().getSupervisorContact().getPosition());
				this.setBusinessContactWechat(auditTerminal.getTerminal().getSupervisorContact().getWechat());
			}
			if (null != auditTerminal.getTerminal().getPromoterContact()) {
				this.setPromoterName(auditTerminal.getTerminal().getPromoterContact().getName());
				this.setPromoterPhone(auditTerminal.getTerminal().getPromoterContact().getPhone());
				this.setPromoterWechat(auditTerminal.getTerminal().getPromoterContact().getWechat());
			}
			if (null != auditTerminal.getTerminal().getKeyUserContact()) {
				this.setKeyUserContactPosition(auditTerminal.getTerminal().getKeyUserContact().getPosition());
			}

		}
		if (null != auditTerminal.getProvinceManager()) {
			this.setProvinceManagerName(auditTerminal.getProvinceManager().getName());
			this.setProvinceManagerId(auditTerminal.getProvinceManager().getIdCardNO());
		}

	}

	public Calendar getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Calendar createOn) {
		this.createOn = createOn;
	}

	public Calendar getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Calendar assignTime) {
		this.assignTime = assignTime;
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

	public String getProvinceManagerName() {
		return provinceManagerName;
	}

	public void setProvinceManagerName(String provinceManagerName) {
		this.provinceManagerName = provinceManagerName;
	}

	public String getProvinceManagerId() {
		return provinceManagerId;
	}

	public void setProvinceManagerId(String provinceManagerId) {
		this.provinceManagerId = provinceManagerId;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public boolean isBanquetHotel() {
		return isBanquetHotel;
	}

	public void setBanquetHotel(boolean isBanquetHotel) {
		this.isBanquetHotel = isBanquetHotel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWarehouseAddres() {
		return warehouseAddres;
	}

	public void setWarehouseAddres(String warehouseAddres) {
		this.warehouseAddres = warehouseAddres;
	}

	public String getBusinessLicenseName() {
		return businessLicenseName;
	}

	public void setBusinessLicenseName(String businessLicenseName) {
		this.businessLicenseName = businessLicenseName;
	}

	public String getRegistrantName() {
		return registrantName;
	}

	public void setRegistrantName(String registrantName) {
		this.registrantName = registrantName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getKeyUserContactPosition() {
		return keyUserContactPosition;
	}

	public void setKeyUserContactPosition(String keyUserContactPosition) {
		this.keyUserContactPosition = keyUserContactPosition;
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

	public String getSupervisorContactName() {
		return supervisorContactName;
	}

	public void setSupervisorContactName(String supervisorContactName) {
		this.supervisorContactName = supervisorContactName;
	}

	public String getSupervisorContactPhone() {
		return supervisorContactPhone;
	}

	public void setSupervisorContactPhone(String supervisorContactPhone) {
		this.supervisorContactPhone = supervisorContactPhone;
	}

	public String getSupervisorContactWechat() {
		return supervisorContactWechat;
	}

	public void setSupervisorContactWechat(String supervisorContactWechat) {
		this.supervisorContactWechat = supervisorContactWechat;
	}

	public String getBusinessContactName() {
		return businessContactName;
	}

	public void setBusinessContactName(String businessContactName) {
		this.businessContactName = businessContactName;
	}

	public String getBusinessContactPhone() {
		return businessContactPhone;
	}

	public void setBusinessContactPhone(String businessContactPhone) {
		this.businessContactPhone = businessContactPhone;
	}

	public String getBusinessContactPosition() {
		return businessContactPosition;
	}

	public void setBusinessContactPosition(String businessContactPosition) {
		this.businessContactPosition = businessContactPosition;
	}

	public String getBusinessContactWechat() {
		return businessContactWechat;
	}

	public void setBusinessContactWechat(String businessContactWechat) {
		this.businessContactWechat = businessContactWechat;
	}

	public String getPromoterName() {
		return promoterName;
	}

	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}

	public String getPromoterPhone() {
		return promoterPhone;
	}

	public void setPromoterPhone(String promoterPhone) {
		this.promoterPhone = promoterPhone;
	}

	public String getPromoterWechat() {
		return promoterWechat;
	}

	public void setPromoterWechat(String promoterWechat) {
		this.promoterWechat = promoterWechat;
	}

	public String getLicensePictureURL() {
		return licensePictureURL;
	}

	public void setLicensePictureURL(String licensePictureURL) {
		this.licensePictureURL = licensePictureURL;
	}

}
