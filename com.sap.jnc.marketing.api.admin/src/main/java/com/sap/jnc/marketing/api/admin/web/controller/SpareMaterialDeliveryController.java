package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.response.sparematerial.SpareMaterialDeliveryPageResponse;
import com.sap.jnc.marketing.dto.response.sparematerial.SpareMaterialPositionResponse;
import com.sap.jnc.marketing.dto.response.sparematerial.SpareMaterialDeliveryResponse;
import com.sap.jnc.marketing.dto.response.sparematerial.SpareMaterialWechatResponse;
import com.sap.jnc.marketing.persistence.criteria.sparematerial.SpareMaterialDeliverySearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.SpareMaterialDelivery;
import com.sap.jnc.marketing.service.sparematerial.SpareMaterialDeliveryService;

/*
 * @author Kay, Du I326950
 */

@RestController
public class SpareMaterialDeliveryController extends GeneralController  {
	@Autowired
	private SpareMaterialDeliveryService spareMaterialDeliveryService;
	
	public Transformer<Position, SpareMaterialPositionResponse> positionTransformer = new Transformer<Position, SpareMaterialPositionResponse>() {

		@Override
		public SpareMaterialPositionResponse transform(Position position) {
			return new SpareMaterialPositionResponse(position);
		}
	};
	
	@RequestMapping(value = "/deliveries", method = RequestMethod.POST)
	public @ResponseBody SpareMaterialDeliveryPageResponse pageQuery(@Valid @RequestBody GeneralSearchRequest<SpareMaterialDeliverySearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<SpareMaterialDelivery> pages = this.spareMaterialDeliveryService.pagedQuery(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return null;
		}
		return new SpareMaterialDeliveryPageResponse(pages, pageRequest);
	}
	
	@RequestMapping(value = "/deliveries/{id}", method = RequestMethod.PUT)
	public @ResponseBody SpareMaterialDeliveryResponse cancelDelivery(@PathVariable("id") Long id) {
		return new SpareMaterialDeliveryResponse(spareMaterialDeliveryService.cancelDelivery(id));
	}
	
	@RequestMapping(value = "/deliveries/positionIds", method = RequestMethod.GET)
	public @ResponseBody Collection<SpareMaterialPositionResponse> findPositionIds() {
		return CollectionUtils.collect(spareMaterialDeliveryService.findPositionIds(), positionTransformer);
	}
}
