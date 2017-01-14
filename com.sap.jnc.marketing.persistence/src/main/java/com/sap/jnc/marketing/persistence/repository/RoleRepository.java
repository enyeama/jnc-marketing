package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.Role;
import com.sap.jnc.marketing.persistence.model.Role_;

@NoRepositoryBean
public interface RoleRepository extends GeneralJpaRepository<Role, Role_, Long> {

	Role findOneByName(String name);

}
