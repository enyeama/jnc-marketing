package com.sap.jnc.marketing.api.admin.web.controller;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.request.payment.PaymentAccountConfigRequest;
import com.sap.jnc.marketing.dto.response.payment.PaymentAccountConfigResponse;
import com.sap.jnc.marketing.dto.response.payment.PaymentAccountValidityResponse;
import com.sap.jnc.marketing.persistence.criteria.payment.PaymentAccountConfigAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.PaymentAccountConfig;
import com.sap.jnc.marketing.service.payment.PaymentAccountConfigService;

/**
 * 支付账户配置
 * 
 * @author I327119
 */
@RestController
public class PaymentAccountConfigController extends GeneralController {

	@Autowired
	private PaymentAccountConfigService paymentAccountConfigService;

	/**
	 * 请求支付账户条件分页
	 * 
	 * @param searchRequest
	 * @return
	 */
	@RequestMapping(value = "/payment/amount/config/pages", method = RequestMethod.POST)
	@ResponseBody
	public PaymentAccountConfigResponse advanceSearch(@RequestBody GeneralSearchRequest<PaymentAccountConfigAdvanceSearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<PaymentAccountConfig> pages = paymentAccountConfigService.advanceSearch(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return null;
		}
		PaymentAccountConfigResponse pacResponse = new PaymentAccountConfigResponse(pages, pageRequest);

		return pacResponse;

	}

	/**
	 * 保存支付账户配置
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/payment/amount/config", method = RequestMethod.POST)
	@ResponseBody
	public PaymentAccountValidityResponse addPaymentAccountConfig(@RequestBody PaymentAccountConfigRequest request) {
		PaymentAccountValidityResponse validityResponse = paymentAccountConfigService.addPaymentAccountConfig(request);
		return validityResponse;
	}

}
