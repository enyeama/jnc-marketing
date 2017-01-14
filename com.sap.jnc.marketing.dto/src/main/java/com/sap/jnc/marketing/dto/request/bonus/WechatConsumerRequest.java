/**
 * 
 */
package com.sap.jnc.marketing.dto.request.bonus;

import java.io.Serializable;
import java.util.Calendar;

import com.sap.jnc.marketing.persistence.enumeration.ConsumerFollowStatus;
import com.sap.jnc.marketing.persistence.enumeration.Gender;

/**
 * 消费者微信信息
 * 
 * @author I323560
 *
 */
public class WechatConsumerRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6887009922668381432L;

	public WechatConsumerRequest() {
		super();
	}

	/**
	 * 微信OpenId
	 */
	private String openId;

	/**
	 * 公众号关注状态
	 */
	private ConsumerFollowStatus followStatus;

	/**
	 * 关注时间
	 */
	private Calendar followTime;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 性别
	 */
	private Gender gender;

	/**
	 * 国家
	 */
	private String country;

	/**
	 * 省
	 */
	private String province;

	/**
	 * 市
	 */
	private String city;

	/**
	 * 语言
	 */
	private String language;

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

	public Calendar getFollowTime() {
		return followTime;
	}

	public void setFollowTime(Calendar followTime) {
		this.followTime = followTime;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
