package com.sap.jnc.marketing.service.payment;

import com.sap.jnc.marketing.dto.request.bonus.BonusRequest;
import com.sap.jnc.marketing.dto.request.transfer.TransferRequest;
import com.sap.jnc.marketing.dto.response.bonus.BonusQueryResponse;

/**
 *
 */
public interface PaymentService {

	Boolean transfer(TransferRequest transferRequest);

	Float grantBonus(BonusRequest bonusRequest);

	BonusQueryResponse query(String orderNo);
}
