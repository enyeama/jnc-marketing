package com.sap.jnc.marketing.dto.shared.contact;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.ContactType;
import com.sap.jnc.marketing.persistence.model.Contact;

public class ContactNode implements Serializable {

	private static final long serialVersionUID = -2556817043697456503L;

	private Long id;

	private ContactType type;

	private String name;

	private String phone;

	private String wechat;

	private String position;

	public ContactNode() {

	}

	public ContactNode(Contact contact) {
		if (contact == null) {
			return;
		}
		this.setId(contact.getId());
		this.setName(contact.getName());
		this.setPhone(contact.getPhone());
		this.setPosition(contact.getPosition());
		this.setType(contact.getType());
		this.setWechat(contact.getWechat());
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ContactType getType() {
		return this.type;
	}

	public void setType(ContactType type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWechat() {
		return this.wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
