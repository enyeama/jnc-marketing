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

import com.sap.jnc.marketing.dto.request.citymanager.config.SalesmanTerminalRelationRequest;
import com.sap.jnc.marketing.dto.request.wechat.salesman.SalesMyOrdersRequest;
import com.sap.jnc.marketing.dto.request.wechat.salesman.SalesOrderRequest;
import com.sap.jnc.marketing.dto.request.wechat.salesman.SalesTerminalsRequest;
import com.sap.jnc.marketing.dto.response.SalesmanTerminalRelationResponse;
import com.sap.jnc.marketing.dto.response.wechat.salesman.SalesCategoryResponse;
import com.sap.jnc.marketing.dto.response.wechat.salesman.SalesMyOrdersResponse;
import com.sap.jnc.marketing.dto.response.wechat.salesman.SalesOrderResponse;
import com.sap.jnc.marketing.dto.response.wechat.salesman.SalesProductResponse;
import com.sap.jnc.marketing.dto.response.wechat.salesman.SalesTeminalsResponse;
import com.sap.jnc.marketing.service.citymanager.config.RelationSettingService;
import com.sap.jnc.marketing.service.wechat.WechatSalesmanService;

/**
 * @author C5245167 Xiao Qi
 */
@RestController
public class SalesmanController extends GeneralController {

	@Autowired
	private WechatSalesmanService service;

	@Autowired
	private RelationSettingService relationSettingService;

	@RequestMapping(path = "/salesman/terminal/config", method = { RequestMethod.POST })
	public void createRelation(@RequestBody SalesmanTerminalRelationRequest relationRequest) {
		this.relationSettingService.createSalesmanTerminalRelation(relationRequest);
	}

	@RequestMapping(path = "/salesman/terminal/config", method = { RequestMethod.PUT })
	public void updateRelation(@RequestBody SalesmanTerminalRelationRequest relationRequest) {
		this.relationSettingService.updateSalesmanTerminalRelation(relationRequest);
	}

	@RequestMapping(path = "/salesman/terminal/config", method = { RequestMethod.DELETE })
	public void deleteRelation(@RequestBody SalesmanTerminalRelationRequest relationRequest) {
		this.relationSettingService.deleteSalesmanTerminalRelation(relationRequest);
	}

	@RequestMapping(path = "/salesman/terminal/config", method = { RequestMethod.GET })
	public Collection<SalesmanTerminalRelationResponse> listRelation() {
		return this.relationSettingService.findAllPositionTerminalRelations();
	}

	@RequestMapping(path = "/salesman/orders", method = { RequestMethod.POST })
	public SalesOrderResponse createOrder(SalesOrderRequest request) {
		return new SalesOrderResponse(service.createOrder(request));
	}

	@RequestMapping(path = "/salesman/terminals", method = { RequestMethod.GET })
	public List<SalesTeminalsResponse> getTerminals(SalesTerminalsRequest request) {
		return service.getTerminals(request).stream().map(a -> new SalesTeminalsResponse(a)).collect(Collectors.toList());
	}

	@RequestMapping(path = "/salesman/orders", method = { RequestMethod.GET })
	public List<SalesMyOrdersResponse> getOrders(SalesMyOrdersRequest request) {
		return service.getOrders(request.getTerminalId(), request.getSalesmanId(), request.getStatus()).stream().map(a -> new SalesMyOrdersResponse(
			a)).collect(Collectors.toList());
	}

	@RequestMapping(path = "/salesman/orders/{id}", method = { RequestMethod.GET })
	public SalesOrderResponse viewOrder(@PathVariable String id) {
		return new SalesOrderResponse(service.viewOrder(id));
	}
	
	@RequestMapping(path = "/salesman/orders/{id}", method = { RequestMethod.POST})
	public SalesOrderResponse cancelOrder(@PathVariable String id) {
		return new SalesOrderResponse(service.cancelOrder(id));
	}

	@RequestMapping(path = "/salesman/category/{salesCategoryid}", method = { RequestMethod.GET })
	public List<SalesCategoryResponse> getCategory(@PathVariable String salesCategoryid) {
		return service.getCategory(salesCategoryid).stream().map(a -> new SalesCategoryResponse(a)).collect(Collectors.toList());
	}

	@RequestMapping(path = "/salesman/products/dmscategoryid/{dmscategoryid}/chanelid/{chanelid}", method = { RequestMethod.GET })
	public List<SalesProductResponse> getProducts(@PathVariable("dmscategoryid") String dmsCategoryId,
		@PathVariable("chanelid") String channelId) {
		return service.getProductsByCategoryId(dmsCategoryId, channelId).stream().map(a -> new SalesProductResponse(a)).collect(Collectors
			.toList());
	}
}
