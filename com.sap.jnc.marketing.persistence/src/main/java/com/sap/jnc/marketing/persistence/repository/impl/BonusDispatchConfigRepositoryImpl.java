package com.sap.jnc.marketing.persistence.repository.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.criteria.dealer.BonusConfigAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.exception.bonus.BonusDispatchConfigRepositoryException;
import com.sap.jnc.marketing.persistence.model.BonusDispatchConfig;
import com.sap.jnc.marketing.persistence.model.BonusDispatchConfig_;
import com.sap.jnc.marketing.persistence.model.ProductErpCategory_;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod_;
import com.sap.jnc.marketing.persistence.repository.BonusDispatchConfigRepository;

/**
 * 红包配置Repository
 * 
 * @author I327119
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BonusDispatchConfigRepositoryImpl extends SimpleJpaRepository<BonusDispatchConfig, Long> implements BonusDispatchConfigRepository {

	private static final String END_DATE = "9999-12-31";
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private EntityManager em;

	@Autowired
	public BonusDispatchConfigRepositoryImpl(EntityManager em) {
		super(BonusDispatchConfig.class, em);
		this.em = em;
	}

	@Override
	public BonusDispatchConfig findCurrentValidConfigByErpCategoryId(Long erpCategoryId) {
		BonusDispatchConfig bonusDispatchConfig = null;
		Calendar current = Calendar.getInstance();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<BonusDispatchConfig> criteriaQuery = criteriaBuilder.createQuery(BonusDispatchConfig.class);
		Root<BonusDispatchConfig> bonusDispatchConfigRoot = criteriaQuery.from(BonusDispatchConfig.class);
		Predicate idPredicate = criteriaBuilder.equal(bonusDispatchConfigRoot.get(BonusDispatchConfig_.erpCategory).get(ProductErpCategory_.id),
			erpCategoryId);
		Predicate fromPredicate = criteriaBuilder.lessThanOrEqualTo(bonusDispatchConfigRoot.join(BonusDispatchConfig_.validityPeriod).get(
			ValidityPeriod_.validFrom), current);
		Predicate toPredicate = criteriaBuilder.greaterThanOrEqualTo(bonusDispatchConfigRoot.join(BonusDispatchConfig_.validityPeriod).get(
			ValidityPeriod_.validTo), current);
		criteriaQuery.where(idPredicate, fromPredicate, toPredicate);

		List<BonusDispatchConfig> bonusDispatchConfigList = em.createQuery(criteriaQuery.select(bonusDispatchConfigRoot)).getResultList();

		// 一个物料分类在当前时间内只能对应一条有效红包配置
		if (CollectionUtils.size(bonusDispatchConfigList) > 1) {
			throw new BonusDispatchConfigRepositoryException(BonusDispatchConfigRepositoryException.GET_BONUS_CONFIG_ERROR);
		}
		else if (CollectionUtils.isNotEmpty(bonusDispatchConfigList)) {
			bonusDispatchConfig = bonusDispatchConfigList.get(0);
		}

		return bonusDispatchConfig;
	}

	@Override
	public Page<BonusDispatchConfig> advanceSearch(BonusConfigAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// Fetches
			root.fetch(BonusDispatchConfig_.erpCategory, JoinType.LEFT);
			// Predicates
			final List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.isNotBlank(searchCriteria.getCategoryId())) {
				predicates.add(cb.equal(root.join(BonusDispatchConfig_.erpCategory).get(ProductErpCategory_.id), Long.parseLong(searchCriteria
					.getCategoryId())));
			}

			if (StringUtils.isNotBlank(searchCriteria.getValidFrom())) {
				try {
					Date validFromDate = sdf.parse(searchCriteria.getValidFrom());
					Calendar validFromCal = Calendar.getInstance();
					validFromCal.setTime(validFromDate);

					// 比较初始日期
					Predicate fPred = cb.greaterThan(root.join(BonusDispatchConfig_.validityPeriod).get(ValidityPeriod_.validFrom), validFromCal);

					predicates.add(fPred);
				}
				catch (ParseException e) {
					throw new BonusDispatchConfigRepositoryException(BonusDispatchConfigRepositoryException.DATETIME_PARSE_ERROR);
				}
			}

			if (StringUtils.isNotBlank(searchCriteria.getValidTo())) {
				try {
					Date validToDate = sdf.parse(searchCriteria.getValidTo());
					Calendar validToCal = Calendar.getInstance();
					validToCal.setTime(validToDate);

					// 比较结束日期
					Predicate tPred = cb.lessThan(root.join(BonusDispatchConfig_.validityPeriod).get(ValidityPeriod_.validTo), validToCal);

					predicates.add(tPred);
				}
				catch (Exception e) {
					throw new BonusDispatchConfigRepositoryException(BonusDispatchConfigRepositoryException.DATETIME_PARSE_ERROR);
				}
			}

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}, pageRequest);
	}

	@Override
	public BonusDispatchConfig findLastBonusByCategoryId(Long erpCategoryId) throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BonusDispatchConfig> query = cb.createQuery(BonusDispatchConfig.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		// From
		Root<BonusDispatchConfig> root = query.from(BonusDispatchConfig.class);

		// Where
		if (erpCategoryId != null) {
			predicates.add(cb.equal(root.join(BonusDispatchConfig_.erpCategory).get(ProductErpCategory_.id), erpCategoryId));
		}
		Calendar DEFAULT_END_DATE = Calendar.getInstance();
		DEFAULT_END_DATE.setTime(SIMPLE_DATE_FORMAT.parse(END_DATE));

		predicates.add(cb.equal(root.join(BonusDispatchConfig_.validityPeriod).get(ValidityPeriod_.validTo), DEFAULT_END_DATE));
		query.where(predicates.toArray(new Predicate[predicates.size()]));

		List<BonusDispatchConfig> lastBonusDispatchConfig = em.createQuery(query).getResultList();

		if (CollectionUtils.isNotEmpty(lastBonusDispatchConfig)) {
			return lastBonusDispatchConfig.get(0);
		}

		return null;
	}

}
