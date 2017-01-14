package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.RegionPositionAssignment;

/**
 * @author I071053 Diouf Du
 */
@NoRepositoryBean
public interface RegionPositionAssignmentRepository extends JpaRepository<RegionPositionAssignment, Long> {

}
