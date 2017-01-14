package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.contract.ContractAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.ContractItem;

@NoRepositoryBean
public interface ContractItemRepository extends JpaRepository<ContractItem, Long> {
	public Page<ContractItem> advanceSearch(ContractAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);

}
