package com.sap.jnc.marketing.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.DepartmentView;

@NoRepositoryBean
public interface DepartmentViewRepository extends JpaRepository<DepartmentView, String> {
	List<DepartmentView> findByIds(Collection<Long> ids);

	DepartmentView findAllById(String id);

	List<DepartmentView> findDepartmentBysuper(String id);

	DepartmentView findByDepartmentName(String departmentName);
	
	DepartmentView findByPositionId(String positionId);

	List<DepartmentView> findAllOffices();

}
