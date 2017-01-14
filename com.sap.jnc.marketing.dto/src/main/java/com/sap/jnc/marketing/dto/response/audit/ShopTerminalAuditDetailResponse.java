package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;
import java.util.Calendar;

import com.sap.jnc.marketing.persistence.model.AuditTerminal;

/**
 * @author I330182 Vodka Li
 *
 */
public class ShopTerminalAuditDetailResponse implements Serializable{

	private static final long serialVersionUID = 5943491750542825095L;
	
	private String provinceManagerName;
	private String provinceManagerId;
	private Calendar createOn;
	private Calendar assignTime;
	private String title;
	private String pictureURL;
	private String branchName;
	private String address;
	private String businessLicenseName;
	private String registrantName;
	private String phone;
	private String cashPersonName;
	private String cashPersonMobile;
	private String cashPersonWechat;
	private String goodsReceiverWechat;
	private boolean InWholeSaleMarket;
	private String licensePictureURL;

	public ShopTerminalAuditDetailResponse(AuditTerminal auditTerminal){
		if(auditTerminal==null){
			return;
		}
		this.setCreateOn(auditTerminal.getCreateOn());
		this.setAssignTime(auditTerminal.getAssignTime());
		if(null!=auditTerminal.getTerminal()){
			this.setTitle(auditTerminal.getTerminal().getTitle());
			this.setPictureURL(auditTerminal.getTerminal().getPictureURL());
			this.setBranchName(auditTerminal.getTerminal().getBranchName());
			this.setAddress(auditTerminal.getTerminal().getAddress());
			this.setBusinessLicenseName(auditTerminal.getTerminal().getBusinessLicenseName());
			this.setRegistrantName(auditTerminal.getTerminal().getRegistrantName());
			this.setPhone(auditTerminal.getTerminal().getPhone());
			this.setCashPersonName(auditTerminal.getTerminal().getCashPersonName());
			this.setCashPersonMobile(auditTerminal.getTerminal().getCashPersonMobile());
			this.setCashPersonWechat(auditTerminal.getTerminal().getCashPersonWechat());
			this.setGoodsReceiverWechat(auditTerminal.getTerminal().getGoodsReceiverWechat());
			this.setInWholeSaleMarket(auditTerminal.getTerminal().getInWholeSaleMarket());
			this.setLicensePictureURL(auditTerminal.getTerminal().getLicensePictureURL());
		}
		if(null!=auditTerminal.getProvinceManager()){
			this.setProvinceManagerName(auditTerminal.getProvinceManager().getName());
			this.setProvinceManagerId(auditTerminal.getProvinceManager().getIdCardNO());
		}
	}
	
	public String getProvinceManagerName(){
		return provinceManagerName;
	}
	
	public String getProvinceManagerId(){
		return provinceManagerId;
	}
	
	public void setProvinceManagerId(String provinceManagerId){
		this.provinceManagerId=provinceManagerId;
	}
	
	public void setProvinceManagerName(String provinceManagerName){
		this.provinceManagerName=provinceManagerName;
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
	
	public String getPictureURL(){
		return pictureURL;
	}
	
	public void setPictureURL(String pictureURL){
		this.pictureURL=pictureURL;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title=title;
	}
	
	public String getBranchName(){
		return branchName;
	}
	
	public void setBranchName(String branchName){
		this.branchName=branchName;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setAddress(String address){
		this.address=address;
	}
	
	public String getBusinessLicenseName(){
		return businessLicenseName;
	}
	
	public void setBusinessLicenseName(String businessLicenseName){
		this.businessLicenseName=businessLicenseName;
	}
	
	public String getRegistrantName(){
		return registrantName;
	}
	
	public void setRegistrantName(String registrantName){
		this.registrantName=registrantName;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public void setPhone(String phone){
		this.phone=phone;
	}
	
	public String getCashPersonName(){
		return cashPersonName;
	}
	
	public void setCashPersonName(String cashPersonName){
		this.cashPersonName= cashPersonName;
	}
	
	public String getCashPersonMobile(){
		return cashPersonMobile;
	}
	
	public void setCashPersonMobile(String cashPersonMobile){
		this.cashPersonMobile=cashPersonMobile;
	}
	
	public String getCashPersonWechat(){
		return cashPersonWechat;
	}
	
	public void setCashPersonWechat(String cashPersonWechat){
		this.cashPersonWechat=cashPersonWechat;
	}
	
	public String getGoodsReceiverWechat(){
		return goodsReceiverWechat;
	}
	
	public void setGoodsReceiverWechat(String goodsReceiverWechat){
		this.goodsReceiverWechat=goodsReceiverWechat;
	}
	
	public boolean getInWholeSaleMarket(){
		return InWholeSaleMarket;
	}
	
	public void setInWholeSaleMarket(boolean InWholeSaleMarket){
		this.InWholeSaleMarket=InWholeSaleMarket;
	}
	
	public String getLicensePictureURL(){
		return licensePictureURL;
	}
	
	public void setLicensePictureURL(String licensePictureURL){
		this.licensePictureURL=licensePictureURL;
	}
	
}