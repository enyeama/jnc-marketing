package com.sap.jnc.marketing.api.wechat.web.controller;

import com.sap.jnc.marketing.dto.request.bonus.BonusRequest;
import com.sap.jnc.marketing.dto.request.transfer.TransferRequest;
import com.sap.jnc.marketing.infrastructure.shared.constant.ApiResult;
import com.sap.jnc.marketing.service.payment.PaymentService;
import com.sap.jnc.marketing.service.security.portal.PortalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dy on 16/6/20.
 */
@RestController
@RequestMapping("pay")
public class PaymentController {

	@Autowired
	private PaymentService payementService;

	@RequestMapping(value = "/transfer")
	public ApiResult tranfer(@AuthenticationPrincipal PortalUser portalUser, HttpServletRequest request,
		@RequestParam("code") String verifyCode) {
		TransferRequest transferRequest = new TransferRequest();
		transferRequest.setUserOpenId(portalUser.getOpenId());
		transferRequest.setClientIp(request.getRemoteAddr());
		transferRequest.setVerifyCode(verifyCode);
		transferRequest.setDescription("bonus");
		payementService.transfer(transferRequest);
		return ApiResult.SUCCESS;
	}

	@RequestMapping(value = "bonus")
	public ApiResult grantBonus(@AuthenticationPrincipal PortalUser portalUser,
								@RequestParam("code")String verifyCode, HttpServletRequest request) {
		BonusRequest bonusRequest = new BonusRequest();
		bonusRequest.setVerifyCode(verifyCode);
		bonusRequest.setOpenId(portalUser.getOpenId());
		bonusRequest.setClientIp(request.getRemoteAddr());
		Float amount = payementService.grantBonus(bonusRequest);
		return ApiResult.successBuilder().result(amount).get();
	}
}
