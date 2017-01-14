package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;
import com.sap.jnc.marketing.persistence.enumeration.ConsumerFollowStatus;
import com.sap.jnc.marketing.persistence.enumeration.Gender;
import com.sap.jnc.marketing.persistence.enumeration.UserGroup;

@Entity
@Table(name = "T_CONSUMER", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
public class Consumer extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = 4156197542041858598L;

	@Id
	@Column(name = "openId")
	private String id;

	@Column(name = "userGroup")
	@Enumerated(EnumType.STRING)
	private UserGroup userGroup;

	@Column(name = "followStatus")
	private ConsumerFollowStatus followStatus;

	@Column(name = "followTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar followTime;

	@Column(name = "nickName")
	private String nickName;

	@Column(name = "gender")
	private Gender gender;

	@Column(name = "country")
	private String country;

	@Column(name = "city")
	private String city;

	@Column(name = "province")
	private String province;

	@Column(name = "language")
	private String language;

	@Column(name = "icon")
	private String icon;

	@Column(name = "unionId")
	private String unionId;

	@Column(name = "comment")
	private String comment;

	@OneToMany(mappedBy = "consumer")
	private List<IndividualProductBonus> bonuses;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserGroup getUserGroup() {
		return this.userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public ConsumerFollowStatus getFollowStatus() {
		return this.followStatus;
	}

	public void setFollowStatus(ConsumerFollowStatus followStatus) {
		this.followStatus = followStatus;
	}

	public Calendar getFollowTime() {
		return this.followTime;
	}

	public void setFollowTime(Calendar followTime) {
		this.followTime = followTime;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUnionId() {
		return this.unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<IndividualProductBonus> getBonuses() {
		return this.bonuses;
	}

	public void setBonuses(List<IndividualProductBonus> bonuses) {
		this.bonuses = bonuses;
	}
}
