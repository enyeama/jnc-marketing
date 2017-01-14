/**
 * 
 */
package com.sap.jnc.marketing.dto.response.bonus;

import java.io.Serializable;

/**
 * 红包验证信息
 * 
 * @author I323560
 *
 */
public class BonusVerificationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1455750873221491801L;

	public BonusVerificationResponse() {
		super();
	}

	/**
	 * 是否已通过验证 (以后如果需要更多的验证状态可以将该属性从boolean改成enum类型)
	 */
	private boolean isVerified;

	/**
	 * 该产品对应红包金额 --- 以“分”为单位
	 */
	private Integer bonusAmount;

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Integer getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(Integer bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	@Override
	public String toString() {
		return "BonusVerificationResponse [isVerified=" + isVerified + ", bonusAmount=" + bonusAmount + "]";
	}

}
