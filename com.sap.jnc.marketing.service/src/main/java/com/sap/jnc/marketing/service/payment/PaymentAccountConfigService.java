package com.sap.jnc.marketing.service.payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.dto.request.payment.PaymentAccountConfigRequest;
import com.sap.jnc.marketing.dto.response.payment.PaymentAccountValidityResponse;
import com.sap.jnc.marketing.persistence.criteria.payment.PaymentAccountConfigAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.PaymentAccountConfig;

public interface PaymentAccountConfigService {

	/**
	 * 条件分页查询支付账户配置信息
	 * 
	 * @param keywords
	 * @param pageRequest
	 * @return
	 */
	Page<PaymentAccountConfig> advanceSearch(PaymentAccountConfigAdvanceSearchKeywordNode keywords, PageRequest pageRequest);

	/**
	 * 新增支付账户配置信息
	 * 
	 * @param request
	 * @return
	 */
	PaymentAccountValidityResponse addPaymentAccountConfig(PaymentAccountConfigRequest request);

	/**
	 * 验证生效日期的合法性
	 * 
	 * @param request
	 * @return
	 */
	PaymentAccountValidityResponse isValidFromLegal(PaymentAccountConfigRequest request);

}
