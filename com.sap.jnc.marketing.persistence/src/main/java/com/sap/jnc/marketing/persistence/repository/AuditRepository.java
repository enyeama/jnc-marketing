package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.enumeration.AuditStatus;
import com.sap.jnc.marketing.persistence.enumeration.AuditType;
import com.sap.jnc.marketing.persistence.model.Audit;

/**
 * @author C5231393 Xu Xiaolei
 * @author I330182 Vodka Li
 */
@NoRepositoryBean
public interface AuditRepository extends JpaRepository<Audit, Long> {

	Audit findOneByAuditId(Long id);

	List<Audit> findAllByAuditIds(List<Long> idsList);

	List<Audit> findAllWithItemAndPictures(Long id);

	Long countByTypeAndStatus(String auditorId, AuditType auditType, AuditStatus auditStatus);

}
