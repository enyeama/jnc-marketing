package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.EmployeePositionAssignment;
import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment;

/**
 * @author I071053 Diouf Du
 */
@NoRepositoryBean
public interface EmployeePositionAssignmentRepository extends JpaRepository<EmployeePositionAssignment, Long> {

	EmployeePositionAssignment findOneByPostionId(Long provinceManagerPostionId);

	EmployeePositionAssignment findOneByEmpoyeeId(Long provinceManagerId);

	List<EmployeePositionAssignment> findAllByPostions(List<ProvinceManagerOfficeAssignment> auditorResult);

	EmployeePositionAssignment findOne(Long employeeId);

}
