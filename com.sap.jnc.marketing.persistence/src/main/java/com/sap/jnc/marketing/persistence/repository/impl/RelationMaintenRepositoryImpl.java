package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.DepartmentView_;
import com.sap.jnc.marketing.persistence.model.EmployeeView_;
import com.sap.jnc.marketing.persistence.model.JobView_;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.PositionView_;
import com.sap.jnc.marketing.persistence.model.Terminal_;
import com.sap.jnc.marketing.persistence.repository.RelationMaintenRepository;

/**
 * @author Maggie Liu
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RelationMaintenRepositoryImpl extends SimpleJpaRepository<PositionView, String> implements RelationMaintenRepository {

	@Autowired
	public RelationMaintenRepositoryImpl(EntityManager em) {
		super(PositionView.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PositionView> findLeaderByDepartment(String id) {
		return super.findAll((root, query, cb) -> {
			query.distinct(true);
			root.fetch(PositionView_.department, JoinType.LEFT);
			return cb.and(cb.equal(root.join(PositionView_.department).get(DepartmentView_.externalId), id), cb.like(root.get(PositionView_.name),
				"%组长%"));
		});
	}

	@Override
	@Transactional(readOnly = true)
	public PositionView findPositionViewById(String id) {
		return super.findOne((root, query, cb) -> {
			query.distinct(true);
			root.fetch(PositionView_.department, JoinType.LEFT);
			return cb.equal(root.get(PositionView_.externalId), id);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PositionView> findAllDLPRecords(PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			query.distinct(true);
			root.fetch(PositionView_.dealer, JoinType.INNER);
			root.fetch(PositionView_.products, JoinType.LEFT);
			return cb.isNotEmpty(root.get(PositionView_.products));
		}, pageRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PositionView> findAllDLPRecords() {
		return super.findAll((root, query, cb) -> {
			query.distinct(true);
			root.fetch(PositionView_.dealer, JoinType.INNER);
			root.fetch(PositionView_.products, JoinType.INNER);
			return cb.and();
		});
	}

	@Override
	@Transactional(readOnly = true)
	public PositionView findLeaderPositionViewByEmployeeId(String leaderEmployeeId) {
			List<PositionView> salesmen= super.findAll((root, query, cb) -> {
			query.distinct(true);
			root.fetch(PositionView_.employees, JoinType.LEFT);
			root.fetch(PositionView_.job, JoinType.LEFT);
			root.fetch(PositionView_.department, JoinType.LEFT);
			return cb.and(cb.equal(root.join(PositionView_.employees).get(EmployeeView_.externalId), leaderEmployeeId), cb.equal(root.join(
				PositionView_.job).get(JobView_.externalId), "576"));
		});
		return salesmen.get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public PositionView findCitymanagerByDepartment(String departmentId) {
		return super.findOne((root, query, cb) -> {
			query.distinct(true);
			root.fetch(PositionView_.job, JoinType.LEFT);
			root.fetch(PositionView_.department, JoinType.LEFT);
			return cb.and(cb.equal(root.join(PositionView_.department).get(DepartmentView_.externalId), departmentId), cb.equal(root.join(
				PositionView_.job).get(JobView_.externalId), "26"));
		});
	}

	@Override@Transactional(readOnly = true)
	public List<PositionView> findsalesmenByterminal(String terminalId) {
		return super.findAll((root, query, cb) -> {
			query.distinct(true);
			root.fetch(PositionView_.terminals, JoinType.LEFT);
			return cb.and(cb.equal(root.join(PositionView_.terminals).get(Terminal_.externalId), terminalId));
		});
	}

}
