package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sap.jnc.marketing.dto.request.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.request.banquet.BanquetVerificationRequest;
import com.sap.jnc.marketing.dto.response.banquet.BanquetVerificationPageResponse;
import com.sap.jnc.marketing.dto.response.banquet.BanquetVerificationResponse;
import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetVerificationSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.BanquetVerification;
import com.sap.jnc.marketing.service.banquet.BanquetVerificationService;

/**
 * @author Joel.Cheng I310645
 *
 */
public class BanquetVerificationController extends GeneralController {

	private static Logger LOGGER = LoggerFactory.getLogger(BanquetVerificationController.class);
	
	@Autowired
	private BanquetVerificationService banquetVerificationService;

	@RequestMapping(value = "/banquets/verifications/pages", method = RequestMethod.POST)
	public BanquetVerificationPageResponse advanceSearch(@Valid @RequestBody GeneralSearchRequest<BanquetVerificationSearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<BanquetVerification> pages = this.banquetVerificationService.advanceSearch(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return new BanquetVerificationPageResponse(pageRequest);
		}
		return new BanquetVerificationPageResponse(pages, pageRequest);
	}
	
	@RequestMapping(value = "/banquets/verifications/application", method = RequestMethod.POST)
	public BanquetVerificationResponse generateBanquetVfyApplication(@Valid @RequestBody BanquetVerificationRequest request) {
		LOGGER.info("Generate new banquet verification application");
		if (request == null) {
			return null;
		}
		return new BanquetVerificationResponse(this.banquetVerificationService.create(request));
	}
	
	@RequestMapping(value = "/banquets/verifications/{id}", method = RequestMethod.POST)
	public BanquetVerificationResponse updateBanquetVfyApplication(@PathVariable Long id, @Valid @RequestBody BanquetVerificationRequest request) {
		LOGGER.info("Update banquet verification application");
		if (request == null || id == null) {
			return null;
		}
		return new BanquetVerificationResponse(this.banquetVerificationService.update(request));
	}
	
	@RequestMapping(value = "/banquets/verifications", method = RequestMethod.POST)
	public void batchSumbitBanquetVerification(@Valid @RequestBody List<Long> ids) {
		LOGGER.info("Batch submit banquet verification");
		if (ids == null) {
			return;
		}
		this.banquetVerificationService.batchSumbitBanquetVerification(ids);
	}
	
	@RequestMapping(value = "/banquets/verifications/{id}", method = RequestMethod.GET)
	public BanquetVerificationResponse findOneById(@PathVariable Long id) {
		LOGGER.info("Find one banquet verification application by id");
		if (id == null) {
			return null;
		}
		return new BanquetVerificationResponse(this.banquetVerificationService.findOneById(id));
	}

	@RequestMapping(value = "/banquets/verifications/signatures", method = RequestMethod.POST)
	public void batchSignBanquetVerification(@Valid @RequestBody List<Long> ids) {
		LOGGER.info("Batch sign submit banquet verification application");
		if (ids == null) {
			return;
		}
		this.banquetVerificationService.batchSignBanquetVerification(ids);
	}

}
