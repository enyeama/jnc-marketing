/**
 * 
 */
package com.sap.jnc.marketing.dto.shared.bonus;

import java.io.Serializable;

/**
 * 红包规则条目
 * 一个条目包含一个范围内的该档次对应的数量，红包金额
 * 
 * @author I323560
 *
 */
public class BonusRuleItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4937803129525822760L;

	public BonusRuleItem() {
		super();
	}

	/**
	 * 数量
	 */
	private int quantity;

	/**
	 * 红包金额 --- 以“分”为单位
	 */
	private Integer bonusAmount;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Integer getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(Integer bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

}
