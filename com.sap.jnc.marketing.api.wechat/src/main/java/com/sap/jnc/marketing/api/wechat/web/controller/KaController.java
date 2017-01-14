package com.sap.jnc.marketing.api.wechat.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.sap.jnc.marketing.dto.request.terminal.TerminalRequest;
import com.sap.jnc.marketing.dto.response.terminal.TerminalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sap.jnc.marketing.dto.request.wechat.ka.KaOrdersRequest;
import com.sap.jnc.marketing.dto.request.wechat.ka.KaTerminalsRequest;
import com.sap.jnc.marketing.dto.request.logistic.QrCodeScanLogisticRequest;
import com.sap.jnc.marketing.dto.request.wechat.ka.KaOrderRequest;
import com.sap.jnc.marketing.dto.response.logistic.QrCodeScanLogisticResponse;
import com.sap.jnc.marketing.dto.response.logistic.QrCodeScanLogisticValidateResponse;
import com.sap.jnc.marketing.dto.response.wechat.ka.KaOrdersResponse;
import com.sap.jnc.marketing.dto.response.wechat.ka.KaTerminalsResponse;
import com.sap.jnc.marketing.service.logistic.LogisticService;
import com.sap.jnc.marketing.service.wechat.WechatKaService;

/**
 * @author C5245167 Xiao Qi
 */
@RestController
@RequestMapping("/ka")
public class KaController extends GeneralController {

	@Autowired
	private WechatKaService wechatKaService;

	@Autowired
	private LogisticService logisticService;

	@RequestMapping(value = { "/sales/terminals" }, method = { RequestMethod.GET })
	public List<KaTerminalsResponse> getTerminals(KaTerminalsRequest request) {
		return wechatKaService.getTerminals(request).stream().map(a -> new KaTerminalsResponse(a)).collect(Collectors.toList());
	}

	@RequestMapping(value = { "/sales/orders" }, method = { RequestMethod.GET })
	public List<KaOrdersResponse> getOrders(KaOrdersRequest request) {
		return wechatKaService.getOrders(request).stream().map(a -> new KaOrdersResponse(a)).collect(Collectors.toList());
	}

	@RequestMapping(value = { "/sales/order" }, method = { RequestMethod.GET })
	public KaOrdersResponse getOrder(KaOrderRequest request) {
		return new KaOrdersResponse(wechatKaService.getOrder(request));
	}

	@RequestMapping(path = "/sales/logistic", method = { RequestMethod.POST })
	public QrCodeScanLogisticResponse createLogistic(@RequestBody QrCodeScanLogisticRequest logisticRequest) {
		return this.logisticService.createLogisticByQrCodeScan(logisticRequest);
	}

	@RequestMapping(path = "/sales/logistic/validateqrcode", method = { RequestMethod.POST })
	public QrCodeScanLogisticValidateResponse validateLogisticByQrCodeScan(@RequestBody QrCodeScanLogisticRequest logisticRequest) {
		return this.logisticService.validateLogisticByQrCodeScan(logisticRequest);
	}

	@RequestMapping(path = "/sales/save", method = { RequestMethod.POST })
	public void saveKaInfo(TerminalRequest request) {
		wechatKaService.saveKaInfo(request);
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public TerminalResponse kaInfo(@RequestParam("kaId") Long kaId) {
		return new TerminalResponse(wechatKaService.findTerminal(kaId));
	}
}
