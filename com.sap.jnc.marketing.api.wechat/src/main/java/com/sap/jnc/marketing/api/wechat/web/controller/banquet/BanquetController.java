package com.sap.jnc.marketing.api.wechat.web.controller.banquet;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.api.wechat.web.controller.GeneralController;
import com.sap.jnc.marketing.dto.request.banquet.BanquetMergeRequest;
import com.sap.jnc.marketing.dto.request.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditTaskGenerateRequest;
import com.sap.jnc.marketing.dto.request.banquet.BanquetApproveRequest;
import com.sap.jnc.marketing.dto.response.banquet.BanquetChannelRelProduct;
import com.sap.jnc.marketing.dto.response.banquet.BanquetEnumInfoResponse;
import com.sap.jnc.marketing.dto.response.banquet.BanquetInfoResponse;
import com.sap.jnc.marketing.dto.response.banquet.BanquetPageResponse;
import com.sap.jnc.marketing.dto.response.banquet.BanquetRelatedInfoResponse;
import com.sap.jnc.marketing.dto.response.banquet.EmployeeInfoResponse;
import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetSearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.AuditType;
import com.sap.jnc.marketing.persistence.enumeration.BanquetApplicationType;
import com.sap.jnc.marketing.persistence.model.Banquet;
import com.sap.jnc.marketing.service.audit.AuditService;
import com.sap.jnc.marketing.service.banquet.BanquetService;
import com.sap.jnc.marketing.service.exception.FieldErrorBodyAudit;
import com.sap.jnc.marketing.service.exception.RequestBodyFieldValidationAuditException;
import com.sap.jnc.marketing.service.exception.ResponseBodyDBAuditException;

/**
 * @author I332242 Zhu Qiang
 */
@RestController
public class BanquetController extends GeneralController {

	@Autowired
	private BanquetService banquetService;
	
	@Autowired
	private AuditService auditService;
	
	@RequestMapping(value = "/banquets/pages", method = RequestMethod.POST)
	public BanquetPageResponse advanceSearch(@Valid @RequestBody GeneralSearchRequest<BanquetSearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<Banquet> pages = this.banquetService.advanceSearch(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return new BanquetPageResponse(pageRequest);
		}
		return new BanquetPageResponse(pages, pageRequest);
	}
	
	@RequestMapping(path = "/banquets/{id}", method = { RequestMethod.GET })
	public BanquetInfoResponse findOneById(@PathVariable Long id) {
		return new BanquetInfoResponse(this.banquetService.findOne(id));
	}
	
	@RequestMapping(path = "/banquets/banquet", method = { RequestMethod.POST })
	public BanquetInfoResponse create(@RequestBody BanquetMergeRequest request) {
		return new BanquetInfoResponse(this.banquetService.create(request));
	}
	
	@RequestMapping(path = "/banquets/banquet/{id}", method = { RequestMethod.POST })
	public void update(@PathVariable Long id, @RequestBody BanquetMergeRequest request) {
		this.banquetService.update(id, request);
	}
	
	@RequestMapping(path = "/banquets/{id}", method = { RequestMethod.DELETE })
	public void delete(@PathVariable Long id, BanquetMergeRequest request) {
		this.banquetService.delete(id);
	}
	
	@RequestMapping(path = "/banquets", method = { RequestMethod.POST })
	public void batchUpdate(@RequestBody BanquetApproveRequest banquetApproveRequest) {
		List<Long> banquetAuditIds = this.banquetService.batchUpdate(banquetApproveRequest);
		if (CollectionUtils.isNotEmpty(banquetAuditIds)) {
			for (Long id : banquetAuditIds) {
				AuditTaskGenerateRequest auditRequest = new AuditTaskGenerateRequest();
				auditRequest.setType(AuditType.BANQUET);
				auditRequest.setTargetId(id);
				this.auditService.generateAuditTask(auditRequest);
			}
		}
	}
	
	@RequestMapping(path = "/banquets/relatedinfo/{empId}", method = { RequestMethod.GET })
	public BanquetRelatedInfoResponse findBanquetRelatedInfo(@PathVariable Long empId) {
		return this.banquetService.findBanquetRelatedInfo(empId);
	}
	
	@RequestMapping(path = "/banquets/type/{type}", method = { RequestMethod.GET })
	public List<BanquetEnumInfoResponse> findBanquetEnumType(@PathVariable String type) {
		return this.banquetService.findBanquetEnumType(type);
	}
	
	@RequestMapping(path = "/handler/{empId}", method = { RequestMethod.GET })
	public List<EmployeeInfoResponse> findHandlerByCreatorId(@PathVariable Long empId) {
		return this.banquetService.findHandlerByCreatorId(empId);
	}
	
	@RequestMapping(path = "/handler/assignment", method = { RequestMethod.POST })
	public BanquetInfoResponse assignHandler(@RequestBody BanquetMergeRequest request) {
		return new BanquetInfoResponse(this.banquetService.assignHandler(request));
	}
	
	@RequestMapping(path = "/banquets/channel", method = { RequestMethod.POST })
	public BanquetChannelRelProduct findBanquetChannel (@RequestBody BanquetApplicationType banquetApplicationType) {
		return this.banquetService.findBanquetChannel(banquetApplicationType);
	}
	
	@RequestMapping(path = "/banquets/office", method = { RequestMethod.GET })
	public List<String> findAllOffice () {
		return this.banquetService.findAllOffice();
	}
	
	@RequestMapping(path = "/banquets/cancellation/{id}", method = { RequestMethod.GET })
	public BanquetInfoResponse cancelBanquet(@PathVariable Long id) {
		return new BanquetInfoResponse(this.banquetService.cancelBanquet(id));
	}
	
	/*
	 * This method will handle all the exception of field validation error include NotBland error, format error, instance consistency check error.
	 */
	@ExceptionHandler(RequestBodyFieldValidationAuditException.class)
	public ResponseEntity<FieldErrorBodyAudit> rulesForCustomerNotFound(RequestBodyFieldValidationAuditException e) {
		return new ResponseEntity<FieldErrorBodyAudit>(e.getFieldErorrBody(), HttpStatus.FORBIDDEN);
	}

	/*
	 * This method will handle all the exception of DB operation error include unkown error.
	 */
	@ExceptionHandler(ResponseBodyDBAuditException.class)
	public ResponseEntity<FieldErrorBodyAudit> dbConnectionErrorHandler(ResponseBodyDBAuditException e) {
		return new ResponseEntity<FieldErrorBodyAudit>(e.getFieldErorrBody(), HttpStatus.FORBIDDEN);
	}
}
