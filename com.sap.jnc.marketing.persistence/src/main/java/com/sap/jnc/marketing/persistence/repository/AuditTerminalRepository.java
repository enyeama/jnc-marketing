package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.audit.AuditTaskSearchCriteria;
import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;
import com.sap.jnc.marketing.persistence.enumeration.AuditType;
import com.sap.jnc.marketing.persistence.model.AuditTerminal;
import com.sap.jnc.marketing.persistence.model.Region;

/**
 * @author C5231393 Xu Xiaolei
 * @author I330182 Vodka Li
 */
@NoRepositoryBean
public interface AuditTerminalRepository extends JpaRepository<AuditTerminal, Long> {

	Page<AuditTerminal> searchAudits(AuditTaskSearchCriteria criteria, PageRequest pageRequest, List<Region> regionList);

	List<AuditTerminal> findAllByTypeAndStatus(Long auditorId, AuditType auditType, AuditStatus auditStatus);

	AuditTerminal findOneTerminalAuditById(Long id);
	
	AuditTerminal findOneTerminalByTargetId(Long auditId);
}
