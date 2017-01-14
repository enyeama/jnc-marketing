package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.enumeration.LogisticOperatorType;
import com.sap.jnc.marketing.persistence.enumeration.MovementType;
import com.sap.jnc.marketing.persistence.enumeration.OrderType;
import com.sap.jnc.marketing.persistence.model.IndividualProduct;
import com.sap.jnc.marketing.persistence.model.IndividualProduct_;
import com.sap.jnc.marketing.persistence.model.Logistic;
import com.sap.jnc.marketing.persistence.model.Logistic_;
import com.sap.jnc.marketing.persistence.repository.LogisticRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class LogisticRepositoryImpl extends GeneralJpaRepositoryImpl<Logistic, Logistic_, Long> implements LogisticRepository {

	@Autowired
	public LogisticRepositoryImpl(EntityManager em) {
		super(Logistic.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Logistic> findForDealerInCase(List<String> caseIds) {
		return super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Logistic_.orderType), OrderType.DEALER_ORDER), cb.equal(root.get(Logistic_.movementType),
				MovementType.DEALER_IN), cb.equal(root.get(Logistic_.operatorType), LogisticOperatorType.JNC), root.join(Logistic_.individualProduct,
					JoinType.LEFT).get(IndividualProduct_.caseId).in(caseIds));
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Logistic> findForDealerOutCase(List<String> caseIds) {
		return super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get(Logistic_.orderType), OrderType.DEALER_ORDER), cb.equal(root.get(Logistic_.movementType),
				MovementType.DEALER_IN), cb.equal(root.get(Logistic_.operatorType), LogisticOperatorType.DEALER), root.join(
					Logistic_.individualProduct, JoinType.LEFT).get(IndividualProduct_.caseId).in(caseIds));
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Logistic> findByCaseID(String caseID) {
		return super.findAll((root, query, cb) -> {
			root.fetch(Logistic_.individualProduct, JoinType.LEFT);
			return cb.equal(root.join(Logistic_.individualProduct).get(IndividualProduct_.caseId), caseID);// TODO Change association
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Logistic> findByErpOrderIDAndDmsOrderID(String erpOrderID, String dmsOrderID) {
		return super.findAll((root, query, cb) -> {
			final Predicate p1 = cb.equal(root.get(Logistic_.orderExternalERPId), erpOrderID);
			final Predicate p2 = cb.equal(root.get(Logistic_.orderId), dmsOrderID);
			return cb.and(p1, p2);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Logistic findPreLogisticByProductCapInnerCode(String capInnerCode) {
		List<Logistic> logisticList = super.findAll(new Specification<Logistic>() {
			@Override
			public Predicate toPredicate(final javax.persistence.criteria.Root<Logistic> root,
				final javax.persistence.criteria.CriteriaQuery<?> query, final CriteriaBuilder cb) {
				root.fetch(Logistic_.individualProduct, JoinType.LEFT);
				query.orderBy(cb.desc(root.get(Logistic_.timestamp)));
				return cb.equal(root.join(Logistic_.individualProduct).get(IndividualProduct_.capInnerCode), capInnerCode);
			}
		});
		if (CollectionUtils.isEmpty(logisticList)) {
			return null;
		}
		return logisticList.get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tuple> listDealerLogisticStatus(Long dealId) {
		final CriteriaBuilder cb = super.sharedEntityManager.getCriteriaBuilder();
		final CriteriaQuery<Tuple> query = cb.createTupleQuery();

		Subquery<String> sq = query.subquery(String.class);
		Root<Logistic> subQueryRoot = sq.from(Logistic.class);
		subQueryRoot.fetch(Logistic_.individualProduct, JoinType.LEFT);
		sq.select(subQueryRoot.join(Logistic_.individualProduct).get(IndividualProduct_.capInnerCode));
		Date now = new Date();
		Calendar calendarFrom = Calendar.getInstance();
		calendarFrom.setTime(now);
		calendarFrom.set(Calendar.HOUR_OF_DAY, 0);
		calendarFrom.set(Calendar.MINUTE, 0);
		calendarFrom.set(Calendar.SECOND, 0);
		calendarFrom.set(Calendar.MILLISECOND, 0);
		Calendar calendarTo = Calendar.getInstance();
		calendarTo.setTime(now);
		calendarTo.set(Calendar.HOUR_OF_DAY, 23);
		calendarTo.set(Calendar.MINUTE, 59);
		calendarTo.set(Calendar.SECOND, 59);
		calendarTo.set(Calendar.MILLISECOND, 999);
		Predicate p = cb.or(cb.equal(subQueryRoot.get(Logistic_.movementType), MovementType.DEALER_OUT), cb.equal(subQueryRoot.get(
			Logistic_.movementType), MovementType.DEALERTOTERMINAL_DEALER_OUT));
		sq.where(cb.and(cb.equal(subQueryRoot.get(Logistic_.operatorId), dealId), cb.equal(subQueryRoot.get(Logistic_.operatorType),
			LogisticOperatorType.DEALER), cb.between(subQueryRoot.get(Logistic_.timestamp), calendarFrom, calendarTo), p));

		// TODO
		final Root<Logistic> rootLogistic = query.from(Logistic.class);
		rootLogistic.fetch(Logistic_.individualProduct, JoinType.LEFT);
		Join<Logistic, IndividualProduct> join = rootLogistic.join(Logistic_.individualProduct);
		final CriteriaBuilder.In inCondition = cb.in(join.get(IndividualProduct_.capInnerCode));
		Predicate p2 = cb.or(cb.equal(rootLogistic.get(Logistic_.movementType), MovementType.LEADER_IN), cb.equal(rootLogistic.get(
			Logistic_.movementType), MovementType.DEALERTOTERMINAL_DEALER_OUT), cb.equal(rootLogistic.get(Logistic_.movementType),
				MovementType.DEALER_OUT));
		query.where(cb.and(p2, inCondition.value(sq)));
		query.groupBy(Arrays.asList(join.get(IndividualProduct_.boxId), rootLogistic.get(Logistic_.operatorId), rootLogistic.get(
			Logistic_.receiverId), rootLogistic.get(Logistic_.movementType)));
		// TODO
		query.select(cb.tuple(join.get(IndividualProduct_.boxId), rootLogistic.get(Logistic_.operatorId), rootLogistic.get(Logistic_.receiverId),
			rootLogistic.get(Logistic_.movementType)));
		// TODO
		return super.sharedEntityManager.createQuery(query).getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Logistic> findByCapInnerCodeAndMovementType(String capInnerCode, MovementType movementType) {
		return super.findAll((root, query, cb) -> {
			root.fetch(Logistic_.individualProduct, JoinType.LEFT);
			final Predicate p1 = cb.equal(root.join(Logistic_.individualProduct).get(IndividualProduct_.capInnerCode), capInnerCode);
			final Predicate p2 = cb.equal(root.get(Logistic_.movementType), movementType);
			return cb.and(p1, p2);
		});
	}

	@Override
	public List<Logistic> findByCapInnerCodesAndMovementType(Collection<String> capinnercodes, MovementType movementType) {
		return super.findAll((root, query, cb) -> {
			root.fetch(Logistic_.individualProduct, JoinType.LEFT);
			final Predicate p1 = cb.equal(root.get(Logistic_.movementType), movementType);
			final Predicate p2 = root.join(Logistic_.individualProduct, JoinType.LEFT).get(IndividualProduct_.capInnerCode).in(capinnercodes);
			return cb.and(p1, p2);
		});
	}

	@Override
	public List<Logistic> findByCaseIdsAndMovementType(Collection<String> caseIds, MovementType movementType) {
		return super.findAll((root, query, cb) -> {
			root.fetch(Logistic_.individualProduct, JoinType.LEFT);
			final Predicate p1 = cb.equal(root.get(Logistic_.movementType), movementType);
			final Predicate p2 = root.join(Logistic_.individualProduct, JoinType.LEFT).get(IndividualProduct_.caseId).in(caseIds);
			return cb.and(p1, p2);
		});
	}
}
