package com.sap.jnc.marketing.dto.request.user;

import com.sap.jnc.marketing.persistence.enumeration.ConsumerFollowStatus;
import com.sap.jnc.marketing.persistence.enumeration.Gender;

/**
 * Created by dy on 16/6/21.
 */
public class ConsumerRequest {

	private String openId;

	private ConsumerFollowStatus followStatus;

	private String nickName;

	private Gender gender;

	private String country;

	private String province;

	private String language;

	private String icon;

	private String followTime;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public ConsumerFollowStatus getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(ConsumerFollowStatus followStatus) {
		this.followStatus = followStatus;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getFollowTime() {
		return followTime;
	}

	public void setFollowTime(String followTime) {
		this.followTime = followTime;
	}
}
