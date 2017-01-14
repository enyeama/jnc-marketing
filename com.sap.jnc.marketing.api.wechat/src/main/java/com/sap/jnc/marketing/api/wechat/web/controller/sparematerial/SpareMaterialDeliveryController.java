package com.sap.jnc.marketing.api.wechat.web.controller.sparematerial;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.api.wechat.web.controller.GeneralController;
import com.sap.jnc.marketing.dto.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.response.sparematerial.SpareMaterialDeliveryPageWechatResponse;
import com.sap.jnc.marketing.dto.response.sparematerial.SpareMaterialDeliveryWechatResponse;
import com.sap.jnc.marketing.persistence.criteria.sparematerial.SpareMaterialDeliverySearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.SpareMaterialDelivery;
import com.sap.jnc.marketing.service.sparematerial.SpareMaterialDeliveryService;

/*
 * @author Kay, Du I326950
 */

@RestController
public class SpareMaterialDeliveryController extends GeneralController {
	@Autowired
	private SpareMaterialDeliveryService spareMaterialDeliveryService;
	
	@RequestMapping(value = "/deliveries/{positionId}/receipts/{id}", method = RequestMethod.PUT)
	public @ResponseBody SpareMaterialDeliveryWechatResponse confirmReceipt(@PathVariable("positionId") String positionId, @PathVariable("id") Long id) {
		return new SpareMaterialDeliveryWechatResponse(spareMaterialDeliveryService.confirmReceipt(positionId, id));
	}
	
	@RequestMapping(value = "/deliveries", method = RequestMethod.POST)
	public @ResponseBody SpareMaterialDeliveryPageWechatResponse pagedQuery(@Valid @RequestBody GeneralSearchRequest<SpareMaterialDeliverySearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<SpareMaterialDelivery> pages = this.spareMaterialDeliveryService.pagedQuery(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return null;
		}
		return new SpareMaterialDeliveryPageWechatResponse(pages, pageRequest);
	}
}
