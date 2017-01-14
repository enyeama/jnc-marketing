package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.sap.jnc.marketing.dto.request.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditAssignRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditOfficeAssignRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditOfficeUnassignRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditResultChangeRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditStatusChangeRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditTaskGenerateRequest;
import com.sap.jnc.marketing.dto.request.audit.AuditTaskRequest;
import com.sap.jnc.marketing.dto.response.audit.AuditDetailResponse;
import com.sap.jnc.marketing.dto.response.audit.AuditorAllResponse;
import com.sap.jnc.marketing.dto.response.audit.AuditorResponse;
import com.sap.jnc.marketing.dto.response.audit.OfficesAllResponse;
import com.sap.jnc.marketing.dto.response.audit.PageAuditBanquetTaskResponse;
import com.sap.jnc.marketing.dto.response.audit.PageAuditExhibitionTaskResponse;
import com.sap.jnc.marketing.dto.response.audit.PageAuditPromotionTaskResponse;
import com.sap.jnc.marketing.dto.response.audit.PageAuditTerminalTaskResponse;
import com.sap.jnc.marketing.dto.response.audit.PageAuditorAssignmentResponse;
import com.sap.jnc.marketing.dto.response.audit.PageAuditorUnassignmentResponse;
import com.sap.jnc.marketing.dto.response.audit.PositionsResponse;
import com.sap.jnc.marketing.dto.response.audit.RegionResponse;
import com.sap.jnc.marketing.persistence.criteria.audit.AuditTaskSearchCriteria;
import com.sap.jnc.marketing.persistence.criteria.audit.AuditorOfficeAssignmentSeatchCriteria;
import com.sap.jnc.marketing.persistence.model.AuditBanquet;
import com.sap.jnc.marketing.persistence.model.AuditExhibition;
import com.sap.jnc.marketing.persistence.model.AuditPromotion;
import com.sap.jnc.marketing.persistence.model.AuditTerminal;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment;
import com.sap.jnc.marketing.service.audit.AuditService;
import com.sap.jnc.marketing.service.exception.FieldErrorBodyAudit;
import com.sap.jnc.marketing.service.exception.RequestBodyFieldValidationAuditException;
import com.sap.jnc.marketing.service.exception.ResponseBodyDBAuditException;

/**
 * @author C5231393 Xu Xiaolei
 */
@RestController
public class AuditController extends GeneralController {

	private static Logger LOGGER = LoggerFactory.getLogger(AuditController.class);

	@Autowired
	private AuditService auditService;

	@RequestMapping(value = "/audits", method = RequestMethod.POST)
	public void generateAuditTask(@RequestBody AuditTaskGenerateRequest request) {
		LOGGER.info("Create audit task");
		if (request == null) {
			return;
		}
		this.auditService.generateAuditTask(request);
	}

	@RequestMapping(value = "/audits/terminal/tasks", method = RequestMethod.POST)
	public PageAuditTerminalTaskResponse searchTerminalAudits(@RequestBody GeneralSearchRequest<AuditTaskSearchCriteria> request) {
		LOGGER.info("Find audit terminal tasks");
		if (request == null || StringUtils.isEmpty(request.getKeywords().getQueryType())) {
			return null;
		}
		final PageRequest pageRequest = request.getPageRequest();
		Page<AuditTerminal> pages = this.auditService.searchTerminalAudits(request.getKeywords(), request.getPageRequest());
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return new PageAuditTerminalTaskResponse(pageRequest);
		}
		return new PageAuditTerminalTaskResponse(pages, pageRequest);
	}

	@RequestMapping(value = "/audits/banquet/tasks", method = RequestMethod.POST)
	public PageAuditBanquetTaskResponse searchBanquetAudits(@RequestBody GeneralSearchRequest<AuditTaskSearchCriteria> request) {
		LOGGER.info("Find audit banquet tasks");
		if (request == null || StringUtils.isEmpty(request.getKeywords().getQueryType())) {
			return null;
		}
		final PageRequest pageRequest = request.getPageRequest();
		Page<AuditBanquet> pages = this.auditService.searchBanquetAudits(request.getKeywords(), request.getPageRequest());
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return new PageAuditBanquetTaskResponse(pageRequest);
		}
		return new PageAuditBanquetTaskResponse(pages, pageRequest);
	}

	@RequestMapping(value = "/audits/exhibition/tasks", method = RequestMethod.POST)
	public PageAuditExhibitionTaskResponse searchExhibitionAudits(@RequestBody GeneralSearchRequest<AuditTaskSearchCriteria> request) {
		LOGGER.info("Find audit exhibition tasks");
		if (request == null || StringUtils.isEmpty(request.getKeywords().getQueryType())) {
			return null;
		}
		final PageRequest pageRequest = request.getPageRequest();
		Page<AuditExhibition> pages = this.auditService.searchExhibitionAudits(request.getKeywords(), request.getPageRequest());
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return new PageAuditExhibitionTaskResponse(pageRequest);
		}
		return new PageAuditExhibitionTaskResponse(pages, pageRequest);
	}

	@RequestMapping(value = "/audits/promotion/tasks", method = RequestMethod.POST)
	public PageAuditPromotionTaskResponse searchPromotionAudits(@RequestBody GeneralSearchRequest<AuditTaskSearchCriteria> request) {
		LOGGER.info("Find audit promotion tasks");
		if (request == null || StringUtils.isEmpty(request.getKeywords().getQueryType())) {
			return null;
		}
		final PageRequest pageRequest = request.getPageRequest();
		Page<AuditPromotion> pages = this.auditService.searchPromotionAudits(request.getKeywords(), request.getPageRequest());
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return new PageAuditPromotionTaskResponse(pageRequest);
		}
		return new PageAuditPromotionTaskResponse(pages, pageRequest);
	}

	@RequestMapping(value = "/audits/assignments", method = RequestMethod.POST)
	public void assignAuditTasks(@RequestBody AuditAssignRequest request) {
		LOGGER.info("Assign audit tasks");
		if (request == null) {
			return;
		}
		this.auditService.assignAuditTasks(request);
	}

	@RequestMapping(value = "/audits/{id}/details", method = RequestMethod.GET)
	public AuditDetailResponse findOneWithAllDetail(@PathVariable Long id) {
		LOGGER.info("Find audit task details");
		if (id == null) {
			return null;
		}
		return this.auditService.findOneWithAllDetail(id);
	}

	@RequestMapping(value = "/audits/audit", method = RequestMethod.POST)
	public void auditTask(@RequestBody AuditTaskRequest request) {
		LOGGER.info("Audit task");
		if (CollectionUtils.isEmpty(request.getAudits())) {
			return;
		}
		this.auditService.auditTask(request);
	}

	@RequestMapping(value = "/audits/region/province", method = RequestMethod.GET)
	public List<RegionResponse> findProvinceRegions() {
		LOGGER.info("Find province regions");
		List<RegionResponse> result = this.auditService.findProvinceRegions().stream().map(region -> new RegionResponse(region)).collect(Collectors
			.toList());
		return result;
	}

	@RequestMapping(value = "/audits/region/city/{id}", method = RequestMethod.GET)
	public List<RegionResponse> findCityRegions(@PathVariable String id) {
		LOGGER.info("Find city regions");
		List<RegionResponse> result = this.auditService.findCityRegions(id).stream().map(region -> new RegionResponse(region)).collect(Collectors
			.toList());
		return result;
	}

	@RequestMapping(value = "/audits/region/county/{provinceid}/{cityid}", method = RequestMethod.GET)
	public List<RegionResponse> findCountyRegions(@PathVariable String provinceid, @PathVariable String cityid) {
		LOGGER.info("Find city regions");
		List<RegionResponse> result = this.auditService.findCountyRegions(provinceid, cityid).stream().map(region -> new RegionResponse(region))
			.collect(Collectors.toList());
		return result;
	}

	@RequestMapping(value = "/audits/auditors/{id}", method = RequestMethod.GET)
	public List<AuditorResponse> findAuditors(@PathVariable Long id) {
		LOGGER.info("Find auditors");
		List<AuditorResponse> result = this.auditService.findAuditors(id).stream().map(employeeview -> new AuditorResponse(employeeview)).collect(
			Collectors.toList());
		return result;
	}

	@RequestMapping(value = "/audits/positions/{id}", method = RequestMethod.GET)
	public List<PositionsResponse> findPositions(@PathVariable Long id) {
		List<PositionsResponse> result = this.auditService.findPositions(id).stream().map(positionview -> new PositionsResponse(positionview))
			.collect(Collectors.toList());
		return result;
	}

	@RequestMapping(value = "/audits/positions", method = RequestMethod.GET)
	public List<PositionsResponse> findPositions() {
		LOGGER.info("Find positions");
		List<PositionsResponse> result = this.auditService.findPositions(null).stream().map(positionview -> new PositionsResponse(positionview))
			.collect(Collectors.toList());
		return result;
	}

	@RequestMapping(value = "/audits/auditstatus", method = RequestMethod.POST)
	public void changeStatus(@RequestBody AuditStatusChangeRequest request) {
		LOGGER.info("Change audit status");
		if (request.getId() == null) {
			return;
		}
		this.auditService.changeStatus(request);
	}

	@RequestMapping(value = "/audits/auditresult", method = RequestMethod.POST)
	public void changeResult(@RequestBody AuditResultChangeRequest request) {
		LOGGER.info("Change audit result");
		if (request.getId() == null) {
			return;
		}
		this.auditService.changeResult(request);
	}

	@RequestMapping(value = "/audits/auditor/office/auditors", method = RequestMethod.GET)
	public List<AuditorAllResponse> findAllAuditors() {
		LOGGER.info("Find all auditors");
		List<AuditorAllResponse> result = this.auditService.findAllAuditors().stream().map(emp -> new AuditorAllResponse(emp)).collect(Collectors
			.toList());
		return result;
	}

	@RequestMapping(value = "/audits/auditor/office/offices", method = RequestMethod.GET)
	public List<OfficesAllResponse> findAllOffices() {
		LOGGER.info("Find all offices");
		List<OfficesAllResponse> result = this.auditService.findAllOffices().stream().map(dep -> new OfficesAllResponse(dep)).collect(Collectors
			.toList());
		return result;
	}

	@RequestMapping(value = "/audits/auditor/office/unassignments", method = RequestMethod.POST)
	public PageAuditorUnassignmentResponse searchAuditorOfficeUnassignments(
		@RequestBody GeneralSearchRequest<AuditorOfficeAssignmentSeatchCriteria> request) {
		LOGGER.info("Search auditor office unassignments");
		if (request == null) {
			return null;
		}
		final PageRequest pageRequest = request.getPageRequest();
		Page<EmployeeView> pages = this.auditService.searchAuditorOfficeUnassignments(request.getKeywords(), request.getPageRequest());
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return new PageAuditorUnassignmentResponse(pageRequest);
		}
		return new PageAuditorUnassignmentResponse(pages, pageRequest);
	}

	@RequestMapping(value = "/audits/auditor/office/assignments", method = RequestMethod.POST)
	public PageAuditorAssignmentResponse searchAuditorOfficeAssignments(
		@RequestBody GeneralSearchRequest<AuditorOfficeAssignmentSeatchCriteria> request) {
		LOGGER.info("Search auditor office assignments");
		if (request == null) {
			return null;
		}
		final PageRequest pageRequest = request.getPageRequest();
		Page<ProvinceManagerOfficeAssignment> pages = this.auditService.searchAuditorOfficeAssignments(request.getKeywords(), request
			.getPageRequest());
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return new PageAuditorAssignmentResponse(pageRequest);
		}
		return new PageAuditorAssignmentResponse(pages, pageRequest);
	}

	@RequestMapping(value = "/audits/auditor/office", method = RequestMethod.POST)
	public void assignAuditorOffice(@RequestBody AuditOfficeAssignRequest request) {
		LOGGER.info("assign auditor to office");
		if (request == null) {
			return;
		}
		this.auditService.assignAuditorOffice(request);
	}

	@RequestMapping(value = "/audits/auditor/office/end", method = RequestMethod.POST)
	public void unassignAuditorOffice(@RequestBody AuditOfficeUnassignRequest request) {
		LOGGER.info("unassign auditor to office");
		if (request == null) {
			return;
		}
		this.auditService.unassignAuditorOffice(request);
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
