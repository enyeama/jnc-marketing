package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.ExternalUserRoleType;
import com.sap.jnc.marketing.persistence.enumeration.UserReferenceType;
import com.sap.jnc.marketing.persistence.enumeration.UserVerificationStatus;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "referenceType")
@Table(name = "T_USER_VERIFICATION", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "UserVerificationSeq", sequenceName = "SEQ_USER_VERIFICATION")
public class UserVerification implements Serializable {

	private static final long serialVersionUID = 5518382706266480331L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserVerificationSeq")
	private Long id;

	@Column(name = "externalId")
	private String externalId;

	@Column(name = "mobile", unique = true)
	private String mobile;

	@Column(name = "name")
	private String name;

	@Column(name = "IDCardNumber")
	private String IDCardNumber;

	@Column(name = "openId")
	private String openId;

	@Column(name = "unionId")
	private String unionId;

	@Column(name = "wechatId")
	private String wechatId;

	@Column(name = "wechatGroup")
	private String wechatGroup;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private UserVerificationStatus status;

	@Column(name = "roleType")
	@Enumerated(EnumType.STRING)
	private ExternalUserRoleType roleType;

	@Column(name = "referenceType", insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private UserReferenceType referenceType;

	@Column(name = "referenceId", insertable = false, updatable = false)
	private Long referenceId;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return this.unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getWechatId() {
		return this.wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getWechatGroup() {
		return this.wechatGroup;
	}

	public void setWechatGroup(String wechatGroup) {
		this.wechatGroup = wechatGroup;
	}

	public UserReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(UserReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public ExternalUserRoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(ExternalUserRoleType roleType) {
		this.roleType = roleType;
	}

	public UserVerificationStatus getStatus() {
		return status;
	}

	public void setStatus(UserVerificationStatus status) {
		this.status = status;
	}

	public String getIDCardNumber() {
		return IDCardNumber;
	}

	public void setIDCardNumber(String iDCardNumber) {
		IDCardNumber = iDCardNumber;
	}
}
