package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.sap.jnc.marketing.persistence.model.Banquet;
import com.sap.jnc.marketing.persistence.model.Banquet_;
import com.sap.jnc.marketing.persistence.model.Department_;
import com.sap.jnc.marketing.persistence.model.Employee_;
import com.sap.jnc.marketing.persistence.criteria.banquet.BanquetSearchKeywordNode;
import com.sap.jnc.marketing.persistence.repository.BanquetRepository;

/**
 * @author I332242 Zhu Qiang
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BanquetRepositoryImpl extends SimpleJpaRepository<Banquet, Long> implements BanquetRepository {

	@Autowired
	public BanquetRepositoryImpl(EntityManager em) {
		super(Banquet.class, em);
	}
	

	@Override
	@Transactional(readOnly=true)
	public Page<Banquet> advanceSearch(BanquetSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return super.findAll((root, query, cb) -> {
			// Fetches
			query.distinct(true);
			root.fetch(Banquet_.cityManager, JoinType.LEFT);
			root.fetch(Banquet_.officeDirector, JoinType.LEFT);
			root.fetch(Banquet_.dealer, JoinType.LEFT);
			root.fetch(Banquet_.terminal, JoinType.LEFT);
			root.fetch(Banquet_.product, JoinType.LEFT);
			root.fetch(Banquet_.creator, JoinType.LEFT);
			root.fetch(Banquet_.handler, JoinType.LEFT);
			root.fetch(Banquet_.office, JoinType.LEFT);
			// Predicates
			final List<Predicate> predicates = new ArrayList<Predicate>();
			if (searchCriteria != null) {
				if (searchCriteria.getId() != null) {
					predicates.add(cb.equal(root.get(Banquet_.id), searchCriteria.getId()));
				}
				if (null != searchCriteria.getCreatorId()) {
					predicates.add(cb.equal(root.join(Banquet_.creator, JoinType.LEFT).get(Employee_.id), searchCriteria.getCreatorId()));
				}
				if (searchCriteria.getStatus() != null) {
					predicates.add(cb.equal(root.get(Banquet_.status), searchCriteria.getStatus()));
				}
				if (StringUtils.isNotBlank(searchCriteria.getOfficeName())) {
					predicates.add(cb.equal(cb.upper(root.join(Banquet_.office,JoinType.LEFT).get(Department_.name)), StringUtils.upperCase(searchCriteria.getOfficeName())));
				}
			}
			
			Predicate p1 = null;
			final Calendar banquetTimeFrom = searchCriteria.getBanquetTimeFrom();
			final Calendar banquetTimeTo = searchCriteria.getBanquetTimeTo();
			if (banquetTimeFrom != null && banquetTimeTo != null) {
				p1 = cb.between(root.get(Banquet_.banquetTime), banquetTimeFrom, banquetTimeTo);
			} else if (banquetTimeFrom != null && banquetTimeTo == null) {
				p1 = cb.greaterThan(root.get(Banquet_.banquetTime), banquetTimeFrom);
			} else if (banquetTimeFrom == null && banquetTimeTo != null) {
				p1 = cb.lessThan(root.get(Banquet_.banquetTime), banquetTimeTo);
			}
			if (p1 != null) {
				predicates.add(p1);
			}
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}, pageRequest);
	}
}
