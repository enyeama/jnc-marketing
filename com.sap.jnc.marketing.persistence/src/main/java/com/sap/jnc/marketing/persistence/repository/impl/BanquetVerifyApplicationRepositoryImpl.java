package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.Calendar;
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

import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetVerifyApplicationSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.BanquetVerification_;
import com.sap.jnc.marketing.persistence.model.BanquetVerifyApplication;
import com.sap.jnc.marketing.persistence.model.BanquetVerifyApplication_;
import com.sap.jnc.marketing.persistence.model.Banquet_;
import com.sap.jnc.marketing.persistence.model.Dealer_;
import com.sap.jnc.marketing.persistence.repository.BanquetVerifyApplicationRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BanquetVerifyApplicationRepositoryImpl extends SimpleJpaRepository<BanquetVerifyApplication, Long> implements BanquetVerifyApplicationRepository {

	@Autowired
	public BanquetVerifyApplicationRepositoryImpl(EntityManager em) {
		super(BanquetVerifyApplication.class, em);
	}

	@Override
	public Page<BanquetVerifyApplication> advanceSearch(BanquetVerifyApplicationSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			// Fetches
			query.distinct(true);
			root.fetch(BanquetVerifyApplication_.banquet, JoinType.LEFT);
			root.fetch(BanquetVerifyApplication_.office, JoinType.LEFT);
			root.fetch(BanquetVerifyApplication_.creator, JoinType.LEFT);
			root.fetch(BanquetVerifyApplication_.banquetVerifications, JoinType.LEFT);
			// Predicates
			final List<Predicate> predicates = new ArrayList<Predicate>();
			if (searchCriteria != null) {
				if (StringUtils.isNotBlank(searchCriteria.getExpressNO())) {
					predicates.add(cb.equal(root.get(BanquetVerifyApplication_.expressNO), searchCriteria.getExpressNO()));
				}
				if (null != searchCriteria.getBanquetId()) {
					predicates.add(cb.equal(root.join(BanquetVerifyApplication_.banquet, JoinType.LEFT).get(Banquet_.id), searchCriteria
						.getBanquetId()));
				}
				if (StringUtils.isNoneBlank(searchCriteria.getDealerName())) {
					predicates.add(cb.equal(root.join(BanquetVerifyApplication_.banquet, JoinType.LEFT)
						.join(Banquet_.dealer, JoinType.LEFT).get(Dealer_.name), searchCriteria.getDealerName()));
				}
				if (StringUtils.isNotBlank(searchCriteria.getRecycledNo())) {
					predicates.add(cb.equal(root.join(BanquetVerifyApplication_.banquetVerifications, JoinType.LEFT)
						.get(BanquetVerification_.verificationNumber), searchCriteria.getRecycledNo()));
				}
				if (searchCriteria.getStatus() != null) {
					predicates.add(cb.equal(root.get(BanquetVerifyApplication_.status), searchCriteria.getStatus()));
				}
			}

			Predicate p1 = null;
			final Calendar expressTimeFrom = searchCriteria.getExpressTimeFrom();
			final Calendar expressTimeTo = searchCriteria.getExpressTimeTo();
			if (expressTimeFrom != null && expressTimeTo != null) {
				p1 = cb.between(root.get(BanquetVerifyApplication_.expressTime), expressTimeFrom, expressTimeTo);
			}
			else if (expressTimeFrom != null && expressTimeTo == null) {
				p1 = cb.greaterThan(root.get(BanquetVerifyApplication_.expressTime), expressTimeFrom);
			}
			else if (expressTimeFrom == null && expressTimeTo != null) {
				p1 = cb.lessThan(root.get(BanquetVerifyApplication_.expressTime), expressTimeTo);
			}
			if (p1 != null) {
				predicates.add(p1);
			}
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}, pageRequest);
//		return null;
	}
}
