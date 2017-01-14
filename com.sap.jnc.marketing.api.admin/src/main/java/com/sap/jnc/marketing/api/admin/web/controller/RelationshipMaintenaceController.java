package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.request.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.request.mainten.DLPRelationRequest;
import com.sap.jnc.marketing.dto.respose.mainten.MaintenDealerResponse;
import com.sap.jnc.marketing.dto.respose.mainten.MaintenLeaderResponse;
import com.sap.jnc.marketing.dto.respose.mainten.MaintenProductResponse;
import com.sap.jnc.marketing.dto.respose.mainten.MaintenRelationsResponse;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.service.relationmainten.RelationMaintenService;

/**
 * @author Maggie Liu
 */
@RestController
public class RelationshipMaintenaceController extends GeneralController{
	@Autowired
	RelationMaintenService relationMaintenService;

	@RequestMapping(value = { "/maintenance/relationships/page" }, method = { RequestMethod.POST })
	public MaintenRelationsResponse findall(@Valid @RequestBody GeneralSearchRequest<?> searchRequest) {	
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<PositionView> pages = this.relationMaintenService.findAllDLPs(pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return new MaintenRelationsResponse(pageRequest);
		}
		
		return new MaintenRelationsResponse(pages, pageRequest);
	}

	@RequestMapping(value = { "/maintenance/products" }, method = { RequestMethod.GET })
	public List<MaintenProductResponse> findproductbymanager(@RequestParam String id) {
		List<Product> products = relationMaintenService.findAllProductsByManager(id);
		if (!CollectionUtils.isEmpty(products) ) {
			return products.stream().distinct().map(product -> new MaintenProductResponse(product)).collect(Collectors.toList());
		}
		return Collections.emptyList() ;
	}

	@RequestMapping(value = { "/maintenance/dealers" }, method = { RequestMethod.GET })
	public Collection<MaintenDealerResponse> finddealerbymanager(@RequestParam String id) {
		List<Dealer> dealers = relationMaintenService.findAllDealerByManager(id);
		if (!CollectionUtils.isEmpty(dealers) ) {
			return dealers.stream().distinct().map(dealer -> new MaintenDealerResponse(dealer)).collect(Collectors.toList());
		}
		return Collections.emptyList() ;
	}

	@RequestMapping(value = { "/maintenance/leaders" }, method = { RequestMethod.GET })
	public @ResponseBody Collection<MaintenLeaderResponse> findleader(@RequestParam String id) {
		List<PositionView> positionViews = relationMaintenService.findAllLeaders(id);
		if (!CollectionUtils.isEmpty(positionViews)) {
			return positionViews.stream().distinct().map(positionView -> new MaintenLeaderResponse(positionView)).collect(Collectors.toList());
		}
		return Collections.emptyList() ;
	}

	@RequestMapping(value = { "/maintenance/relationships" }, method = { RequestMethod.POST })
	public @ResponseBody boolean create(@RequestBody DLPRelationRequest dclReauest) {
		return relationMaintenService.createRelation(dclReauest.getDealerId(), dclReauest.getLeaderId(), dclReauest.getProductId());
	}
	
	@RequestMapping(value = { "/maintenance/relationships/config" }, method = { RequestMethod.POST })
	public @ResponseBody boolean delete(@RequestBody DLPRelationRequest dclReauest){
		return relationMaintenService.deleteRelation(dclReauest.getDealerId(), dclReauest.getLeaderId(), dclReauest.getProductId());	
	}
	
}
