/**
 * 
 */
package com.sap.jnc.marketing.dto.request.wechat.employee;

import java.io.Serializable;

/**
 * 员工微信注册信息
 * 
 * @author I323560
 *
 */
public class WechatRegisterEmployee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5710135574779763931L;

	public WechatRegisterEmployee() {
		super();
	}

	/**
	 * 员工编号
	 */
	private String externalId;

	/**
	 * 身份证号
	 */
	private String idCardNO;

	/**
	 * 名字
	 */
	private String name;

	/**
	 * 电话号码
	 */
	private String phone;

	/**
	 * 微信openId
	 */
	private String openId;

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getIdCardNO() {
		return idCardNO;
	}

	public void setIdCardNO(String idCardNO) {
		this.idCardNO = idCardNO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
