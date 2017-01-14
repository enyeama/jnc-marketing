package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.sap.jnc.marketing.persistence.enumeration.Gender;

@Embeddable
public class WechatAccount implements Serializable {

	private static final long serialVersionUID = -8779325115445356278L;

	@Column(name = "wechatAccountOpenId")
	private String openId;

	@Column(name = "wechatAccountGroup")
	private String group;

	@Column(name = "wechatAccountUserName")
	private String userName;

	@Column(name = "wechatAccountGender")
	private Gender gender;

	@Column(name = "wechatAccountHeadImgUrl")
	private String headImgUrl;

	@Column(name = "wechatAccountCountry")
	private String country;

	@Column(name = "wechatAccountProvince")
	private String province;

	@Column(name = "wechatAccountCity")
	private String city;

	@Column(name = "wechatAccountSubscribeAt")
	private Calendar subscribeAt;

	@Column(name = "wechatAccountUnsubscribeAt")
	private Calendar unsubscribeAt;

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getHeadImgUrl() {
		return this.headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Calendar getSubscribeAt() {
		return this.subscribeAt;
	}

	public void setSubscribeAt(Calendar subscribeAt) {
		this.subscribeAt = subscribeAt;
	}

	public Calendar getUnsubscribeAt() {
		return this.unsubscribeAt;
	}

	public void setUnsubscribeAt(Calendar unsubscribeAt) {
		this.unsubscribeAt = unsubscribeAt;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
}
