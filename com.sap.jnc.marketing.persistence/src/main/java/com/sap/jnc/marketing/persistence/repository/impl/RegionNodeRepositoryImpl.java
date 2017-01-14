package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.enumeration.RegionLevel;
import com.sap.jnc.marketing.persistence.model.RegionNode;
import com.sap.jnc.marketing.persistence.model.RegionNode_;
import com.sap.jnc.marketing.persistence.repository.RegionNodeRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RegionNodeRepositoryImpl extends SimpleJpaRepository<RegionNode, Long> implements RegionNodeRepository {

	@Autowired
	public RegionNodeRepositoryImpl(EntityManager em) {
		super(RegionNode.class, em);
	}

	@Override
	public List<RegionNode> findAllByLevel(RegionLevel level) {
		return super.findAll((root, query, cb) -> {
			return cb.and(cb.equal(root.get(RegionNode_.level), level));
		});
	}
}
