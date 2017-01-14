package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.EmployeePositionAssignment;
import com.sap.jnc.marketing.persistence.model.EmployeePositionAssignment_;
import com.sap.jnc.marketing.persistence.model.ProvinceManagerOfficeAssignment;
import com.sap.jnc.marketing.persistence.repository.EmployeePositionAssignmentRepository;

/**
 * @author I071053 Diouf Du
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class EmployeePositionAssignmentRepositoryImpl extends SimpleJpaRepository<EmployeePositionAssignment, Long> implements EmployeePositionAssignmentRepository {

	@Autowired
	public EmployeePositionAssignmentRepositoryImpl(EntityManager em) {
		super(EmployeePositionAssignment.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public EmployeePositionAssignment findOneByPostionId(Long provinceManagerPostionId) {
		return super.findOne((root, query, cb) -> {
			return cb.equal(root.get(EmployeePositionAssignment_.positionExternalId), provinceManagerPostionId);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public EmployeePositionAssignment findOneByEmpoyeeId(Long provinceManagerId) {
		return super.findOne((root, query, cb) -> {
			return cb.equal(root.get(EmployeePositionAssignment_.employeeExternalId), provinceManagerId);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmployeePositionAssignment> findAllByPostions(List<ProvinceManagerOfficeAssignment> auditorResult) {
		return super.findAll((root, query, cb) -> {
			final Path<String> path = root.get(EmployeePositionAssignment_.positionExternalId);
			List<String> idsList = new ArrayList<String>();
			for(ProvinceManagerOfficeAssignment item:auditorResult){
				idsList.add(String.valueOf(item.getProvinceManager().getId()));
			}
			return path.in(idsList);
		});
	}

}
