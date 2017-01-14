package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.DepartmentView;
import com.sap.jnc.marketing.persistence.model.DepartmentView_;
import com.sap.jnc.marketing.persistence.model.JobView_;
import com.sap.jnc.marketing.persistence.model.PositionView_;
import com.sap.jnc.marketing.persistence.repository.DepartmentViewRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DepartmentViewRepositoryImpl extends SimpleJpaRepository<DepartmentView, String> implements DepartmentViewRepository {

	private static String EXTERNAL_OFFICE_MANAGER = "25";

	@Autowired
	public DepartmentViewRepositoryImpl(EntityManager em) {
		super(DepartmentView.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DepartmentView> findByIds(Collection<Long> ids) {
		return super.findAll((root, query, cb) -> {
			return root.get(DepartmentView_.id).in(ids);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public DepartmentView findAllById(String id) {
		return super.findOne((root, query, cb) -> {
			query.distinct(true);
			root.fetch(DepartmentView_.positions, JoinType.LEFT);
			return cb.equal(root.get(DepartmentView_.externalId), id);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<DepartmentView> findDepartmentBysuper(String id) {
		return super.findAll((root, query, cb) -> {
			root.fetch(DepartmentView_.superior, JoinType.LEFT);
			return cb.equal(root.join(DepartmentView_.superior).get(DepartmentView_.externalId), id);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public DepartmentView findByDepartmentName(String departmentName) {
		List<DepartmentView> departmentList = super.findAll((root, query, cb) -> {
			return cb.like(root.get(DepartmentView_.name), "%" + departmentName.trim() + "%");
		});
		if (CollectionUtils.isEmpty(departmentList) || departmentList.size() != 1) {
			return null;
		}
		return departmentList.get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public DepartmentView findByPositionId(String positionId) {
		List<DepartmentView> departmentViews = super.findAll((root, query, cb) -> {
			root.fetch(DepartmentView_.positions, JoinType.LEFT);
			return cb.equal(root.join(DepartmentView_.positions).get(PositionView_.externalId), positionId);
		});

		return departmentViews.get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DepartmentView> findAllOffices() {
		List<DepartmentView> departmentViews = super.findAll((root, query, cb) -> {
			return cb.equal(root.join(DepartmentView_.positions).join(PositionView_.job).get(JobView_.externalId), EXTERNAL_OFFICE_MANAGER);
		});

		return departmentViews;
	}

}