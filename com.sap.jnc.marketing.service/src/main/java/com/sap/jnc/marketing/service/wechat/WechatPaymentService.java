package com.sap.jnc.marketing.service.wechat;

import com.sap.jnc.marketing.dto.shared.wechat.*;

public interface WechatPaymentService {

	/**
	 * @param transferRequest
	 * @return
	 */
	WechatTransferResponse transfer(WechatTransferRequest transferRequest);

	WechatBonusResponse grantBonus(WechatBonusRequest bonusRequest);

	WechatBonusQueryResponse queryBonus(WechatBonusQueryRequest wechatBonusQueryRequest);
}
