package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;

/**
 * @author I332242 Zhu Qiang
 */
public class DealerInfoRequest implements Serializable {

	private static final long serialVersionUID = -2632601980000860422L;

	private Long id;

	private String name;

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
}
