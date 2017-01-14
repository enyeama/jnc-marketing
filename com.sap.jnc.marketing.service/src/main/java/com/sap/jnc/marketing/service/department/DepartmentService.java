/**
 * 
 */
package com.sap.jnc.marketing.service.department;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sap.jnc.marketing.dto.response.department.DepartmentResponse;

/**
 * @author Quansheng Liu I075496
 */
@Service
public interface DepartmentService {
	List<DepartmentResponse> listKAOffices();
}
