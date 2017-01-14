/**
 * 
 */
package com.sap.jnc.marketing.api.admin.web.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sap.jnc.marketing.dto.response.department.DepartmentResponse;
import com.sap.jnc.marketing.service.department.DepartmentService;

/**
 * @author Quansheng Liu I075496
 */
@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(value = "/kaoffices", method = RequestMethod.GET)
	public Collection<DepartmentResponse> listKAOffices() {
		return this.departmentService.listKAOffices();
	}
}
