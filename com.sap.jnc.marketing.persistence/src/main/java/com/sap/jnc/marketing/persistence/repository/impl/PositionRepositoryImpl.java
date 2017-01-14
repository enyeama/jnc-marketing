package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.Position;
import com.sap.jnc.marketing.persistence.repository.PositionRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PositionRepositoryImpl extends SimpleJpaRepository<Position, Long> implements PositionRepository {

	@Autowired
	public PositionRepositoryImpl(EntityManager em) {
		super(Position.class, em);
	}
}
