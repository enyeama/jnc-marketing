package com.sap.jnc.marketing.persistence.repository.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

import com.sap.jnc.marketing.persistence.criteria.payment.PaymentAccountConfigAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.PaymentAccountConfig;
import com.sap.jnc.marketing.persistence.model.PaymentAccountConfig_;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod_;
import com.sap.jnc.marketing.persistence.repository.PaymentAccountConfigRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PaymentAccountConfigRepositoryImpl extends SimpleJpaRepository<PaymentAccountConfig, Long> implements PaymentAccountConfigRepository {

	private EntityManager em;
	private static final String END_DATE = "9999-12-31";
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	public PaymentAccountConfigRepositoryImpl(EntityManager em) {
		super(PaymentAccountConfig.class, em);
		this.em = em;
	}

	@Override
	public Page<PaymentAccountConfig> advanceSearch(PaymentAccountConfigAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			final List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotBlank(searchCriteria.getValidFrom())) {
				try {
					Date validFromDate = sdf.parse(searchCriteria.getValidFrom());
					Calendar validFromCal = Calendar.getInstance();
					validFromCal.setTime(validFromDate);

					// 比较初始日期
					Predicate fPred = cb.greaterThan(root.join(PaymentAccountConfig_.validityPeriod).get(ValidityPeriod_.validFrom), validFromCal);

					predicates.add(fPred);
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}

			}

			if (StringUtils.isNotBlank(searchCriteria.getValidTo())) {
				try {
					Date validToDate = sdf.parse(searchCriteria.getValidTo());
					Calendar validToCal = Calendar.getInstance();
					validToCal.setTime(validToDate);

					// 比较结束日期
					Predicate tPred = cb.lessThan(root.join(PaymentAccountConfig_.validityPeriod).get(ValidityPeriod_.validTo), validToCal);

					predicates.add(tPred);
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}, pageRequest);

	}

	@Override
	public PaymentAccountConfig findLastPaymentConfigByPaymentAccountIdAndPaymentAccountOpenId(Long defaultAccountId, String defaultAccountOpenId) throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PaymentAccountConfig> query = cb.createQuery(PaymentAccountConfig.class);
		final List<Predicate> predicates = new ArrayList<Predicate>();

		// From
		Root<PaymentAccountConfig> root = query.from(PaymentAccountConfig.class);

		// Where
		if (defaultAccountId != null) {
			predicates.add(cb.equal(root.get(PaymentAccountConfig_.defaultAccountId), defaultAccountId));
		}
		if (defaultAccountOpenId != null && StringUtils.isNotBlank(defaultAccountOpenId)) {
			predicates.add(cb.equal(root.get(PaymentAccountConfig_.defaultAccountOpenId), defaultAccountOpenId));
		}

		Calendar DEFAULT_END_DATE = Calendar.getInstance();
		DEFAULT_END_DATE.setTime(SIMPLE_DATE_FORMAT.parse(END_DATE));

		predicates.add(cb.equal(root.get(PaymentAccountConfig_.validityPeriod).get(ValidityPeriod_.validTo), DEFAULT_END_DATE));
		query.where(predicates.toArray(new Predicate[predicates.size()]));

		List<PaymentAccountConfig> lastPaymentAccountConfig = em.createQuery(query).getResultList();

		if (CollectionUtils.isNotEmpty(lastPaymentAccountConfig)) {
			return lastPaymentAccountConfig.get(0);
		}

		return null;
	}
}
