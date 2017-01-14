package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.criteria.dealer.DealerAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Contact_;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.Dealer_;
import com.sap.jnc.marketing.persistence.model.PositionView_;
import com.sap.jnc.marketing.persistence.repository.DealerRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class DealerRepositoryImpl extends SimpleJpaRepository<Dealer, Long> implements DealerRepository {

	@Autowired
	public DealerRepositoryImpl(EntityManager em) {
		super(Dealer.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Dealer> findByExternalId(String externalId) {
		return super.findAll((root, query, cb) -> {
			return cb.equal(root.get(Dealer_.externalId), externalId);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Dealer> advanceSearch(DealerAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			// Fetches
			// root.fetch(Dealer_.cityManager, JoinType.LEFT);
			root.fetch(Dealer_.legalContact, JoinType.LEFT);
			root.fetch(Dealer_.office, JoinType.LEFT);
			root.fetch(Dealer_.parent, JoinType.LEFT);
			// Predicates
			final List<Predicate> predicates = new ArrayList<>(4);
			if (StringUtils.isNotBlank(searchCriteria.getExternalId())) {
				predicates.add(cb.like(cb.upper(root.get(Dealer_.externalId)), "%" + StringUtils.upperCase(searchCriteria.getExternalId()) + "%"));
			}
			if (StringUtils.isNotBlank(searchCriteria.getLegalContactName())) {
				predicates.add(cb.like(cb.upper(root.join(Dealer_.legalContact).get(Contact_.name)), "%" + StringUtils.upperCase(searchCriteria
					.getLegalContactName()) + "%"));
			}
			if (searchCriteria.getStatus() != null) {
				predicates.add(cb.equal(root.get(Dealer_.status), searchCriteria.getStatus()));
			}
			if (searchCriteria.getIsPlatformDealer() != null) {
				predicates.add(cb.equal(root.get(Dealer_.isPlatformDealer), searchCriteria.getIsPlatformDealer()));
			}
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}, pageRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Dealer> findAllWithNoUserReference(PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			// Fetch
			query.distinct(true);
			root.fetch(Dealer_.userReferences, JoinType.LEFT);
			root.fetch(Dealer_.office, JoinType.LEFT);
			root.fetch(Dealer_.legalContact, JoinType.LEFT);
			root.fetch(Dealer_.cityManager, JoinType.LEFT);
			root.fetch(Dealer_.cityManagerEmployee, JoinType.LEFT);
			root.fetch(Dealer_.parent, JoinType.LEFT);
			// Filter
			return cb.isEmpty(root.get(Dealer_.userReferences));
		}, pageRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Dealer> findDealerByCitymanager(String id) {
		return super.findAll((root, query, cb) -> {
			root.fetch(Dealer_.cityManager, JoinType.LEFT);
			return cb.and(cb.equal(root.join(Dealer_.cityManager).get(PositionView_.externalId), id));
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Dealer findDealerByExternalId(String id) {
		return super.findOne((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Dealer_.externalId), id));
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Dealer> findDealerByExternalIds(Collection<String> dealerIdSet) {
		return super.findAll((root, query, cb) -> {
			return root.get(Dealer_.externalId).in(dealerIdSet);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Dealer findDealerById(Long dealerID) {
		return super.findOne((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Dealer_.id), dealerID));
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Dealer findDealerWithLeaders(String dealerId) {
		return super.findOne((root, query, cb) -> {
			query.distinct(true);
			root.fetch(Dealer_.leaders, JoinType.LEFT).fetch(PositionView_.employees, JoinType.LEFT);
			return cb.equal(root.get(Dealer_.id), dealerId);
		});
	}
}
