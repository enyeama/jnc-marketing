package com.sap.jnc.marketing.api.wechat.web.controller.sparematerial;

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
import com.sap.jnc.marketing.dto.request.sparematerial.SpareMaterialPaymentWechatRequest;
import com.sap.jnc.marketing.dto.response.sparematerial.SpareMaterialPaymentPageWechatResponse;
import com.sap.jnc.marketing.dto.response.sparematerial.SpareMaterialPaymentWechatResponse;
import com.sap.jnc.marketing.dto.response.sparematerial.SpareMaterialWechatResponse;
import com.sap.jnc.marketing.persistence.criteria.sparematerial.SpareMaterialPaymentSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.SpareMaterialPayment;
import com.sap.jnc.marketing.service.sparematerial.SpareMaterialPaymentService;

/*
 * @author I326950 Kay Du
 */

@RestController
public class SpareMaterialPaymentController {
	@Autowired
	private SpareMaterialPaymentService spareMaterialPaymentService;

	public Transformer<Product, SpareMaterialWechatResponse> spareMaterialTransformer = new Transformer<Product, SpareMaterialWechatResponse>() {

		@Override
		public SpareMaterialWechatResponse transform(Product product) {
			// TODO Auto-generated method stub
			return new SpareMaterialWechatResponse(product);
		}
	};

	@RequestMapping(value = "/consumptions/{positionId}/materials", method = RequestMethod.GET)
	public @ResponseBody Collection<SpareMaterialWechatResponse> findMaterialsByPositionId(@PathVariable("positionId") String positionId) {
		return CollectionUtils.collect(spareMaterialPaymentService.findSpareMaterialsByPositionId(positionId), spareMaterialTransformer);
	}

	@RequestMapping(value = "/consumptions/{positionId}/consumption/{materialId}", method = RequestMethod.PUT)
	public @ResponseBody SpareMaterialPaymentWechatResponse updateConsumptionQuantityByPositionIdAndMaterialId(@PathVariable("positionId") String positionId,
		@PathVariable("materialId") String materialId, @RequestBody SpareMaterialPaymentWechatRequest spareMaterialPaymentRequest) {
		return new SpareMaterialPaymentWechatResponse(spareMaterialPaymentService.updateConsumptionQuantityByPositionIdAndMaterialId(positionId, materialId, spareMaterialPaymentRequest));
	}
	
	@RequestMapping(value = "/consumptions", method = RequestMethod.POST)
	public @ResponseBody SpareMaterialPaymentPageWechatResponse pagedQuery(@Valid @RequestBody GeneralSearchRequest<SpareMaterialPaymentSearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<SpareMaterialPayment> pages = this.spareMaterialPaymentService.pagedQuery(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return null;
		}
		return new SpareMaterialPaymentPageWechatResponse(pages, pageRequest);
	}
}
