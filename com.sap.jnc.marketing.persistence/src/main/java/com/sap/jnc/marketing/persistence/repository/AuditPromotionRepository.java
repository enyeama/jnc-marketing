package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.audit.AuditTaskSearchCriteria;
import com.sap.jnc.marketing.persistence.model.AuditPromotion;
import com.sap.jnc.marketing.persistence.model.Region;

@NoRepositoryBean
public interface AuditPromotionRepository extends JpaRepository<AuditPromotion, Long> {

	Page<AuditPromotion> searchAudits(AuditTaskSearchCriteria criteria, PageRequest pageRequest, List<Region> regionList);

}
