package com.sap.jnc.marketing.api.admin.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sap.jnc.marketing.dto.GeneralSearchRequest;
import com.sap.jnc.marketing.dto.GeneralSearchRequest.Paging;
import com.sap.jnc.marketing.dto.response.migration.EmployeeErrorMessageResponse;
import com.sap.jnc.marketing.dto.response.migration.EmployeeMigratePageResponse;
import com.sap.jnc.marketing.persistence.criteria.migration.EmployeeAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.service.migration.EmployeeMigrateService;

@RestController 
public class EmployeeMigrateController extends GeneralController{
	@Autowired
	private EmployeeMigrateService employeeMigrateService;

	@RequestMapping(value = "/employeemigrate/pages", method = RequestMethod.POST)
	public EmployeeMigratePageResponse advanceSearch(@Valid @RequestBody GeneralSearchRequest<EmployeeAdvanceSearchKeywordNode> searchRequest) {
		final PageRequest pageRequest = searchRequest.getPageRequest();
		final Page<Employee> pages = this.employeeMigrateService.advanceSearch(searchRequest.getKeywords(), pageRequest);
		if ((pages == null) || CollectionUtils.isEmpty(pages.getContent())) {
			return null;
		}
		return new EmployeeMigratePageResponse(pages, pageRequest);
	}
	@RequestMapping(value="/employeemigrate/export",method=RequestMethod.POST)
	public void export(@Valid @RequestBody GeneralSearchRequest<EmployeeAdvanceSearchKeywordNode> searchRequest) throws IOException{
		Paging paging = new Paging();
		paging.setSize(100000);
		searchRequest.setPaging(paging);
		PageRequest pageRequest = searchRequest.getPageRequest();
		Page<Employee> pages = this.employeeMigrateService.advanceSearch(searchRequest.getKeywords(), pageRequest);
	}
	@RequestMapping(value="/employeemigrate/files",method = RequestMethod.POST)
	public EmployeeErrorMessageResponse uploadFiles(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) throws IOException {
		EmployeeErrorMessageResponse message = employeeMigrateService.parseFile(type, file.getBytes());
		return message;
	}
}