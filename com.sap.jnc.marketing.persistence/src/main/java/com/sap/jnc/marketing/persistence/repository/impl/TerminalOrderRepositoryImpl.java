package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.criteria.dealer.DealerOrdersAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.criteria.kaorder.KAOrderAdvancedSearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.PeriodValidateType;
import com.sap.jnc.marketing.persistence.enumeration.ProductDmsCategoryLevel;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalOrderType;
import com.sap.jnc.marketing.persistence.model.Employee_;
import com.sap.jnc.marketing.persistence.model.PositionView_;
import com.sap.jnc.marketing.persistence.model.Position_;
import com.sap.jnc.marketing.persistence.model.ProductDmsCategory_;
import com.sap.jnc.marketing.persistence.model.Product_;
import com.sap.jnc.marketing.persistence.model.TerminalOrder;
import com.sap.jnc.marketing.persistence.model.TerminalOrder_;
import com.sap.jnc.marketing.persistence.model.Terminal_;
import com.sap.jnc.marketing.persistence.repository.TerminalOrderRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TerminalOrderRepositoryImpl extends SimpleJpaRepository<TerminalOrder, Long> implements TerminalOrderRepository {

	@Autowired
	public TerminalOrderRepositoryImpl(EntityManager em) {
		super(TerminalOrder.class, em);
	}

	@Override
	public List<TerminalOrder> findOrders(String terminalId, String salePosExtId, List<TerminalOrderType> orderType) {
		return super.findAll((root, query, cb) -> {
			root.fetch(TerminalOrder_.terminal, JoinType.LEFT);
			root.fetch(TerminalOrder_.product, JoinType.LEFT);
			In<TerminalOrderType> inPredicate = cb.in(root.get(TerminalOrder_.type));
			for (TerminalOrderType type : orderType) {
				inPredicate.value(type);
			}
			return cb.and(cb.equal(root.join(TerminalOrder_.createrPosition, JoinType.LEFT).get(Position_.externalId), salePosExtId),
				inPredicate,
				cb.notEqual(root.get(TerminalOrder_.status), TerminalOrderStatus.CANCEL),
				cb.equal(root.join(TerminalOrder_.terminal, JoinType.LEFT).get(Terminal_.id), terminalId));
		});
	}

	@Override
	public List<TerminalOrder> findOrdersByStatus(String terminalId, String salePosExtId, TerminalOrderStatus status,
		List<TerminalOrderType> orderType) {
		return super.findAll((root, query, cb) -> {
			root.fetch(TerminalOrder_.terminal, JoinType.LEFT);
			root.fetch(TerminalOrder_.product, JoinType.LEFT);
			In<TerminalOrderType> inPredicate = cb.in(root.get(TerminalOrder_.type));
			for (TerminalOrderType type : orderType) {
				inPredicate.value(type);
			}
			return cb.and(cb.equal(root.join(TerminalOrder_.createrPosition, JoinType.LEFT).get(Position_.externalId), salePosExtId),
				inPredicate,
				cb.equal(root.get(TerminalOrder_.status), status),
				cb.equal(root.join(TerminalOrder_.terminal, JoinType.LEFT).get(Terminal_.id), terminalId));
		});
	}

	@Override
	public List<TerminalOrder> findOrdersByLeader(String leaderPosExtId, TerminalOrderStatus status) {
		return super.findAll((root, query, cb) -> {
			root.fetch(TerminalOrder_.terminal, JoinType.LEFT);
			root.fetch(TerminalOrder_.product, JoinType.LEFT);
			if (status == TerminalOrderStatus.CANCEL) {
				return cb.and(cb.equal(root.join(TerminalOrder_.responsibleLeaderPosition, JoinType.LEFT).get(Position_.externalId), leaderPosExtId),
					cb.notEqual(root.get(TerminalOrder_.status), TerminalOrderStatus.CANCEL));
			}
			else {
				return cb.and(cb.equal(root.join(TerminalOrder_.responsibleLeaderPosition, JoinType.LEFT).get(Position_.externalId), leaderPosExtId),
					cb.equal(root.get(TerminalOrder_.status), status));
			}
		});
	}

	@Override
	public TerminalOrder findOneWithCategory(Long orderId) {
		List<TerminalOrder> terminalOrders = super.findAll((root, query, cb) -> {
			root.fetch(TerminalOrder_.product, JoinType.LEFT).fetch(Product_.productDmsCategories, JoinType.LEFT);
			return cb.and(cb.equal(root.get(TerminalOrder_.id), orderId), cb.equal(root.join(TerminalOrder_.product, JoinType.LEFT).join(
				Product_.productDmsCategories, JoinType.LEFT).get(ProductDmsCategory_.level), ProductDmsCategoryLevel.THIRD_LEVEL));
		});
		return terminalOrders.get(0);
	}

	@Override
	public Page<TerminalOrder> advanceKAOrderSearch(KAOrderAdvancedSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			String branchName = searchCriteria.getBranchName();
			String kaSystemNumber = searchCriteria.getKaSystemNumber();
			TerminalOrderStatus terminalOrderStatus = searchCriteria.getTerminalOrderStatus();
			Calendar dateFrom = searchCriteria.getDateFrom();
			Calendar dateTo = searchCriteria.getDateTo();
			Calendar date = searchCriteria.getDate();
			Long kaSpecialistPositionId = searchCriteria.getKaSpecialistPositionId();
			Long cityManagerEmployeeId = searchCriteria.getCityManagerEmployeeId();
			PeriodValidateType validType = searchCriteria.getValidType();
			List<Predicate> predicateList = new ArrayList<>();
			predicateList.add(cb.equal(root.get(TerminalOrder_.type), TerminalOrderType.KAORDER));
			Join terminalJoin = null;

			if (!StringUtils.isEmpty(branchName)) {
				if (terminalJoin == null) {
					terminalJoin = root.join(TerminalOrder_.terminal);
				}
				predicateList.add(cb.like(cb.upper(terminalJoin.get(Terminal_.branchName)), "%" + branchName.trim().toUpperCase() + "%"));
			}
			if (!StringUtils.isEmpty(kaSystemNumber)) {
				if (terminalJoin == null) {
					terminalJoin = root.join(TerminalOrder_.terminal);
				}
				predicateList.add(cb.equal(cb.upper(terminalJoin.get(Terminal_.KASystemNumber)), kaSystemNumber.trim().toUpperCase()));
			}
			if (cityManagerEmployeeId != null && cityManagerEmployeeId.longValue() > 0) {
				Join cityManagerEmployeeJoin = root.join(TerminalOrder_.cityManagerEmployee);
				predicateList.add(cb.equal(cityManagerEmployeeJoin.get(Employee_.id), cityManagerEmployeeId));
			}
			if (kaSpecialistPositionId != null && kaSpecialistPositionId.longValue() > 0) {
				Join terminalKaSpecialistPositionJoin = null;
				if (terminalJoin == null) {
					terminalJoin = root.join(TerminalOrder_.terminal);
				}
				terminalKaSpecialistPositionJoin = terminalJoin.join(Terminal_.KASpecialist);
				predicateList.add(cb.equal(terminalKaSpecialistPositionJoin.get(PositionView_.id), kaSpecialistPositionId));
			}
			if (terminalOrderStatus != null) {
				predicateList.add(cb.equal(root.get(TerminalOrder_.status), terminalOrderStatus));
			}
			else {
				predicateList.add(cb.notEqual(root.get(TerminalOrder_.status), TerminalOrderStatus.CANCEL));
			}
			if (date != null && validType != null && validType != PeriodValidateType.BETWEEN) {
				switch (validType) {
				case GREATERTHAN:
					date.set(Calendar.HOUR_OF_DAY, 0);
					date.set(Calendar.MINUTE, 0);
					date.set(Calendar.SECOND, 0);
					date.set(Calendar.MILLISECOND, 0);
					predicateList.add(cb.greaterThan(root.get(TerminalOrder_.createOn), date));
					break;
				case LESSTHAN:
					date.set(Calendar.HOUR_OF_DAY, 23);
					date.set(Calendar.MINUTE, 59);
					date.set(Calendar.SECOND, 59);
					date.set(Calendar.MILLISECOND, 999);
					predicateList.add(cb.lessThan(root.get(TerminalOrder_.createOn), date));
					break;
				case EQUAlTO:
					date.set(Calendar.HOUR_OF_DAY, 0);
					date.set(Calendar.MINUTE, 0);
					date.set(Calendar.SECOND, 0);
					date.set(Calendar.MILLISECOND, 0);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date.getTime());
					calendar.set(Calendar.HOUR_OF_DAY, 23);
					calendar.set(Calendar.MINUTE, 59);
					calendar.set(Calendar.SECOND, 59);
					calendar.set(Calendar.MILLISECOND, 999);
					predicateList.add(cb.between(root.get(TerminalOrder_.createOn), date, calendar));
					break;
				}
			}
			else if (dateFrom != null && dateTo != null && validType == PeriodValidateType.BETWEEN) {
				dateFrom.set(Calendar.HOUR_OF_DAY, 0);
				dateFrom.set(Calendar.MINUTE, 0);
				dateFrom.set(Calendar.SECOND, 0);
				dateFrom.set(Calendar.MILLISECOND, 0);
				dateTo.set(Calendar.HOUR_OF_DAY, 23);
				dateTo.set(Calendar.MINUTE, 59);
				dateTo.set(Calendar.SECOND, 59);
				dateTo.set(Calendar.MILLISECOND, 999);
				predicateList.add(cb.between(root.get(TerminalOrder_.createOn), dateFrom, dateTo));
			}
			if (CollectionUtils.isEmpty(predicateList)) {
				return null;
			}
			else if (predicateList.size() == 1) {
				return predicateList.get(0);
			}
			else {
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		} , pageRequest);
	}

	@Override
	public Page<TerminalOrder> findOrdersByDealer(DealerOrdersAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			query.distinct(true);
			root.fetch(TerminalOrder_.terminal, JoinType.LEFT);
			root.fetch(TerminalOrder_.product, JoinType.LEFT);
			// Predicates
			final List<Predicate> predicates = new ArrayList<>(3);
			if (StringUtils.isNotBlank(searchCriteria.getOrderId())) {
				predicates.add(cb.equal(root.get(TerminalOrder_.id), searchCriteria.getOrderId()));
			}
			if (StringUtils.isNotBlank(searchCriteria.getTerminal())) {
				predicates.add(cb.like(cb.upper(root.join(TerminalOrder_.terminal).get(Terminal_.title)), StringUtils.upperCase(searchCriteria
					.getTerminal()) + "%"));
			}
			predicates.add(cb.equal(root.get(TerminalOrder_.status), searchCriteria.getStatus()));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		} , pageRequest);
	}

	@Override
	public List<TerminalOrder> findOrdersByTerminal(String terminalId, TerminalOrderStatus status) {
		return super.findAll((root, query, cb) -> {
			query.distinct(true);
			root.fetch(TerminalOrder_.terminal, JoinType.LEFT);
			root.fetch(TerminalOrder_.product, JoinType.LEFT);
			root.fetch(TerminalOrder_.dealer, JoinType.LEFT);
			root.fetch(TerminalOrder_.responsibleLeader, JoinType.LEFT);
			if (status == TerminalOrderStatus.CANCEL) {
				return cb.and(cb.notEqual(root.get(TerminalOrder_.status), status),
					cb.equal(root.join(TerminalOrder_.terminal, JoinType.LEFT).get(Terminal_.id), terminalId));
			} else {
				return cb.and(cb.equal(root.get(TerminalOrder_.status), status),
					cb.equal(root.join(TerminalOrder_.terminal, JoinType.LEFT).get(Terminal_.id), terminalId));
			}
		});
	}
}
