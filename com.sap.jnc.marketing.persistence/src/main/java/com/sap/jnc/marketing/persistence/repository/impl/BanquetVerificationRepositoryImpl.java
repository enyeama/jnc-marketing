package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetVerificationSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.BanquetVerification;
import com.sap.jnc.marketing.persistence.model.BanquetVerification_;
import com.sap.jnc.marketing.persistence.repository.BanquetVerificationRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BanquetVerificationRepositoryImpl extends SimpleJpaRepository<BanquetVerification, Long> implements BanquetVerificationRepository {

	@Autowired
	public BanquetVerificationRepositoryImpl(EntityManager em) {
		super(BanquetVerification.class, em);
	}
	
	@Override
	public BanquetVerification findByVerificationNumber(String verificationNumber) {
		return super.findOne((root,query,cb) -> {
			return cb.equal(root.get(BanquetVerification_.verificationNumber), verificationNumber);
		});
	}
	
	@Override
	public Page<BanquetVerification> advanceSearch(BanquetVerificationSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		// return super.findAll((root, query, cb) -> {
		// // Fetches
		// query.distinct(true);
		// root.fetch(BanquetVerifyApplication_.banquet, JoinType.LEFT);
		// root.fetch(BanquetVerifyApplication_.office, JoinType.LEFT);
		// root.fetch(BanquetVerifyApplication_.creator, JoinType.LEFT);
		// root.fetch(BanquetVerifyApplication_.banquetVerifications, JoinType.LEFT);
		// // Predicates
		// final List<Predicate> predicates = new ArrayList<Predicate>();
		// if (searchCriteria != null) {
		// if (StringUtils.isNotBlank(searchCriteria.getExpressNO())) {
		// predicates.add(cb.equal(root.get(BanquetVerifyApplication_.expressNO), searchCriteria.getExpressNO()));
		// }
		// if (null != searchCriteria.getBanquetId()) {
		// predicates.add(cb.equal(root.join(BanquetVerifyApplication_.banquet, JoinType.LEFT).get(Banquet_.id), searchCriteria
		// .getBanquetId()));
		// }
		// if (StringUtils.isNoneBlank(searchCriteria.getDealerName())) {
		// predicates.add(cb.equal(root.join(BanquetVerifyApplication_.banquet, JoinType.LEFT)
		// .join(Banquet_.dealer, JoinType.LEFT).get(Dealer_.name), searchCriteria.getDealerName()));
		// }
		// if (StringUtils.isNotBlank(searchCriteria.getRecycledNo())) {
		// predicates.add(cb.equal(root.join(BanquetVerifyApplication_.banquetVerifications, JoinType.LEFT)
		// .get(BanquetVerification_.verificationNumber), searchCriteria.getRecycledNo()));
		// }
		// if (searchCriteria.getStatus() != null) {
		// predicates.add(cb.equal(root.get(BanquetVerifyApplication_.status), searchCriteria.getStatus()));
		// }
		// }
		//
		// Predicate p1 = null;
		// final Calendar expressTimeFrom = searchCriteria.getExpressTimeFrom();
		// final Calendar expressTimeTo = searchCriteria.getExpressTimeTo();
		// if (expressTimeFrom != null && expressTimeTo != null) {
		// p1 = cb.between(root.get(BanquetVerifyApplication_.expressTime), expressTimeFrom, expressTimeTo);
		// }
		// else if (expressTimeFrom != null && expressTimeTo == null) {
		// p1 = cb.greaterThan(root.get(BanquetVerifyApplication_.expressTime), expressTimeFrom);
		// }
		// else if (expressTimeFrom == null && expressTimeTo != null) {
		// p1 = cb.lessThan(root.get(BanquetVerifyApplication_.expressTime), expressTimeTo);
		// }
		// if (p1 != null) {
		// predicates.add(p1);
		// }
		// return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		// }, pageRequest);
		return null;
	}
}
