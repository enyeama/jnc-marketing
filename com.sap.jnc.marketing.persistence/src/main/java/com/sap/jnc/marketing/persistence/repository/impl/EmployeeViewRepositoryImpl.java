package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.apache.commons.collections.CollectionUtils;
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
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.EmployeeView_;
import com.sap.jnc.marketing.persistence.model.Employee_;
import com.sap.jnc.marketing.persistence.model.JobView_;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.PositionView_;
import com.sap.jnc.marketing.persistence.repository.EmployeeViewRepository;

/**
 * @author I071053 Diouf Du
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class EmployeeViewRepositoryImpl extends SimpleJpaRepository<EmployeeView, String> implements EmployeeViewRepository {

	private static String EXTERNAL_MANAGER = "503";

	private static String EXTERNAL_AUDITOR = "12";

	private static String AUDITOR_DEPARTMENT = "1083";

	@Autowired
	public EmployeeViewRepositoryImpl(EntityManager em) {
		super(EmployeeView.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<EmployeeView> advanceSearch(AuditorOfficeAssignmentSeatchCriteria criteria, PageRequest pageRequest) {
		final Page<EmployeeView> list = super.findAll((root, query, cb) -> {
			final List<Predicate> predicate = new ArrayList<>();
			query.distinct(true);
			root.fetch(EmployeeView_.positions, JoinType.LEFT).fetch(PositionView_.job, JoinType.LEFT);
			final Path<String> path = root.join(EmployeeView_.positions, JoinType.LEFT).join(PositionView_.job, JoinType.LEFT).get(
				JobView_.externalId);
			final List<String> idsList = new ArrayList<String>();
			idsList.add(EXTERNAL_MANAGER);
			idsList.add(EXTERNAL_AUDITOR);
			predicate.add(path.in(idsList));
			if (criteria.getAuditorId() != null) {
				predicate.add(cb.equal(root.get(EmployeeView_.id), criteria.getAuditorId()));
			}
			if (criteria.getJobId() != null) {
				predicate.add(cb.equal(root.join(EmployeeView_.positions, JoinType.LEFT).join(PositionView_.job, JoinType.LEFT).get(
					JobView_.externalId), criteria.getJobId()));
			}
			final Predicate[] pre = new Predicate[predicate.size()];
			return cb.and(predicate.toArray(pre));
		}, pageRequest);
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmployeeView> findAllByIds(List<PositionView> empPosResult) {
		return super.findAll((root, query, cb) -> {
			query.distinct(true);
			root.fetch(EmployeeView_.positions, JoinType.LEFT);
			final Path<String> path = root.join(EmployeeView_.positions, JoinType.LEFT).get(PositionView_.externalId);
			final List<String> idsList = new ArrayList<String>();
			for (final PositionView item : empPosResult) {
				idsList.add(item.getExternalId());
			}
			return path.in(idsList);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Page<EmployeeView> findAllWithNoUserReference(PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			// Fetch
			query.distinct(true);
			final Fetch<EmployeeView, Employee> fetchEmployee = root.fetch(EmployeeView_.employee, JoinType.LEFT);
			fetchEmployee.fetch(Employee_.createBy);
			fetchEmployee.fetch(Employee_.updateBy);
			root.fetch(EmployeeView_.productSalesCategory, JoinType.LEFT);
			// Filter
			return cb.isEmpty(root.get(EmployeeView_.userReferences));
		}, pageRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public EmployeeView findOneById(Long id) {
		final EmployeeView result = super.findOne((root, query, cb) -> {
			query.distinct(true);
			root.fetch(EmployeeView_.positions, JoinType.LEFT);
			return cb.equal(root.get(EmployeeView_.id), id);
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public EmployeeView findOneByExternalId(String externalId) {
		final EmployeeView result = super.findOne((root, query, cb) -> {
			query.distinct(true);
			root.fetch(EmployeeView_.positions, JoinType.LEFT);
			return cb.equal(root.get(EmployeeView_.externalId), externalId);
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmployeeView> findAllAuditors() {
		return super.findAll((root, query, cb) -> {
			query.distinct(true);
			root.fetch(EmployeeView_.positions, JoinType.LEFT);
			return cb.equal(root.join(EmployeeView_.positions, JoinType.LEFT).join(PositionView_.department).get(DepartmentView_.externalId),
				AUDITOR_DEPARTMENT);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public PositionView findPositionByEmployeeIdAndJobType(Long employeeId, String jobExternalId) {
		if ((employeeId == null) || (employeeId.longValue() < 1)) {
			return null;
		}
		final EmployeeView employeeView = this.findOneById(employeeId);
		if (employeeView == null) {
			return null;
		}
		final List<PositionView> positionViewList = employeeView.getPositions();
		if (CollectionUtils.isEmpty(positionViewList)) {
			return null;
		}
		for (final PositionView positionView : positionViewList) {
			if (jobExternalId.equals(positionView.getExternalId())) {
				return positionView;
			}
		}
		return null;
	}
}
