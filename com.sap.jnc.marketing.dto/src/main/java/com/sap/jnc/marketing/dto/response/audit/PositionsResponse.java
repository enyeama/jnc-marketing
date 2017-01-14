package com.sap.jnc.marketing.dto.response.audit;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.sap.jnc.marketing.persistence.model.PositionView;

/**
 * @author C5231393 Xu Xiaolei
 */
public class PositionsResponse implements Serializable {

	private static final long serialVersionUID = 1267359781400241795L;

	private Long id;

	private String name;

	public PositionsResponse(PositionView positionView) {
		if (positionView == null) {
			return;
		}
		if (positionView.getId() != null) {
			this.setId(positionView.getId());
		}
		if (!StringUtils.isEmpty(positionView.getName())) {
			this.setName(positionView.getName());
		}
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
