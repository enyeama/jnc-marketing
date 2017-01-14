package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.IndividualProduct;
import com.sap.jnc.marketing.persistence.model.IndividualProduct_;
import com.sap.jnc.marketing.persistence.repository.IndividualProductRepository;

/**
 * @author I324442 Sha Liu
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class IndividualProductRepositoryImpl extends SimpleJpaRepository<IndividualProduct, String> implements IndividualProductRepository {

	@Autowired
	public IndividualProductRepositoryImpl(EntityManager em) {
		super(IndividualProduct.class, em);
	}

	@Override
	public List<IndividualProduct> findByCaseID(String caseID) {
		return super.findAll((root, query, cb) -> {
			return cb.equal(root.get(IndividualProduct_.caseId), caseID);
		});
	}

	@Override
	public List<IndividualProduct> findAllByCaseId(String scanCode) {
		return super.findAll((root, query, cb) -> {
			root.fetch(IndividualProduct_.product, JoinType.LEFT);
			root.fetch(IndividualProduct_.banquetItem, JoinType.LEFT);
			return cb.equal(root.get(IndividualProduct_.caseId), scanCode);
		});
	}

	@Override
	public IndividualProduct findByBoxId(String scanCode) {
		return super.findOne((root, query, cb) -> {
			root.fetch(IndividualProduct_.product, JoinType.LEFT);
			root.fetch(IndividualProduct_.banquetItem, JoinType.LEFT);
			return cb.equal(root.get(IndividualProduct_.boxId), scanCode);
		});
	}

	@Override
	public List<IndividualProduct> findAllByBoxId(List<String> boxIdList) {
		return super.findAll((root, query, cb) -> {
			root.fetch(IndividualProduct_.product, JoinType.LEFT);
			root.fetch(IndividualProduct_.banquetItem, JoinType.LEFT);
			In<String> inParams = cb.in(root.get(IndividualProduct_.boxId));
			for (String boxId : boxIdList) {
				inParams.value(boxId);
			}
			return inParams;
		});
	}

	@Override
	public List<IndividualProduct> findByBoxIDorCaseID(String id) {
		return super.findAll(new Specification<IndividualProduct>() {
			@Override
			public Predicate toPredicate(final javax.persistence.criteria.Root<IndividualProduct> root,
				final javax.persistence.criteria.CriteriaQuery<?> query, final CriteriaBuilder cb) {
				query.distinct(true);
				return cb.or(cb.equal(root.get(IndividualProduct_.boxId), id), cb.equal(root.get(IndividualProduct_.caseId), id));
			}
		});
	}

	@Override
	public List<IndividualProduct> findByBoxID(String boxID) {
		return super.findAll((root, query, cb) -> {
			return cb.equal(root.get(IndividualProduct_.boxId), boxID);
		});
	}

	@Override
	public List<IndividualProduct> findByBottleOID(String bottleOID) {
		return super.findAll((root, query, cb) -> {
			return cb.equal(root.get(IndividualProduct_.capOuterCode), bottleOID);
		});
	}

	@Override
	public List<IndividualProduct> findByCapInnerCodes(Collection<String> capInnerIds) {
		return super.findAll((root, query, cb) -> {
			root.fetch(IndividualProduct_.bonuses, JoinType.LEFT);
			return root.get(IndividualProduct_.capInnerCode).in(capInnerIds);
		});
	}

	@Override
	public List<IndividualProduct> findByBoxIds(Collection<String> boxIds) {
		return super.findAll((root, query, cb) -> {
			return root.get(IndividualProduct_.boxId).in(boxIds);
		});
	}

	@Override
	public List<IndividualProduct> findByCapOuterCodes(Collection<String> capOuterCodes) {
		return super.findAll((root, query, cb) -> {
			return root.get(IndividualProduct_.capOuterCode).in(capOuterCodes);
		});
	}

	@Override
	public List<IndividualProduct> findByCaseIds(Collection<String> CaseIds) {
		return super.findAll((root, query, cb) -> {
			return root.get(IndividualProduct_.caseId).in(CaseIds);
		});
	}
}
