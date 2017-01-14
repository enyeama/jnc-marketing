package com.sap.jnc.marketing.dto.request.audit;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.AuditPictureType;

/**
 * @author C5231393 Xu Xiaolei
 */
public class AuditPictureRequest implements Serializable {

	private static final long serialVersionUID = 34825453418849074L;

	private String url;

	private String address;

	private String comment;

	private Long processorId;

	private AuditPictureType type;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Long getProcessorId() {
		return processorId;
	}

	public void setProcessorId(Long processorId) {
		this.processorId = processorId;
	}

	public AuditPictureType getType() {
		return type;
	}

	public void setType(AuditPictureType type) {
		this.type = type;
	}

}
