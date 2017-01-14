package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;

/**
 * @author I332242 Zhu Qiang
 */
public class ProductInfoRequest implements Serializable {

	private static final long serialVersionUID = -6332101770780363865L;

	private String id;

	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
