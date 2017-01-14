package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.model.BanquetType;

/**
 * @author I332242 Zhu Qiang
 */
public class BanquetTypeInfoResponse implements Serializable {
	
	private static final long serialVersionUID = -1969396315440568626L;

	private Long id;

	private String name;
	
	public BanquetTypeInfoResponse() {
	}

	public BanquetTypeInfoResponse(BanquetType banquetType) {
		if(banquetType == null) {
			return;
		}
		this.setId(banquetType.getId());
		this.setName(banquetType.getName());
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
}
