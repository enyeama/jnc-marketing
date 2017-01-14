package com.sap.jnc.marketing.infrastructure.shared.utils.sms;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

public class SmsEntity implements Serializable {

	private static final long serialVersionUID = -3406159958356963286L;

	private String id;

	private String phoneNumber;

	private String code;

	private Timestamp createTime;

	private long validThru; // in seconds.

	private Integer type;

	public final static long DEFAULT_VALID_THRU = 60 * 10;

	public SmsEntity(Integer type, String phoneNumber, String code, long validThru) {
		if (code == null || !StringUtils.isNumeric(code)) {
			throw new IllegalArgumentException("invalid verification code.");
		}
		if (phoneNumber == null || !StringUtils.isNumeric(phoneNumber)) {
			throw new IllegalArgumentException("invalid phone number.");
		}
		this.id = UUID.randomUUID().toString();
		this.phoneNumber = phoneNumber;
		this.code = code;
		this.createTime = new Timestamp(System.currentTimeMillis());
		this.validThru = validThru;
		this.type = type;
	}

	public String getEntityKey() {
		return SmsType.valueOf(type).getLabel() + ":" + this.phoneNumber;
	}

	public SmsEntity(Integer type, String phoneNumber, String code) {
		this(type, phoneNumber, code, DEFAULT_VALID_THRU);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public long getValidThru() {
		return validThru;
	}

	public void setValidThru(long validThru) {
		this.validThru = validThru;
	}

	public boolean isTimeout() {
		long elasedTime = System.currentTimeMillis() - createTime.getTime();
		return elasedTime > validThru * 1000;
	}
}
