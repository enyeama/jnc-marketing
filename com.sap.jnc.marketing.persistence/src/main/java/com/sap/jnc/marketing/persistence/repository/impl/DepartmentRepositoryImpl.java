package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.Department;
import com.sap.jnc.marketing.persistence.model.Department_;
import com.sap.jnc.marketing.persistence.model.ValidityPeriod_;
import com.sap.jnc.marketing.persistence.repository.DepartmentRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DepartmentRepositoryImpl extends SimpleJpaRepository<Department, Long> implements DepartmentRepository {

	private static final String DEPARTMENT_NAME = "办事处";

	@Autowired
	public DepartmentRepositoryImpl(EntityManager em) {
		super(Department.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Department> findAllOfficeByName() {
		final List<Department> results = this.findAll((root, query, cb) -> {
			return cb.like(root.get(Department_.name), "%" + DEPARTMENT_NAME + "%");
		});

		return results;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Department> findAllByExternalIds(List<String> externalIds) {
		return super.findAll((root, query, cb) -> {
			return root.get(Department_.externalId).in(externalIds);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public Department findLatestOneByExternalIds(String externalIds) {
		final Page<Department> deparments = super.findAll((root, query, cb) -> {
			return root.get(Department_.externalId).in(externalIds);
		}, new PageRequest(0, 1, new Sort(new Order(Direction.DESC, Department_.validityPeriod.getName() + "." + ValidityPeriod_.validFrom
			.getName()))));
		if ((deparments != null) && deparments.hasContent()) {
			return deparments.getContent().get(0);
		}
		return null;
	}
}