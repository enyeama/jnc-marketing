package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.Privilege;
import com.sap.jnc.marketing.persistence.model.Privilege_;

@NoRepositoryBean
public interface PrivilegeRepository extends GeneralJpaRepository<Privilege, Privilege_, Long> {

}
