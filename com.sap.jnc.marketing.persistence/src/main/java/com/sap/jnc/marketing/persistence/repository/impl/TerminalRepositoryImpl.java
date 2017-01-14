package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
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

import com.sap.jnc.marketing.persistence.criteria.ka.KAAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.criteria.ka.KAExportCriteriaRequest;
import com.sap.jnc.marketing.persistence.enumeration.TerminalStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalType;
import com.sap.jnc.marketing.persistence.model.DepartmentView_;
import com.sap.jnc.marketing.persistence.model.EmployeeView_;
import com.sap.jnc.marketing.persistence.model.PositionView_;
import com.sap.jnc.marketing.persistence.model.Region_;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.model.Terminal_;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TerminalRepositoryImpl extends SimpleJpaRepository<Terminal, Long> implements TerminalRepository {

	@Autowired
	public TerminalRepositoryImpl(EntityManager em) {
		super(Terminal.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public Terminal findTerminalById(Long id) {
		final Terminal result = super.findOne((root, query, cb) -> {
			query.distinct(true);
			root.fetch(Terminal_.salesmen, JoinType.LEFT);
			root.fetch(Terminal_.region, JoinType.LEFT);
			return cb.equal(root.get(Terminal_.id), id);
		});
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Terminal> findAllWithNoUserReference(PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			return cb.isEmpty(root.get(Terminal_.userReferences));
		} , pageRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Terminal> findBySalesman(String salePosExtId, String titleKey) {
		return super.findAll((root, query, cb) -> {
			query.distinct(true);
			root.fetch(Terminal_.channel, JoinType.LEFT);
			if (StringUtils.isBlank(titleKey)) {
				return cb.and(cb.notEqual(root.get(Terminal_.type), TerminalType.KA),
					cb.equal(root.join(Terminal_.salesmen, JoinType.LEFT).get(PositionView_.externalId), salePosExtId));
			}
			else {
				return cb.and(cb.notEqual(root.get(Terminal_.type), TerminalType.KA),
					cb.like(root.get(Terminal_.title), titleKey + "%"),
					cb.equal(root.join(Terminal_.salesmen, JoinType.LEFT).get(PositionView_.externalId), salePosExtId));
			}
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Terminal> findByCreater(String createrId, String titleKey) {
		return super.findAll((root, query, cb) -> {
			root.fetch(Terminal_.channel);
			if (StringUtils.isBlank(titleKey)) {
				return cb.equal(root.get(Terminal_.creationClerk), createrId);
			}
			else {
				return cb.and(cb.equal(root.get(Terminal_.creationClerk), createrId), cb.like(root.get(Terminal_.title), titleKey + "%"));
			}
		});
	}

	@Override
	public List<Terminal> findByKASpecialExternalId(String externalId) {
		return super.findAll((root, query, cb) -> {
			root.fetch(Terminal_.KASpecialist, JoinType.LEFT);
			return cb.and(cb.equal(root.get(Terminal_.KASpecialist).get(PositionView_.externalId), externalId), cb.equal(root.get(Terminal_.type),
				TerminalType.KA));
		});
	}

	@Override
	public List<Terminal> findByKAName(String kaSpecialExternalId, String kaName) {
		return super.findAll((root, query, cb) -> {
			root.fetch(Terminal_.KAOffice, JoinType.LEFT);
			root.fetch(Terminal_.maintainer, JoinType.LEFT);
			root.fetch(Terminal_.KASpecialist, JoinType.LEFT);
			return cb.and(cb.equal(root.get(Terminal_.branchName), kaName), cb.equal(root.get(Terminal_.KASpecialist).get(PositionView_.externalId),
				kaSpecialExternalId));
		});
	}

	@Override
	public Page<Terminal> advanceKASearch(KAAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			final Long id = searchCriteria.getId();
			final String branchName = searchCriteria.getBranchName();
			final Long kaOfficeId = searchCriteria.getKaOfficeId();
			final String kaSystemNumber = searchCriteria.getKaSystemNumber();
			final String storeNumber = searchCriteria.getStoreNumber();
			final List<Predicate> predicateList = new ArrayList<>();
			predicateList.add(cb.equal(root.get(Terminal_.type), TerminalType.KA));
			predicateList.add(cb.notEqual(root.get(Terminal_.status), TerminalStatus.INACTIVE));
			if ((id != null) && (id.longValue() >= 1)) {
				predicateList.add(cb.equal(root.get(Terminal_.id), id));
			}
			if (!StringUtils.isEmpty(branchName)) {
				predicateList.add(cb.like(cb.upper(root.get(Terminal_.branchName)), "%" + branchName.trim().toUpperCase() + "%"));
			}
			if ((kaOfficeId != null) && (kaOfficeId.longValue() >= 1)) {
				root.fetch(Terminal_.KAOffice, JoinType.LEFT);
				predicateList.add(cb.equal(root.join(Terminal_.KAOffice).get(DepartmentView_.id), kaOfficeId));
			}
			if (!StringUtils.isEmpty(kaSystemNumber)) {
				predicateList.add(cb.equal(cb.upper(root.get(Terminal_.KASystemNumber)), kaSystemNumber.trim().toUpperCase()));
			}
			if (!StringUtils.isEmpty(storeNumber)) {
				predicateList.add(cb.equal(cb.upper(root.get(Terminal_.storeNumber)), storeNumber.trim().toUpperCase()));
			}
			if (CollectionUtils.isEmpty(predicateList)) {
				return null;
			}
			else if (predicateList.size() == 1) {
				return predicateList.get(0);
			}
			else {
				return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
			}
		} , pageRequest);
	}

	@Override
	public List<Terminal> findByIds(Collection<Long> ids) {
		return super.findAll((root, query, cb) -> {
			return root.get(Terminal_.id).in(ids);
		});
	}

	@Override
	public List<Terminal> findByExternalIds(Collection<String> externalIds) {
		return super.findAll((root, query, cb) -> {
			return root.get(Terminal_.externalId).in(externalIds);
		});
	}

	@Override
	public List<Terminal> findAllKAs(KAExportCriteriaRequest criteria) {
		return super.findAll((root, query, cb) -> {
			final List<Long> terminalIds = criteria.getTerminalIds();
			final List<Predicate> predicateList = new ArrayList<>();
			predicateList.add(cb.equal(root.get(Terminal_.type), TerminalType.KA));
			predicateList.add(cb.equal(root.get(Terminal_.status), TerminalStatus.ACTIVE));
			query.orderBy(cb.asc(root.get(Terminal_.id)));
			if (!CollectionUtils.isEmpty(terminalIds)) {
				predicateList.add(root.get(Terminal_.id).in(terminalIds));
			}
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		});
	}

	@Override
	public List<Terminal> findTerminalsByCityManager(String cityId) {
		return super.findAll((root, query, cb) -> {
			root.fetch(Terminal_.region, JoinType.LEFT);
			return cb.and(cb.equal(root.get(Terminal_.region).get(Region_.cityId), cityId), cb.notEqual(root.get(Terminal_.type), TerminalType.KA));
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Terminal> findByKaSales(String salesManId, String titleKey) {
		return super.findAll((root, query, cb) -> {
			query.distinct(true);
			root.fetch(Terminal_.channel, JoinType.LEFT);
			if (StringUtils.isBlank(titleKey)) {
				return cb.and(cb.equal(root.get(Terminal_.type), TerminalType.KA), cb.equal(root.join(Terminal_.salesmen, JoinType.LEFT).join(
					PositionView_.employees, JoinType.LEFT).get(EmployeeView_.externalId), salesManId));
			}
			else {
				return cb.and(cb.equal(root.get(Terminal_.type), TerminalType.KA), cb.like(root.get(Terminal_.title), titleKey + "%"), cb.equal(root
					.join(Terminal_.salesmen, JoinType.LEFT).join(PositionView_.employees, JoinType.LEFT).get(EmployeeView_.externalId), salesManId));
			}
		});
	}

	@Override
	public List<Terminal> findAllByOrganizationCode(String organizationCode) {
		return super.findAll((root, query, cb) -> {
			return cb.equal(root.get(Terminal_.organizationCode), organizationCode);
		});

	}
}
