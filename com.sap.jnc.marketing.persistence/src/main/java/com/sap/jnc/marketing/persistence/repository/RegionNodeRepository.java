package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.enumeration.RegionLevel;
import com.sap.jnc.marketing.persistence.model.RegionNode;

@NoRepositoryBean
public interface RegionNodeRepository extends JpaRepository<RegionNode, Long> {

	List<RegionNode> findAllByLevel(RegionLevel level);

}
