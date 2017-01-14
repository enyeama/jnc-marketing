package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.request.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.request.dealer.DealerOrderRequest;
import com.sap.jnc.marketing.dto.request.logistic.QrCodeScanLogisticRequest;
import com.sap.jnc.marketing.dto.response.dealer.DealerLeaderResponse;
import com.sap.jnc.marketing.dto.response.dealer.DealerLogisticStasticsResponse;
import com.sap.jnc.marketing.dto.response.dealer.DealerOrderPageResponse;
import com.sap.jnc.marketing.dto.response.dealer.DealerOrderResponse;
import com.sap.jnc.marketing.dto.response.logistic.QrCodeScanLogisticResponse;
import com.sap.jnc.marketing.dto.response.logistic.QrCodeScanLogisticValidateResponse;
import com.sap.jnc.marketing.persistence.criteria.dealer.DealerOrdersAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;
import com.sap.jnc.marketing.service.dealer.DealerService;
import com.sap.jnc.marketing.service.logistic.LogisticService;

/**
 * @author I071053 Diouf Du
 */
@RestController
public class DealerController {

	private static Logger LOGGER = LoggerFactory.getLogger(DealerController.class);

	@Autowired
	private LogisticService logisticService;
	
	@Autowired
	private DealerService dealerService;

	@RequestMapping(value = { "/dealer/logistic" }, method = { RequestMethod.POST })
	public QrCodeScanLogisticResponse createLogistic(@RequestBody QrCodeScanLogisticRequest logisticRequest) {
		return this.logisticService.createLogisticByQrCodeScan(logisticRequest);
	}

	@RequestMapping(value = { "/dealer/logistic/validateqrcode" }, method = { RequestMethod.POST })
	public QrCodeScanLogisticValidateResponse validateLogisticByQrCodeScan(@RequestBody QrCodeScanLogisticRequest logisticRequest) {
		return this.logisticService.validateLogisticByQrCodeScan(logisticRequest);
	}

	@RequestMapping(value = { "/dealer/leaders/{dealerId}" }, method = { RequestMethod.GET })
	public List<DealerLeaderResponse> getLeaders(@PathVariable("dealerId") String dealerId) {
		return dealerService.findAllDealerLeaders(dealerId).stream().map(a -> new DealerLeaderResponse(a)).collect(Collectors.toList());
	}
	
	@RequestMapping(value = { "/dealer/{dealerId}/leader/logistic" }, method = { RequestMethod.GET })
	public Collection<DealerLogisticStasticsResponse> getDealerOutCaseHistory(@PathVariable("dealerId") String dealerId) {
		return this.logisticService.findAllDealerLogisticStatus(Long.valueOf(dealerId));
	}
	
	@RequestMapping(value = { "/dealer/orders" }, method = { RequestMethod.POST })
	public DealerOrderPageResponse getDealerOrders(@Valid @RequestBody GeneralSearchRequest<DealerOrdersAdvanceSearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<TerminalOrder> pages = this.dealerService.getOrders(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return new DealerOrderPageResponse(pageRequest);
		}
		return new DealerOrderPageResponse(pages, pageRequest);
	}
	
	@RequestMapping(value = { "/dealer/order" }, method = { RequestMethod.POST })
	public DealerOrderResponse cancelOrder(@RequestBody DealerOrderRequest request) {
		return new DealerOrderResponse(this.dealerService.cancelOrder(request));
	}
}
