package com.sap.jnc.marketing.persistence.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.audit.AuditorOfficeAssignmentSeatchCriteria;
import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment;

@NoRepositoryBean
public interface ProvinceManagerOfficeAssignmentRepository extends JpaRepository<ProvinceManagerOfficeAssignment, Long> {

	List<ProvinceManagerOfficeAssignment> findProvinceManagerByOfficeId(Long officeId);

	ProvinceManagerOfficeAssignment findOfficeIdByPositionId(Long provinceManagerId);

	List<ProvinceManagerOfficeAssignment> findAuditorByOfficeId(Long officeId, Long positionId);

	Page<ProvinceManagerOfficeAssignment> advanceSearch(AuditorOfficeAssignmentSeatchCriteria criteria, PageRequest pageRequest);

	List<ProvinceManagerOfficeAssignment> findOneByPositionId(String officeExternalId, String positionExternalId, Calendar validFrom,
		Calendar validTo,
		boolean isManager);

}
