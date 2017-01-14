/**
 * 
 */
package com.sap.jnc.marketing.dto.response.contact;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.ContactType;
import com.sap.jnc.marketing.persistence.model.Contact;

/**
 * @author Quansheng Liu I075496
 */
public class ContactResponse implements Serializable {

	private static final long serialVersionUID = 4830998089033661195L;
	private Long id;

	private ContactType type;

	private String name;

	private String phone;

	private String wechat;

	private String wechatOpenId;

	private String position;

	private String IDCardNumber;

	public ContactResponse() {

	}

	public ContactResponse(Contact contact) {
		if (contact == null) {
			return;
		}
		this.setId(contact.getId());
		this.setType(contact.getType());
		this.setName(contact.getName());
		this.setPhone(contact.getPhone());
		this.setWechat(contact.getWechat());
		this.setWechatOpenId(contact.getWechatOpenId());
		this.setIDCardNumber(contact.getIDCardNumber());
		this.setPosition(contact.getPosition());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ContactType getType() {
		return type;
	}

	public void setType(ContactType type) {
		this.type = type;
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

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getWechatOpenId() {
		return wechatOpenId;
	}

	public void setWechatOpenId(String wechatOpenId) {
		this.wechatOpenId = wechatOpenId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getIDCardNumber() {
		return IDCardNumber;
	}

	public void setIDCardNumber(String iDCardNumber) {
		IDCardNumber = iDCardNumber;
	}

}
