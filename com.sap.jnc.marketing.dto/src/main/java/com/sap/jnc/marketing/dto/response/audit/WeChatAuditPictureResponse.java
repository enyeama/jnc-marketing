package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.AuditPictureType;
import com.sap.jnc.marketing.persistence.model.AuditPicture;

public class WeChatAuditPictureResponse implements Serializable {

	private static final long serialVersionUID = 2306473281007148064L;

	private String url;
	private AuditPictureType type;
	private String address;
	private String comment;
	private String processor;

	public WeChatAuditPictureResponse(AuditPicture auditPicture) {
		if (auditPicture == null) {
			return;
		}
		this.setAddress(auditPicture.getAddress());
		this.setType(auditPicture.getType());
		this.setAddress(auditPicture.getAddress());
		this.setComment(auditPicture.getComment());
		this.setUrl(auditPicture.getUrl());
		if (null != auditPicture.getProcessor()) {
			this.setProcessor(auditPicture.getProcessor().getExternalId());
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public AuditPictureType getType() {
		return type;
	}

	public void setType(AuditPictureType type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

}
