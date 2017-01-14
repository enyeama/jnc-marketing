package com.sap.jnc.marketing.service.contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.sap.jnc.marketing.persistence.criteria.contract.ContractAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.ContractItem;

public interface ContractService {
	Page<ContractItem> advanceSearch(ContractAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);
}
