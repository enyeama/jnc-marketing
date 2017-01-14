package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.BanquetVerificationItem;

/**
 * @author C5205383 Kevin Ren
 */
@NoRepositoryBean
public interface BanquetVerificationItemRepository extends JpaRepository<BanquetVerificationItem, Long> {

}
