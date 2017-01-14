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
// import com.sap.jnc.marketing.dto.response.migration.DealerMigrationImportErrorResponse;
import com.sap.jnc.marketing.persistence.criteria.migration.DealerMigrationAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.model.Dealer_;
import com.sap.jnc.marketing.persistence.model.PositionView_;
import com.sap.jnc.marketing.persistence.repository.DealerMigrationRepository;

/**
 * @author I323691 Marco Huang
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class DealerMigrationRepositoryImpl extends SimpleJpaRepository<Dealer, Long> implements DealerMigrationRepository {

	@Autowired
	public DealerMigrationRepositoryImpl(EntityManager em) {
		super(Dealer.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Dealer> queryDealer(DealerMigrationAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return super
			.findAll((root, query, cb) -> {
				// Fetches
				root.fetch(Dealer_.parent, JoinType.LEFT);
				root.fetch(Dealer_.cityManager, JoinType.LEFT);
				// Predicates
				final List<Predicate> predicates = new ArrayList<>();
				if (StringUtils.isNotBlank(searchCriteria.getDealerId())) {
					predicates.add(cb.like(cb.upper(root.get(Dealer_.externalId)), "%" + StringUtils.upperCase((String) searchCriteria.getDealerId())
						+ "%"));
				}
				if (StringUtils.isNotBlank(searchCriteria.getDealerName())) {
					predicates.add(cb.like(cb.upper(root.get(Dealer_.name)), "%" + StringUtils.upperCase((String) searchCriteria.getDealerName())
						+ "%"));
				}
				if (searchCriteria.getDealerStatus() != null) {
					predicates.add(cb.equal(root.get(Dealer_.status), searchCriteria.getDealerStatus()));
				}
				if (StringUtils.isNotBlank(searchCriteria.getIsPlatform())) {
					predicates.add(cb.equal(root.get(Dealer_.isPlatformDealer), Boolean.parseBoolean(searchCriteria.getIsPlatform())));
				}
				if (StringUtils.isNotBlank(searchCriteria.getIsPrimaryDealer())) {
					predicates.add(cb.equal(root.get(Dealer_.isParent), searchCriteria.getIsPrimaryDealer()));
				}
				if (searchCriteria.getCityManagerPositionId() != null) {
					predicates.add(cb.like(cb.upper(root.join(Dealer_.cityManager).get(PositionView_.externalId)), "%"
						+ StringUtils.upperCase((String) searchCriteria.getCityManagerPositionId()) + "%"));
				}
				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}, pageRequest);
	}

	@Override
	public List<Dealer> findByExternalId(String externalId) {
		return super.findAll((root, query, cb) -> {
			return cb.equal(root.get(Dealer_.externalId), externalId);
		});
	}

	@Override
	public List<Dealer> findDealerByCitymanager(String id) {
		return super.findAll((root, query, cb) -> {
			root.fetch(Dealer_.cityManager, JoinType.LEFT);
			return cb.and(cb.equal(root.join(Dealer_.cityManager).get(PositionView_.externalId), id));
		});
	}

	@Override
	public Dealer findDealerByExternalId(String id) {
		return super.findOne((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Dealer_.externalId), id));
		});
	}

	@Override
	public Dealer findDealerById(Long dealerId) {
		return super.findOne((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Dealer_.id), dealerId));
		});
	}

	@Override
	public Dealer findDealerByExternalIdAndIsParent(String externalId, boolean isParent) {
		return super.findOne((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Dealer_.externalId), externalId), cb.equal(root.get(Dealer_.isParent), isParent));
		});
	}

	@Override
	public List<Dealer> findByExternalIds(Collection<String> dealerIdSet) {
		return super.findAll((root, query, cb) -> {
			return root.get(Dealer_.externalId).in(dealerIdSet);
		});
	}

	@Override
	public List<Dealer> findPrimaryDealerByExternalIds(Collection<String> dealerIdSet) {
		return super.findAll((root, query, cb) -> {
			return cb.and(root.get(Dealer_.externalId).in(dealerIdSet), cb.equal(root.get(Dealer_.isParent), true));
		});
	}
}
