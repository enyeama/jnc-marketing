package com.sap.jnc.marketing.dto.shared.banquet.banquetrebate;

import com.sap.jnc.marketing.persistence.model.Amount;

import java.io.Serializable;

import com.sap.jnc.marketing.persistence.enumeration.RebateTargetType;
import com.sap.jnc.marketing.persistence.model.BanquetRebateConfig;

/**
 * @author Joel.Cheng I310645
 *
 */
public class BanquetRebateConfigInfo implements Serializable {

	private static final long serialVersionUID = -4809078964722239860L;

	private Amount amount;

	private RebateTargetType rebateTargetType;

	public BanquetRebateConfigInfo() {

	}

	public BanquetRebateConfigInfo(BanquetRebateConfig banquetRebateConfig) {
		this.amount = banquetRebateConfig.getAmount();
		this.rebateTargetType = banquetRebateConfig.getRebateTargetType();
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public RebateTargetType getRebateTargetType() {
		return rebateTargetType;
	}

	public void setRebateTargetType(RebateTargetType rebateTargetType) {
		this.rebateTargetType = rebateTargetType;
	}

}
