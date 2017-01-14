package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.request.terminalorder.DealerLeaderSearchRequest;
import com.sap.jnc.marketing.dto.response.bonus.page.PageBean;
import com.sap.jnc.marketing.dto.response.kaorder.DeliveryNodeResponse;
import com.sap.jnc.marketing.dto.response.kaorder.KAOrderListResponsePage;
import com.sap.jnc.marketing.dto.response.kaorder.KAOrderPageResponse;
import com.sap.jnc.marketing.dto.shared.kaorder.TerminalOrderInfo;
import com.sap.jnc.marketing.persistence.criteria.kaorder.KAOrderAdvancedSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;
import com.sap.jnc.marketing.service.terminalorder.TerminalOrderService;

/**
 * @author Joel.Cheng I310645
 */
@RestController
public class KAOrderController extends GeneralController {

	@Autowired
	private TerminalOrderService terminalOrderService;

	@RequestMapping(value = "/kaorders/pages", method = RequestMethod.POST)
	public PageBean<KAOrderListResponsePage> advanceSearch(@RequestBody GeneralSearchRequest<KAOrderAdvancedSearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<TerminalOrder> pages = this.terminalOrderService.advanceKAOrderSearch(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return null;
		}
		KAOrderPageResponse kaOrderPageResponse = new KAOrderPageResponse(pages, pageRequest);

		List<KAOrderListResponsePage> items = kaOrderPageResponse.getContent();
		int total = (int) kaOrderPageResponse.getTotalElements();
		int number = pages.getNumber();
		int size = pages.getSize();

		PageBean<KAOrderListResponsePage> kaOrderResponseBean = new PageBean<KAOrderListResponsePage>(number + 1, size, total, items);
		return kaOrderResponseBean;
	}

	@RequestMapping(value = "/kaorders/citymanagers/{kaOfficeExternalId}", method = RequestMethod.GET)
	public Collection<DeliveryNodeResponse> findCityManagersByKaOfficeId(@PathVariable String kaOfficeExternalId) {
		return this.terminalOrderService.findCityManagersByKaOfficeExternalId(kaOfficeExternalId);
	}

	@RequestMapping(value = "/kaorders/maintainers/{cityManagerPositionExternalId}", method = RequestMethod.GET)
	public Collection<DeliveryNodeResponse> findMaintainersByCityManagerPositionId(@PathVariable String cityManagerPositionExternalId) {
		return this.terminalOrderService.findMaintainersByCityManagerPositionExternalId(cityManagerPositionExternalId);
	}

	@RequestMapping(value = "/kaorders/leaders", method = RequestMethod.POST)
	public Collection<DeliveryNodeResponse> findLeaders(@RequestBody DealerLeaderSearchRequest dealerLeaderRequest) {
		return this.terminalOrderService.findLeadersByCityManagerPositionExternalId(dealerLeaderRequest);
	}

	@RequestMapping(value = { "/kaorders" }, method = { RequestMethod.POST })
	public TerminalOrderInfo createTerminalOrder(@RequestBody TerminalOrderInfo terminalOrderRequest) {
		return this.terminalOrderService.createKAOrder(terminalOrderRequest);
	}

	@RequestMapping(value = { "/kaorders" }, method = { RequestMethod.PUT })
	public TerminalOrderInfo updateTerminalOrder(@RequestBody TerminalOrderInfo terminalOrderRequest) {
		return this.terminalOrderService.updateKAOrder(terminalOrderRequest);
	}

	@RequestMapping(value = { "/kaorders/{id}/cancel" }, method = { RequestMethod.POST })
	public void deleteTerminalOrder(@PathVariable Long id) {
		this.terminalOrderService.cancelKAOrder(id);
	}

	@RequestMapping(value = { "/kaorders/{id}" }, method = { RequestMethod.GET })
	public TerminalOrderInfo getTerminalOrder(@PathVariable Long id) {
		return this.terminalOrderService.getKAOrder(id);
	}

}
