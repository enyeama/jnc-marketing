package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
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
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.criteria.migration.EmployeeAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.model.Employee;
import com.sap.jnc.marketing.persistence.model.Employee_;
import com.sap.jnc.marketing.persistence.repository.EmployeeRepository;

/**
 * @author I071053 Diouf Du
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class EmployeeRepositoryImpl extends SimpleJpaRepository<Employee, Long> implements EmployeeRepository {

	@Autowired
	public EmployeeRepositoryImpl(EntityManager em) {
		super(Employee.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Employee> advanceSearch(EmployeeAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			// Predicates
			final List<Predicate> predicates = new ArrayList<>(4);
			if (StringUtils.isNotBlank(searchCriteria.getExternalId())) {
				predicates.add(cb.like(cb.upper(root.get(Employee_.externalId)),
						"%" + StringUtils.upperCase(searchCriteria.getExternalId()) + "%"));
			}
			if (StringUtils.isNotBlank(searchCriteria.getName())) {
				predicates.add(cb.like(cb.upper(root.get(Employee_.name)),
						"%" + StringUtils.upperCase(searchCriteria.getName()) + "%"));
			}
			if (StringUtils.isNotBlank(searchCriteria.getIdCardNO())) {
				predicates.add(cb.like(cb.upper(root.get(Employee_.idCardNO)),
						"%" + StringUtils.upperCase(searchCriteria.getIdCardNO()) + "%"));
			}
			if (StringUtils.isNotBlank(searchCriteria.getPhone())) {
				predicates.add(cb.like(cb.upper(root.get(Employee_.phone)),
						"%" + StringUtils.upperCase(searchCriteria.getPhone()) + "%"));
			}
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}, pageRequest);
	}

	@Override
	public Collection<Employee> findByIds(Collection<Long> ids) {
		return super.findAll((root, query, cb) -> {
			return root.get(Employee_.id).in(ids);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public String findWechatGroupId(Employee employee) {
		List<Employee> employeeList = super.findAll((root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.isBlank(employee.getExternalId()) || StringUtils.isBlank(employee.getIdCardNO())
					|| StringUtils.isBlank(employee.getName()) || StringUtils.isBlank(employee.getPhone())) {
				throw new RuntimeException("员工注册信息不完整");
			}
			predicates.add(cb.equal(root.get(Employee_.externalId), employee.getExternalId()));
			predicates.add(cb.equal(root.get(Employee_.idCardNO), employee.getIdCardNO()));
			predicates.add(cb.equal(root.get(Employee_.name), employee.getName()));
			predicates.add(cb.equal(root.get(Employee_.phone), employee.getPhone()));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		});

		String wechatGroupId = null;
		if (employeeList.size() > 1) {
			throw new RuntimeException("员工注册信息不唯一");
		} else if (CollectionUtils.isNotEmpty(employeeList)) {
			wechatGroupId = employeeList.get(0).getWechatAccountGroup();
		}

		return wechatGroupId;
	}
}
