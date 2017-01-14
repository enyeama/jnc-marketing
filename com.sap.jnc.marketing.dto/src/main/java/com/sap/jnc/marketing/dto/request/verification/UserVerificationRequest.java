package com.sap.jnc.marketing.dto.request.verification;

import com.sap.jnc.marketing.persistence.enumeration.ExternalUserRoleType;
import com.sap.jnc.marketing.persistence.enumeration.ReferenceType;
import com.sap.jnc.marketing.persistence.enumeration.UserReferenceType;
import com.sap.jnc.marketing.persistence.enumeration.UserVerificationStatus;
import com.sap.jnc.marketing.persistence.model.UserVerification;
import com.sap.jnc.marketing.persistence.model.UserVerificationForTerminal;
import org.springframework.util.StringUtils;

/**
 * Created by guokai on 16/6/28.
 */
public class UserVerificationRequest {

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

	public UserVerificationForTerminal toUserVerification(UserVerificationForTerminal obj) {
		obj.setExternalId(externalId == null ? obj.getExternalId() : externalId);
		obj.setId(id == null ? obj.getId() : id);
		obj.setMobile(StringUtils.isEmpty(mobile) ? obj.getMobile() : mobile);
		obj.setName(StringUtils.isEmpty(name) ? obj.getName() : name);
		obj.setIDCardNumber(StringUtils.isEmpty(IDCardNumber) ? obj.getIDCardNumber() : IDCardNumber);
		obj.setOpenId(StringUtils.isEmpty(openId) ? obj.getOpenId() : openId);
		obj.setUnionId(StringUtils.isEmpty(unionId) ? obj.getUnionId() : unionId);
		obj.setWechatId(StringUtils.isEmpty(wechatId) ? obj.getWechatId() : wechatId);
		obj.setWechatGroup(StringUtils.isEmpty(wechatGroup) ? obj.getWechatGroup() : wechatGroup);
		obj.setStatus(status == null ? obj.getStatus() : UserVerificationStatus.valueOf(status));
		obj.setRoleType(roleType == null ? obj.getRoleType() : ExternalUserRoleType.valueOf(roleType));
		obj.setReferenceType(referenceType == null ? obj.getReferenceType() : UserReferenceType.valueOf(referenceType));
		obj.setReferenceId(referenceId == null ? obj.getReferenceId() : referenceId);
		return obj;
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
