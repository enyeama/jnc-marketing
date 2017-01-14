/**
 * 
 */
package com.sap.jnc.marketing.service.bonus;

import com.sap.jnc.marketing.dto.request.bonus.BonusResultRequest;
import com.sap.jnc.marketing.dto.response.bonus.BonusVerificationResponse;

/**
 * 红包规则服务
 * 
 * @author I323560
 */
public interface BonusRuleService {

	/**
	 * 通过瓶盖内码和验证码获取红包验证信息（是否通过验证，红包金额）
	 * 
	 * @param capInnerCode 瓶盖内码
	 * @param verificationCode 验证码
	 * @return 红包验证信息
	 */
	public BonusVerificationResponse getBonusVerification(String capInnerCode, String verificationCode);

	/**
	 * 设置红包发送结果信息
	 * 
	 * @param bonusResultRequest 红包发送结果信息
	 */
	public void setBonusResult(BonusResultRequest bonusResultRequest);

}
