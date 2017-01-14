package com.sap.jnc.marketing.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.AuditPicture;

@NoRepositoryBean
public interface AuditPictureRepository extends JpaRepository<AuditPicture, Long> {

	List<AuditPicture> findAllByAuditId(Long auditId);

}
