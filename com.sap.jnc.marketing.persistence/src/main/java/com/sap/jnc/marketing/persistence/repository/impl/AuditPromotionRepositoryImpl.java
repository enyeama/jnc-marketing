package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.criteria.audit.AuditTaskSearchCriteria;
import com.sap.jnc.marketing.persistence.model.AuditBanquet_;
import com.sap.jnc.marketing.persistence.model.AuditPromotion;
import com.sap.jnc.marketing.persistence.model.AuditPromotion_;
import com.sap.jnc.marketing.persistence.model.Employee_;
import com.sap.jnc.marketing.persistence.model.ModificatoryEntity_;
import com.sap.jnc.marketing.persistence.model.Region;
import com.sap.jnc.marketing.persistence.model.Region_;
import com.sap.jnc.marketing.persistence.model.TerminalOrder_;
import com.sap.jnc.marketing.persistence.repository.AuditPromotionRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuditPromotionRepositoryImpl extends SimpleJpaRepository<AuditPromotion, Long> implements AuditPromotionRepository {

	@Autowired
	public AuditPromotionRepositoryImpl(EntityManager em) {
		super(AuditPromotion.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AuditPromotion> searchAudits(AuditTaskSearchCriteria criteria, PageRequest pageRequest, List<Region> regionList) {
		final Page<AuditPromotion> list = super.findAll((root, query, cb) -> {
			final List<Predicate> predicate = new ArrayList<>();
			root.fetch(AuditPromotion_.terminalOrder, JoinType.LEFT).fetch(TerminalOrder_.responsibleLeader, JoinType.LEFT);
			root.fetch(AuditPromotion_.terminalOrder, JoinType.LEFT).fetch(TerminalOrder_.product, JoinType.LEFT);
			root.fetch(AuditPromotion_.terminalOrder, JoinType.LEFT).fetch(TerminalOrder_.dealer, JoinType.LEFT);
			root.fetch(AuditPromotion_.terminalOrder, JoinType.LEFT).fetch(TerminalOrder_.terminal, JoinType.LEFT);
			// 省区经理界面
			if (criteria.getQueryType().equals("1")) {
				// 省区经理
				if (criteria.getProvinceManagerId() != null) {
					predicate.add(cb.equal(root.join(AuditPromotion_.provinceManager).get(Employee_.id), criteria.getProvinceManagerId()));
				}
			}
			// 稽核类型
			if (criteria.getType() != null) {
				predicate.add(cb.equal(root.get(AuditPromotion_.type), criteria.getType()));
			}
			// 市管部界面
			if (criteria.getQueryType().equals("2")) {
				// 区域
				if (regionList.size() > 0) {
					List<Long> idsList = regionList.stream().map(region -> region.getId()).collect(Collectors.toList());
					predicate.add(root.join(AuditPromotion_.region).get(Region_.id).in(idsList));
				}
			}
			// 稽核员
			if (criteria.getAuditorId() != null) {
				predicate.add(cb.equal(root.join(AuditPromotion_.auditor).get(Employee_.id), criteria.getAuditorId()));
			}
			// 状态
			if (criteria.getStatus() != null) {
				predicate.add(cb.equal(root.get(AuditPromotion_.status), criteria.getStatus()));
			}
			// 稽核结果
			if (criteria.getResult() != null) {
				predicate.add(cb.equal(root.get(AuditBanquet_.auditResult), criteria.getResult()));
			}
			// 创建时间
			final Calendar createTimeFrom = criteria.getCreateDateFrom();
			final Calendar createTimeTo = criteria.getCreateDateTo();
			if (createTimeFrom != null && createTimeTo == null) {
				predicate.add(cb.greaterThanOrEqualTo(root.get(ModificatoryEntity_.createOn), createTimeFrom));
			}
			if (createTimeFrom == null && createTimeTo != null) {
				predicate.add(cb.lessThanOrEqualTo(root.get(ModificatoryEntity_.createOn), createTimeTo));
			}
			if (createTimeFrom != null && createTimeTo != null) {
				predicate.add(cb.greaterThanOrEqualTo(root.get(ModificatoryEntity_.createOn), createTimeFrom));
				predicate.add(cb.lessThanOrEqualTo(root.get(ModificatoryEntity_.createOn), createTimeTo));
			}
			// 分配时间
			final Calendar assignTimeFrom = criteria.getAssignDateFrom();
			final Calendar assignTimeTo = criteria.getAssignDateTo();
			if (assignTimeFrom != null && assignTimeTo == null) {
				predicate.add(cb.greaterThanOrEqualTo(root.get(AuditPromotion_.assignTime), assignTimeFrom));
			}
			if (assignTimeFrom == null && assignTimeTo != null) {
				predicate.add(cb.lessThanOrEqualTo(root.get(AuditPromotion_.assignTime), assignTimeTo));
			}
			if (assignTimeFrom != null && assignTimeTo != null) {
				predicate.add(cb.greaterThanOrEqualTo(root.get(AuditPromotion_.assignTime), assignTimeFrom));
				predicate.add(cb.lessThanOrEqualTo(root.get(AuditPromotion_.assignTime), assignTimeTo));
			}
			// 稽核时间
			final Calendar auditTimeFrom = criteria.getAuditDateFrom();
			final Calendar auditTimeTo = criteria.getAuditDateTo();
			if (auditTimeFrom != null && auditTimeTo == null) {
				predicate.add(cb.greaterThanOrEqualTo(root.get(AuditPromotion_.auditTime), auditTimeFrom));
			}
			if (auditTimeFrom == null && auditTimeTo != null) {
				predicate.add(cb.lessThanOrEqualTo(root.get(AuditPromotion_.auditTime), auditTimeTo));
			}
			if (auditTimeFrom != null && auditTimeTo != null) {
				predicate.add(cb.greaterThanOrEqualTo(root.get(AuditPromotion_.auditTime), auditTimeFrom));
				predicate.add(cb.lessThanOrEqualTo(root.get(AuditPromotion_.auditTime), auditTimeTo));
			}
			final Predicate[] pre = new Predicate[predicate.size()];
			return cb.and(predicate.toArray(pre));
		}, pageRequest);
		return list;
	}
}