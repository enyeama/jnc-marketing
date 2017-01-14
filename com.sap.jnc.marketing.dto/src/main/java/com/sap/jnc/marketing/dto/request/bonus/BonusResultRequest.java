/**
 * 
 */
package com.sap.jnc.marketing.dto.request.bonus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 红包发送结果信息
 * 
 * @author I323560
 *
 */
public class BonusResultRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6887009922668381432L;

	public BonusResultRequest() {
		super();
	}

	/**
	 * 瓶盖内码
	 */
	private String capInnerCode;

	/**
	 * 纬度
	 */
	private BigDecimal latitude;

	/**
	 * 经度
	 */
	private BigDecimal longitude;

	/**
	 * 活动名称
	 */
	private String activityName;

	/**
	 * 公众账号AppId
	 */
	private String wechatSubscriptionAccountAppId;

	/**
	 * 商户号
	 */
	private String wechatPaymentAccountId;

	/**
	 * 消费者微信编号
	 */
	private String consumerOpenId;

	/**
	 * 红包单号
	 */
	private String wechatBonusNumber;

	/**
	 * 祝福语
	 */
	private String message;

	public String getCapInnerCode() {
		return capInnerCode;
	}

	public void setCapInnerCode(String capInnerCode) {
		this.capInnerCode = capInnerCode;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getWechatSubscriptionAccountAppId() {
		return wechatSubscriptionAccountAppId;
	}

	public void setWechatSubscriptionAccountAppId(String wechatSubscriptionAccountAppId) {
		this.wechatSubscriptionAccountAppId = wechatSubscriptionAccountAppId;
	}

	public String getWechatPaymentAccountId() {
		return wechatPaymentAccountId;
	}

	public void setWechatPaymentAccountId(String wechatPaymentAccountId) {
		this.wechatPaymentAccountId = wechatPaymentAccountId;
	}

	public String getConsumerOpenId() {
		return consumerOpenId;
	}

	public void setConsumerOpenId(String consumerOpenId) {
		this.consumerOpenId = consumerOpenId;
	}

	public String getWechatBonusNumber() {
		return wechatBonusNumber;
	}

	public void setWechatBonusNumber(String wechatBonusNumber) {
		this.wechatBonusNumber = wechatBonusNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "BonusResultRequest{" + "latitude=" + latitude + ", longitude=" + longitude + ", activityName='"
				+ activityName + '\'' + ", wechatSubscriptionAccountAppId='" + wechatSubscriptionAccountAppId + '\''
				+ ", wechatPaymentAccountId='" + wechatPaymentAccountId + '\'' + ", consumerOpenId='" + consumerOpenId
				+ '\'' + ", wechatBonusNumber='" + wechatBonusNumber + '\'' + ", message='" + message + '\'' + '}';
	}
}
