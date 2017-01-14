package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.AuditItem;

@NoRepositoryBean
public interface AuditItemRepository extends JpaRepository<AuditItem, Long> {

	List<AuditItem> findAllByAuditId(Long id);

}
