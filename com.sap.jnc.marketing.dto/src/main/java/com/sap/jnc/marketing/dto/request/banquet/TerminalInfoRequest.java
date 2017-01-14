package com.sap.jnc.marketing.dto.request.banquet;

import java.io.Serializable;

/**
 * @author I332242 Zhu Qiang
 */
public class TerminalInfoRequest implements Serializable {
	
	private static final long serialVersionUID = 2292823387795525726L;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
