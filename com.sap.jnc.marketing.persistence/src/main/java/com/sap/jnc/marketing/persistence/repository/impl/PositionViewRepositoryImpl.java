package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.DepartmentView_;
import com.sap.jnc.marketing.persistence.model.Department_;
import com.sap.jnc.marketing.persistence.model.EmployeeView;
import com.sap.jnc.marketing.persistence.model.EmployeeView_;
import com.sap.jnc.marketing.persistence.model.JobView_;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.PositionView_;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;

/**
 * @author I071053 Diouf Du
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class PositionViewRepositoryImpl extends SimpleJpaRepository<PositionView, String> implements PositionViewRepository {

	private static final int SALES_MAN_ID = 50;
	private static final String CITY_MANAGER_EXTERNAL_ID = "30";

	@Autowired
	public PositionViewRepositoryImpl(EntityManager em) {
		super(PositionView.class, em);
	}

	@Override
	public List<PositionView> findByIds(Collection<Long> ids) {
		return super.findAll((root, query, cb) -> {
			return root.get(PositionView_.id).in(ids);
		});
	}

	@Override
	public List<PositionView> findByExternalIds(Collection<String> externalIds) {
		return super.findAll((root, query, cb) -> {
			return root.get(PositionView_.externalId).in(externalIds);
		});
	}

	@Override
	public List<PositionView> findAllSalesManByDepartmentId(Long departmentId) {
		return super.findAll((root, query, cb) -> {
			return cb.equal(root.get(PositionView_.id), 910);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public PositionView findOneByPositionId(String id) {
		return super.findOne((root, query, cb) -> {
			query.distinct(true);
			root.fetch(PositionView_.office, JoinType.LEFT);
			return cb.equal(root.get(PositionView_.externalId), id);
		});
	}

	@Override
	public List<PositionView> findPositionViewByexternalIds(Collection<String> positionIdSet) {
		return super.findAll((root, query, cb) -> {
			return root.get(PositionView_.externalId).in(positionIdSet);
		});
	}

	@Override
	public Collection<PositionView> findAllCityManagers() {
		return super.findAll((root, query, cb) -> {
			query.distinct(true);
			Join jobJoin = root.join(PositionView_.job);
			return cb.equal(jobJoin.get(JobView_.externalId), CITY_MANAGER_EXTERNAL_ID);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public PositionView findLeadersBySalesId(String saleExtId) {
		return super.findOne((root, query, cb) -> {
			query.distinct(true);
			Subquery<String> subquery = query.subquery(String.class);
			Root<EmployeeView> subRoot = subquery.from(EmployeeView.class);
			subquery.distinct(true);
			subquery.select(subRoot.join(EmployeeView_.positions, JoinType.LEFT).join(PositionView_.department, JoinType.LEFT).join(
				DepartmentView_.department, JoinType.LEFT).get(Department_.externalId));
			subquery.where(cb.equal(subRoot.get(EmployeeView_.externalId), saleExtId));
			return cb.and(cb.equal(root.get(PositionView_.isHead), true), cb.in(root.join(PositionView_.department, JoinType.LEFT).join(
				DepartmentView_.department).get(Department_.externalId)).value(subquery));
		});
	}

}
