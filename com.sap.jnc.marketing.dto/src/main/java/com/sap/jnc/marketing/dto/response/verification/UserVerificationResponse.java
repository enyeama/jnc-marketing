package com.sap.jnc.marketing.dto.response.verification;

import com.sap.jnc.marketing.persistence.enumeration.ExternalUserRoleType;
import com.sap.jnc.marketing.persistence.enumeration.UserReferenceType;
import com.sap.jnc.marketing.persistence.enumeration.UserVerificationStatus;
import com.sap.jnc.marketing.persistence.model.UserVerification;

import javax.persistence.*;

/**
 * Created by guokai on 16/6/28.
 */
public class UserVerificationResponse {

	private Long id;

	private String externalId;

	private String mobile;

	private String name;

	private String IDCardNumber;

	private String openId;

	private String unionId;

	private String wechatId;

	private String wechatGroup;

	private String status;

	private String roleType;

	private String referenceType;

	private Long referenceId;

	public UserVerificationResponse(UserVerification obj) {
		this.externalId = obj.getExternalId();
		this.id = obj.getId();
		this.mobile = obj.getMobile();
		this.name = obj.getName();
		this.IDCardNumber = obj.getIDCardNumber();
		this.openId = obj.getOpenId();
		this.unionId = obj.getUnionId();
		this.wechatId = obj.getWechatId();
		this.wechatGroup = obj.getWechatGroup();
		this.status = obj.getStatus().getLabel();
		this.roleType = obj.getRoleType().getLabel();
		this.referenceType = obj.getReferenceType().getLabel();
		this.referenceId = obj.getReferenceId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIDCardNumber() {
		return IDCardNumber;
	}

	public void setIDCardNumber(String IDCardNumber) {
		this.IDCardNumber = IDCardNumber;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getWechatGroup() {
		return wechatGroup;
	}

	public void setWechatGroup(String wechatGroup) {
		this.wechatGroup = wechatGroup;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}
}
