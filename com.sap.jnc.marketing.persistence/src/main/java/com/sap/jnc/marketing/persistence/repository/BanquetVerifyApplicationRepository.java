package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetVerifyApplicationSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.BanquetVerifyApplication;

/**
 * @author C5205383 Kevin Ren
 * @author I332242 Frank Zhu
 */
@NoRepositoryBean
public interface BanquetVerifyApplicationRepository extends JpaRepository<BanquetVerifyApplication, Long> {

	Page<BanquetVerifyApplication> advanceSearch(BanquetVerifyApplicationSearchKeywordNode searchCriteria, PageRequest pageRequest);
}
