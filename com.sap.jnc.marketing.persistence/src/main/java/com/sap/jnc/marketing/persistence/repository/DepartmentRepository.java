package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.Department;

@NoRepositoryBean
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	List<Department> findAllOfficeByName();

	List<Department> findAllByExternalIds(List<String> externalIds);

	Department findLatestOneByExternalIds(String externalId);
}