package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.request.bonus.BonusDispatcherRequest;
import com.sap.jnc.marketing.dto.response.bonus.BonusConfigResponse;
import com.sap.jnc.marketing.dto.response.bonus.BonusValidityResponse;
import com.sap.jnc.marketing.dto.response.bonus.ProductCategoryResponse;
import com.sap.jnc.marketing.persistence.criteria.dealer.BonusConfigAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.BonusDispatchConfig;
import com.sap.jnc.marketing.service.bonus.BonusConfigureService;

/**
 * 红包金额配置Controller
 * 
 * @author I327119
 */
@RestController
public class BonusConfigureController extends GeneralController {

	@Autowired
	private BonusConfigureService bonusConfigureService;

	/**
	 * 产品类型列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/bonus/config/product/categories", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductCategoryResponse> findProductCategories() {
		List<ProductCategoryResponse> productCategoryResponses = bonusConfigureService.listProductCategories();
		return productCategoryResponses;
	}

	/**
	 * 红包配置页面分页条件查询
	 * 
	 * @param searchRequest
	 * @return
	 */
	@RequestMapping(value = "/bonus/config/pages", method = RequestMethod.POST)
	@ResponseBody
	public BonusConfigResponse advanceSearch(
			@RequestBody GeneralSearchRequest<BonusConfigAdvanceSearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<BonusDispatchConfig> pages = this.bonusConfigureService.advanceSearch(searchRequest.getKeywords(),
				pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return null;
		}
		BonusConfigResponse bcResponse = new BonusConfigResponse(pages, pageRequest);

		return bcResponse;
	}

	/**
	 * 保存新建红包配置
	 * 
	 * @param bonusDispatcherRequest
	 * @return
	 */
	@RequestMapping(value = "/bonus/config", method = RequestMethod.POST)
	@ResponseBody
	public BonusValidityResponse addBonusDispatcher(@RequestBody BonusDispatcherRequest request) {
		BonusValidityResponse validityResponse = bonusConfigureService.addBonusDispatcher(request);
		return validityResponse;
	}

}
