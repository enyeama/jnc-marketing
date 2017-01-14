package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.UserReference;

@NoRepositoryBean
public interface UserReferenceRepository extends JpaRepository<UserReference, Long> {

}
