package com.sap.jnc.marketing.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.criteria.dealer.DealerAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Dealer;

@NoRepositoryBean
public interface DealerRepository extends JpaRepository<Dealer, Long> {

	List<Dealer> findByExternalId(String externalId);

	List<Dealer> findDealerByCitymanager(String id);

	Dealer findDealerByExternalId(String id);

	Dealer findDealerById(Long dealerId);

	Page<Dealer> advanceSearch(DealerAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest);

	public Dealer findDealerWithLeaders(String dealerId);
	List<Dealer> findDealerByExternalIds(Collection<String> dealerIdSet);

	Page<Dealer> findAllWithNoUserReference(PageRequest pageRequest);

}
