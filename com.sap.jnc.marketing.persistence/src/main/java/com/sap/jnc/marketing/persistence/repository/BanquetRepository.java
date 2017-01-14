package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Banquet;

/**
 * @author C5205383 Kevin Ren
 * @author I332242  Frank Zhu
 */
@NoRepositoryBean
public interface BanquetRepository extends JpaRepository<Banquet, Long> {
	
	Page<Banquet> advanceSearch(BanquetSearchKeywordNode searchCriteria, PageRequest pageRequest);
}
