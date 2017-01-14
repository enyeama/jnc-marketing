/**
 * 
 */
package com.sap.jnc.marketing.service.department.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.response.department.DepartmentResponse;
import com.sap.jnc.marketing.persistence.model.Department;
import com.sap.jnc.marketing.persistence.repository.DepartmentRepository;
import com.sap.jnc.marketing.service.department.DepartmentService;

/**
 * @author Quansheng Liu I075496
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public List<DepartmentResponse> listKAOffices() {
		List<Department> departmentList = this.departmentRepository.findAllOfficeByName();
		if (CollectionUtils.isEmpty(departmentList)) {
			return Collections.emptyList();
		}
		List<DepartmentResponse> departmentResponseList = new ArrayList<>(departmentList.size());
		for (Department department : departmentList) {
			departmentResponseList.add(new DepartmentResponse(department));
		}
		return departmentResponseList;
	}

}
