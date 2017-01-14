package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.audit.AuditorOfficeAssignmentSeatchCriteria;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.PositionView;

/**
 * @author I071053 Diouf Du
 */
@NoRepositoryBean
public interface EmployeeViewRepository extends JpaRepository<EmployeeView, String> {

	Page<EmployeeView> advanceSearch(AuditorOfficeAssignmentSeatchCriteria criteria, PageRequest pageRequest);

	Page<EmployeeView> findAllWithNoUserReference(PageRequest pageRequest);

	List<EmployeeView> findAllByIds(List<PositionView> empPosResult);

	EmployeeView findOneById(Long id);

	EmployeeView findOneByExternalId(String externalId);

	List<EmployeeView> findAllAuditors();

	PositionView findPositionByEmployeeIdAndJobType(Long employeeId, String jobExternalId);

}
