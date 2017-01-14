package com.sap.jnc.marketing.dto.shared;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.IntegratingMarketing;

/**
 * @author Joel.Cheng I310645
 *
 */
public class IntegratingMarketingInfo implements Serializable {

	private static final long serialVersionUID = 8807095463240998981L;

	private Long id;

	private String name;

	private String phone;

	private String mobile;

	public IntegratingMarketingInfo() {
		// TODO Auto-generated constructor stub
	}

	public IntegratingMarketingInfo(IntegratingMarketing integratingMarketing) {
		this.id = integratingMarketing.getId();
		this.name = integratingMarketing.getName();
		this.phone = integratingMarketing.getPhone();
		this.mobile = integratingMarketing.getMobile();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
