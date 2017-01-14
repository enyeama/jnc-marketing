package com.sap.jnc.marketing.service.contract.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sap.jnc.marketing.persistence.criteria.contract.ContractAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.ContractItem;
import com.sap.jnc.marketing.persistence.repository.ContractItemRepository;
import com.sap.jnc.marketing.service.contract.ContractService;

@Service
public class ContractServiceImpl implements ContractService {

	@Autowired
	private ContractItemRepository contractItemRepositoryImpl;

	@Override
	public Page<ContractItem> advanceSearch(ContractAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return contractItemRepositoryImpl.advanceSearch(searchCriteria, pageRequest);
	}

}
