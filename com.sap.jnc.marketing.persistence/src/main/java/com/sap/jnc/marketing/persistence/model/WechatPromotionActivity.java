package com.sap.jnc.marketing.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.jnc.marketing.persistence.config.PersistenceConfig;

@Entity
@Table(name = "T_WECHAT_PROMOTION_ACTIVITY", schema = PersistenceConfig.ENTITY_SCHEMA_NAME)
@SequenceGenerator(schema = PersistenceConfig.ENTITY_SCHEMA_NAME, allocationSize = 1, name = "WechatPromotionActivitySeq", sequenceName = "SEQ_WECHAT_PROMOTION_ACT")
public class WechatPromotionActivity extends ModificatoryEntity implements Serializable {

	private static final long serialVersionUID = -2253511686387815770L;

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WechatPromotionActivitySeq")
	private Long id;

	@Column(name = "activityId")
	private String activityId;
	
	@Column(name = "wechatOpenID")
	private String wechatOpenID;

	@Column(name = "wechatName")
	private String wechatName;

	@Column(name = "wechatId")
	private String wechatId;

	@Column(name = "url")
	private String url;

	@Column(name = "receiveDate")
	private Calendar receiveDate;

	@Column(name = "addressRecommend")
	private String addressRecommend;

	@Column(name = "addressAdcode")
	private String addressAdcode;

	@Column(name = "addressNation")
	private String addressNation;

	@Column(name = "addressProvince")
	private String addressProvince;

	@Column(name = "addressCity")
	private String addressCity;

	@Column(name = "addressDistrict")
	private String addressDistrict;

	@Column(name = "addressStreet")
	private String addressStreet;

	@Column(name = "addressStreetNumber")
	private String addressStreetNumber;

	@Column(name = "gpsLongitude", precision = 34, scale = 10)
	private BigDecimal longitude;

	@Column(name = "gpsLatitude", precision = 34, scale = 10)
	private BigDecimal latitude;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getWechatOpenID() {
		return wechatOpenID;
	}

	public void setWechatOpenID(String wechatOpenID) {
		this.wechatOpenID = wechatOpenID;
	}

	public String getWechatName() {
		return wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Calendar getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Calendar receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getAddressRecommend() {
		return addressRecommend;
	}

	public void setAddressRecommend(String addressRecommend) {
		this.addressRecommend = addressRecommend;
	}

	public String getAddressAdcode() {
		return addressAdcode;
	}

	public void setAddressAdcode(String addressAdcode) {
		this.addressAdcode = addressAdcode;
	}

	public String getAddressNation() {
		return addressNation;
	}

	public void setAddressNation(String addressNation) {
		this.addressNation = addressNation;
	}

	public String getAddressProvince() {
		return addressProvince;
	}

	public void setAddressProvince(String addressProvince) {
		this.addressProvince = addressProvince;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressDistrict() {
		return addressDistrict;
	}

	public void setAddressDistrict(String addressDistrict) {
		this.addressDistrict = addressDistrict;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddressStreetNumber() {
		return addressStreetNumber;
	}

	public void setAddressStreetNumber(String addressStreetNumber) {
		this.addressStreetNumber = addressStreetNumber;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
}
