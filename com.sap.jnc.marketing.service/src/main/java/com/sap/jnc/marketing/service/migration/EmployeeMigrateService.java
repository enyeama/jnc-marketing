package com.sap.jnc.marketing.service.migration;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.dto.response.migration.EmployeeErrorMessageResponse;
import com.sap.jnc.marketing.persistence.criteria.migration.EmployeeAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Employee;

public interface EmployeeMigrateService {
	Page<Employee> advanceSearch(EmployeeAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);
//	void export(List<Employee> list);
	EmployeeErrorMessageResponse parseFile(String type, byte[] content);
}