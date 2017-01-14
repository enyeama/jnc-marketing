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
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.request.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.request.banquet.BanquetVerifyApplyRequest;
import com.sap.jnc.marketing.dto.response.banquet.BanquetVerifyApplicationResponse;
import com.sap.jnc.marketing.dto.response.banquet.BanquetVfyApplicationPageResponse;
import com.sap.jnc.marketing.dto.response.banquet.OfficeInfoResponse;
import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetVerifyApplicationSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.BanquetVerifyApplication;
import com.sap.jnc.marketing.service.banquet.BanquetVerifyApplicationService;

/**
 * @author I332242 Zhu Qiang
 */
@RestController
public class BanquetVerifyApplicationController extends GeneralController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(BanquetVerifyApplicationController.class);
	
	@Autowired
	private BanquetVerifyApplicationService banquetVerifyApplicationService;
	
	@RequestMapping(value = "/banquets/verifications/applications/pages", method = RequestMethod.POST)
	public BanquetVfyApplicationPageResponse advanceSearch(@Valid @RequestBody GeneralSearchRequest<BanquetVerifyApplicationSearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<BanquetVerifyApplication> pages = this.banquetVerifyApplicationService.advanceSearch(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return new BanquetVfyApplicationPageResponse(pageRequest);
		}
		return new BanquetVfyApplicationPageResponse(pages, pageRequest);
	}
	
	@RequestMapping(value = "/banquets/verifications/applications/offices/{id}", method = RequestMethod.GET)
	public @ResponseBody OfficeInfoResponse findOfficeByCityManagerId(@PathVariable("id") Long id) {
		LOGGER.info("Find office department by city manager id ");
		if (id == null) {
			return null;
		}
		return new OfficeInfoResponse(this.banquetVerifyApplicationService.findOfficeByCityManagerId(id));
	}
	
	@RequestMapping(value = "/banquets/verifications/applications/application", method = RequestMethod.POST)
	public BanquetVerifyApplicationResponse generateBanquetVfyApplication(@Valid @RequestBody BanquetVerifyApplyRequest request) {
		LOGGER.info("Generate new banquet verification application");
		if (request == null) {
			return null;
		}
		return new BanquetVerifyApplicationResponse(this.banquetVerifyApplicationService.create(request));
	}
	
	@RequestMapping(value = "/banquets/verifications/applications/{id}", method = RequestMethod.POST)
	public BanquetVerifyApplicationResponse updateBanquetVfyApplication(@PathVariable Long id, @Valid @RequestBody BanquetVerifyApplyRequest request) {
		LOGGER.info("Update banquet verification application");
		if (request == null || id == null) {
			return null;
		}
		return new BanquetVerifyApplicationResponse(this.banquetVerifyApplicationService.update(request));
	}
	
	@RequestMapping(value = "/banquets/verifications/applications", method = RequestMethod.POST)
	public void batchSumbitBanquetVfyApplication(@Valid @RequestBody List<Long> ids) {
		LOGGER.info("Batch submit banquet verification application");
		if (ids == null) {
			return;
		}
		this.banquetVerifyApplicationService.batchSumbitBanquetVfyApplication(ids);
	}
	
	@RequestMapping(value = "/banquets/verifications/applications/{id}", method = RequestMethod.GET)
	public BanquetVerifyApplicationResponse findOneById(@PathVariable Long id) {
		LOGGER.info("Find one banquet verification application by id");
		if (id == null) {
			return null;
		}
		return new BanquetVerifyApplicationResponse(this.banquetVerifyApplicationService.findOneById(id));
	}

	@RequestMapping(value = "/banquets/verifications/applications/signatures", method = RequestMethod.POST)
	public void batchSignBanquetVfyApplication(@Valid @RequestBody List<Long> ids) {
		LOGGER.info("Batch sign submit banquet verification application");
		if (ids == null) {
			return;
		}
		this.banquetVerifyApplicationService.batchSignBanquetVfyApplication(ids);
	}
	
	@RequestMapping(value = "/banquets/verifications/applications/boxes/{id}", method = RequestMethod.GET)
	public int findReportedByBanquetId(@PathVariable Long id) {
		LOGGER.info("Find total reported box number");
		if (id == null) {
			return 0;
		}
		return this.banquetVerifyApplicationService.findRbatedByBanquetId(id);
	}
}
