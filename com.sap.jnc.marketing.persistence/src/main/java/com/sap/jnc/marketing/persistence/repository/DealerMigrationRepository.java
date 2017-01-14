package com.sap.jnc.marketing.persistence.repository;

import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.migration.DealerMigrationAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Dealer;



/**
 * @author I323691 Marco Huang
 */
@NoRepositoryBean
public interface DealerMigrationRepository extends JpaRepository<Dealer, Long> {
	
	
	List<Dealer> findByExternalId(String externalId);

	List<Dealer> findDealerByCitymanager(String id);

	Dealer findDealerByExternalId(String id);

	Dealer findDealerById(Long dealerId);

	Page<Dealer> queryDealer(DealerMigrationAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);

	Dealer findDealerByExternalIdAndIsParent(String primaryDealerId, boolean b);
	
	List<Dealer> findByExternalIds(Collection<String> dealerIdSet);
	
	List<Dealer> findPrimaryDealerByExternalIds(Collection<String> dealerIdSet);
}
