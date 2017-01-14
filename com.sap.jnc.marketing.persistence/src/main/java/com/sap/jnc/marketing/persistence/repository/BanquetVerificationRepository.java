package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetVerificationSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.BanquetVerification;

/**
 * @author C5205383 Kevin Ren
 */
@NoRepositoryBean
public interface BanquetVerificationRepository extends JpaRepository<BanquetVerification, Long> {
	public BanquetVerification findByVerificationNumber(String verificationNumber);
	
	Page<BanquetVerification> advanceSearch(BanquetVerificationSearchKeywordNode searchCriteria, PageRequest pageRequest);
}
