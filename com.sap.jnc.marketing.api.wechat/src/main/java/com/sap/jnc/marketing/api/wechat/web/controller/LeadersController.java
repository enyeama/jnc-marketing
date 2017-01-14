package com.sap.jnc.marketing.api.wechat.web.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.request.logistic.QrCodeScanLogisticRequest;
import com.sap.jnc.marketing.dto.request.wechat.leaders.LeaderOrdersRequest;
import com.sap.jnc.marketing.dto.response.logistic.QrCodeScanLogisticResponse;
import com.sap.jnc.marketing.dto.response.logistic.QrCodeScanLogisticValidateResponse;
import com.sap.jnc.marketing.dto.response.terminal.TerminalResponse;
import com.sap.jnc.marketing.dto.response.wechat.leaders.LeaderOrdersResponse;
import com.sap.jnc.marketing.service.logistic.LogisticService;
import com.sap.jnc.marketing.service.position.PositionService;
import com.sap.jnc.marketing.service.wechat.WechatLeadersService;

/**
 * @author C5245167 Xiao Qi
 */
@RestController
public class LeadersController extends GeneralController {

	@Autowired
	private WechatLeadersService service;

	@Autowired
	LogisticService logisticService;

	@Autowired
	private PositionService postionService;
	
	@RequestMapping(path = "/leader/orders", method = { RequestMethod.GET })
	public List<LeaderOrdersResponse> getOrders(LeaderOrdersRequest request) {
		return service.getOrders(request).stream().map(a -> new LeaderOrdersResponse(a)).collect(Collectors.toList());
	}

	@RequestMapping(path = "/leader/orders/detail", method = { RequestMethod.GET })
	public LeaderOrdersResponse getOrder(LeaderOrdersRequest request) {
		return new LeaderOrdersResponse(service.getOrderDetail(request));
	}

	@RequestMapping(path = "/leader/logistic", method = { RequestMethod.POST })
	public QrCodeScanLogisticResponse createLogistic(@RequestBody QrCodeScanLogisticRequest logisticRequest) {
		return this.logisticService.createLogisticByQrCodeScan(logisticRequest);
	}

	@RequestMapping(path = "/leader/logistic/validateqrcode", method = { RequestMethod.POST })
	public QrCodeScanLogisticValidateResponse validateLogisticByQrCodeScan(@RequestBody QrCodeScanLogisticRequest logisticRequest) {
		return this.logisticService.validateLogisticByQrCodeScan(logisticRequest);
	}

	@RequestMapping(path = "/leader/terminals/{employeeId}", method = { RequestMethod.GET })
	public Collection<TerminalResponse> listTerminalsByLeaderEmployeeId(@PathVariable("employeeId") long employeeId) {
		return this.postionService.findAllTerminalsByLeaderEmployeeId(employeeId);
	}
}
