package com.sap.jnc.marketing.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.Contract;

@NoRepositoryBean
public interface ContractRepository extends JpaRepository<Contract, Long> {
	List<Contract> findContratsByManager(String id);

	List<Contract> findContractByExternalIds(Collection<String> contractIdSet);

	Collection<Contract> findContractByTerminalOrderInfo(Long channelId, Long productId, Long regionId);
}
