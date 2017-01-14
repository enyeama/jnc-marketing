package com.sap.jnc.marketing.api.wechat.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.request.logistic.QrCodeScanLogisticRequest;
import com.sap.jnc.marketing.dto.request.terminal.TerminalRequest;
import com.sap.jnc.marketing.dto.request.verification.UserVerificationRequest;
import com.sap.jnc.marketing.dto.request.wechat.terminal.TerminalOrdersRequest;
import com.sap.jnc.marketing.dto.response.logistic.QrCodeScanLogisticResponse;
import com.sap.jnc.marketing.dto.response.logistic.QrCodeScanLogisticValidateResponse;
import com.sap.jnc.marketing.dto.response.terminal.TerminalResponse;
import com.sap.jnc.marketing.dto.response.verification.UserVerificationResponse;
import com.sap.jnc.marketing.dto.response.wechat.terminal.TerminalOrderDetailResponse;
import com.sap.jnc.marketing.dto.response.wechat.terminal.TerminalOrdersResponse;
import com.sap.jnc.marketing.infrastructure.shared.constant.ApiResult;
import com.sap.jnc.marketing.infrastructure.shared.constant.Constants;
import com.sap.jnc.marketing.service.logistic.LogisticService;
import com.sap.jnc.marketing.service.security.portal.PortalUser;
import com.sap.jnc.marketing.service.terminal.TerminalService;
import com.sap.jnc.marketing.service.wechat.WechatTerminalService;

/**
 * Created by guokai on 16/6/17.
 */

@RestController
public class TerminalController {

	@Autowired
	TerminalService terminalService;

	@Autowired
	LogisticService logisticService;
	
	@Autowired
	WechatTerminalService wechatTerminalService;

	@RequestMapping("/terminal/create")
	public ApiResult create(TerminalRequest request) {
		request.setCreationClerk("106");
		terminalService.create(request);
		return ApiResult.SUCCESS;
	}

	@RequestMapping("/terminal/find")
	public TerminalResponse find(@RequestParam("id") Long id) {
		return new TerminalResponse(terminalService.findById(id));
	}

	@RequestMapping("/terminal/list")
	public List<TerminalResponse> list() {
		return terminalService.findAll().stream().map(a -> new TerminalResponse(a)).collect(Collectors.toList());
	}

	@RequestMapping(value = { "/terminal/logistic" }, method = { RequestMethod.POST })
	public QrCodeScanLogisticResponse createLogistic(@RequestBody QrCodeScanLogisticRequest logisticRequest) {
		return this.logisticService.createLogisticByQrCodeScan(logisticRequest);
	}

	@RequestMapping(value = { "/terminal/logistic/validateqrcode" }, method = { RequestMethod.POST })
	public QrCodeScanLogisticValidateResponse validateLogisticByQrCodeScan(@RequestBody QrCodeScanLogisticRequest logisticRequest) {
		return this.logisticService.validateLogisticByQrCodeScan(logisticRequest);
	}

	/*
	 * 子账号显示
	 */
	@RequestMapping("account")
	public List<UserVerificationResponse> account(@AuthenticationPrincipal PortalUser user) {
		// TODO 暂时占位越过登陆验证
		Long id = user == null ? Constants.TERMINAL_ID : user.getUser().getId();
		return terminalService.findAccount(id).stream().map(a -> new UserVerificationResponse(a)).collect(Collectors.toList());
	}

	/*
	 * 子账号显示
	 */
	@RequestMapping("account_detail")
	public UserVerificationResponse accountDetail(@AuthenticationPrincipal PortalUser user, Long id) {
		Long terminalId = user == null ? Constants.TERMINAL_ID : user.getUser().getId();
		return new UserVerificationResponse(terminalService.findDetail(terminalId, id));
	}


	/*
	 * 子账号添加
	 */
	@RequestMapping("account_add")
	public ApiResult accountAdd(@AuthenticationPrincipal PortalUser user, UserVerificationRequest request) {
		Long id = user == null ? Constants.TERMINAL_ID : user.getUser().getId();
		terminalService.accountAdd(id, request);
		return ApiResult.SUCCESS;
	}
	
	/*
	 * 终端获取订单
	 */
	@RequestMapping(path = "/terminal/orders", method = { RequestMethod.GET })
	public List<TerminalOrdersResponse> getOrders(TerminalOrdersRequest request) {
		return wechatTerminalService.getOrders(request).stream().map(a -> new TerminalOrdersResponse(a)).collect(Collectors.toList());
	}
	
	/*
	 * 查看订单详细
	 */
	@RequestMapping(path = "/terminal/orders/detail", method = { RequestMethod.GET })
	public TerminalOrderDetailResponse getOrder(TerminalOrdersRequest request) {
		return new TerminalOrderDetailResponse(wechatTerminalService.viewOrder(request));
	}
}
