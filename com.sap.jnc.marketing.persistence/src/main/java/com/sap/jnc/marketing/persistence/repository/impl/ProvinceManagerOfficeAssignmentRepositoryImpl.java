package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

import com.sap.jnc.marketing.persistence.criteria.audit.AuditorOfficeAssignmentSeatchCriteria;
import com.sap.jnc.marketing.persistence.model.DepartmentView_;
import com.sap.jnc.marketing.persistence.model.EmployeeView_;
import com.sap.jnc.marketing.persistence.model.JobView_;
import com.sap.jnc.marketing.persistence.model.PositionView_;
import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment;
import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment_;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod_;
import com.sap.jnc.marketing.persistence.repository.ProvinceManagerOfficeAssignmentRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProvinceManagerOfficeAssignmentRepositoryImpl extends SimpleJpaRepository<ProvinceManagerOfficeAssignment, Long> implements ProvinceManagerOfficeAssignmentRepository {

	private static String EXTERNAL_PROVINCE_MANAGER = "503";

	private static String EXTERNAL_AUDITOR = "12";

	@Autowired
	public ProvinceManagerOfficeAssignmentRepositoryImpl(EntityManager em) {
		super(ProvinceManagerOfficeAssignment.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProvinceManagerOfficeAssignment> findProvinceManagerByOfficeId(Long officeId) {
		final List<ProvinceManagerOfficeAssignment> result = super.findAll((root, query, cb) -> {
			return cb.equal(root.join(ProvinceManagerOfficeAssignment_.provinceManager, JoinType.LEFT).join(PositionView_.department).get(
				DepartmentView_.id), officeId);
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public ProvinceManagerOfficeAssignment findOfficeIdByPositionId(Long positionId) {
		final ProvinceManagerOfficeAssignment result = super.findOne((root, query, cb) -> {
			return cb.equal(root.join(ProvinceManagerOfficeAssignment_.provinceManager).get(PositionView_.id), positionId);
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProvinceManagerOfficeAssignment> findAuditorByOfficeId(Long officeId, Long positionId) {
		final List<ProvinceManagerOfficeAssignment> result = super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.join(ProvinceManagerOfficeAssignment_.provinceManager, JoinType.LEFT).join(PositionView_.department).get(
				DepartmentView_.id), officeId), cb.notEqual(root.join(ProvinceManagerOfficeAssignment_.provinceManager).get(PositionView_.id),
					positionId));
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProvinceManagerOfficeAssignment> advanceSearch(AuditorOfficeAssignmentSeatchCriteria criteria, PageRequest pageRequest) {
		final Page<ProvinceManagerOfficeAssignment> list = (Page<ProvinceManagerOfficeAssignment>) super.findAll((root, query, cb) -> {
			final List<Predicate> predicate = new ArrayList<>();
			query.distinct(true);
			root.fetch(ProvinceManagerOfficeAssignment_.provinceManager, JoinType.LEFT);
			root.fetch(ProvinceManagerOfficeAssignment_.office, JoinType.LEFT);
			if (criteria.getAuditorId() != null) {
				predicate.add(cb.equal(root.join(ProvinceManagerOfficeAssignment_.provinceManager, JoinType.LEFT).join(PositionView_.employees).get(
					EmployeeView_.id), criteria.getAuditorId()));
			}
			if (criteria.getJobId() != null) {
				predicate.add(cb.equal(root.join(ProvinceManagerOfficeAssignment_.provinceManager, JoinType.LEFT).join(PositionView_.job,
					JoinType.LEFT).get(JobView_.externalId), criteria.getJobId()));
			}
			if (criteria.getOfficeId() != null) {
				predicate.add(cb.equal(root.join(ProvinceManagerOfficeAssignment_.office, JoinType.LEFT).get(DepartmentView_.id), criteria
					.getOfficeId()));
			}
			final Calendar validFrom = criteria.getValidFrom();
			if (validFrom != null) {
				predicate.add(cb.greaterThanOrEqualTo(root.get(ProvinceManagerOfficeAssignment_.validityPeriod).get(ValidityPeriod_.validFrom),
					validFrom));
			}
			final Predicate[] pre = new Predicate[predicate.size()];
			return cb.and(predicate.toArray(pre));
		}, pageRequest);
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProvinceManagerOfficeAssignment> findOneByPositionId(String officeExternalId, String positionExternalId, Calendar validFrom,
		Calendar validTo, boolean isManager) {
		final List<ProvinceManagerOfficeAssignment> result = super.findAll((root, query, cb) -> {
			final List<Predicate> predicate = new ArrayList<>();
			query.distinct(true);
			if (isManager) {
				predicate.add(cb.equal(root.join(ProvinceManagerOfficeAssignment_.provinceManager, JoinType.LEFT).join(PositionView_.job).get(
					JobView_.externalId), EXTERNAL_PROVINCE_MANAGER));
			}
			else {
				predicate.add(cb.equal(root.join(ProvinceManagerOfficeAssignment_.provinceManager, JoinType.LEFT).join(PositionView_.job).get(
					JobView_.externalId), EXTERNAL_AUDITOR));
				predicate.add(cb.equal(root.join(ProvinceManagerOfficeAssignment_.provinceManager, JoinType.LEFT).get(PositionView_.externalId),
					positionExternalId));
			}
			predicate.add(cb.equal(root.join(ProvinceManagerOfficeAssignment_.office, JoinType.LEFT).get(DepartmentView_.externalId),
				officeExternalId));
			predicate.add(cb.or(cb.and(cb.greaterThan(root.get(ProvinceManagerOfficeAssignment_.validityPeriod).get(ValidityPeriod_.validFrom),
				validFrom), cb.lessThan(root.get(ProvinceManagerOfficeAssignment_.validityPeriod).get(ValidityPeriod_.validFrom), validTo)), cb.and(cb
					.lessThan(root.get(ProvinceManagerOfficeAssignment_.validityPeriod).get(ValidityPeriod_.validFrom), validFrom), cb.greaterThan(
						root.get(ProvinceManagerOfficeAssignment_.validityPeriod).get(ValidityPeriod_.validTo), validTo)), cb.and(cb.greaterThan(root
							.get(ProvinceManagerOfficeAssignment_.validityPeriod).get(ValidityPeriod_.validTo), validFrom), cb.lessThan(root.get(
								ProvinceManagerOfficeAssignment_.validityPeriod).get(ValidityPeriod_.validTo), validTo))));
			final Predicate[] pre = new Predicate[predicate.size()];
			return cb.and(predicate.toArray(pre));
		});
		return result;
	}

}
