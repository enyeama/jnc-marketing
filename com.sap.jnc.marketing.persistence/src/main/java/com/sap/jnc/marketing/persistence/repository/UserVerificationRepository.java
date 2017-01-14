package com.sap.jnc.marketing.persistence.repository;

import com.sap.jnc.marketing.persistence.enumeration.ExternalUserRoleType;
import com.sap.jnc.marketing.persistence.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.UserVerification;

import java.util.List;

@NoRepositoryBean
public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {

    List<UserVerification> findOneByPhone(String phoneNumber, String userName);

    List<UserVerification> findAllByMobile(String mobile);

    List<UserVerification> findAllByReferenceId(Long id, ExternalUserRoleType type);
}
