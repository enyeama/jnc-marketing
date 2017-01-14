package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.JobPositionAssignment;

@NoRepositoryBean
public interface JobPositionAssignmentRepository extends JpaRepository<JobPositionAssignment, Long> {

	JobPositionAssignment findOneByPosition(String postionId);

}
