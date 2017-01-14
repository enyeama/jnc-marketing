package com.sap.jnc.marketing.dto.shared.kaorder;

import java.io.Serializable;
import java.util.List;

import com.sap.jnc.marketing.dto.shared.IndividualProductInfo;

/**
 * @author Joel.Cheng I310645
 *
 */
public class TerminalOrderItemInfo implements Serializable {

	private static final long serialVersionUID = -1025309968110789716L;

	private Long id;

	private String comment;

	private TerminalOrderInfo orderInfo;

	private List<IndividualProductInfo> individualProductInfos;

	public TerminalOrderItemInfo() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public TerminalOrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(TerminalOrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public List<IndividualProductInfo> getIndividualProductInfos() {
		return individualProductInfos;
	}

	public void setIndividualProductInfos(List<IndividualProductInfo> individualProductInfos) {
		this.individualProductInfos = individualProductInfos;
	}

}
