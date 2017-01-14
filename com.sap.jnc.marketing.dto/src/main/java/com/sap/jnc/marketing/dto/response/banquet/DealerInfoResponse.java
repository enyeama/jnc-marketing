package com.sap.jnc.marketing.dto.response.banquet;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.sap.jnc.marketing.persistence.model.Dealer;

/**
 * @author I332242 Zhu Qiang
 */
public class DealerInfoResponse implements Serializable {

	private static final long serialVersionUID = 4406742987723986546L;
	
	public DealerInfoResponse() {
		
	}
	
	public DealerInfoResponse(Dealer dealer) {
		if (null == dealer) {
			return;
		}
		if (null != dealer.getId()) {
			this.setId(dealer.getId());
		}
		if (StringUtils.isNotBlank(dealer.getName())) {
			this.setName(dealer.getName());
		}
	}

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
