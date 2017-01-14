package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.repository.KARepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;


@Repository
public class KARepositoryImpl extends SimpleJpaRepository<Terminal, String> implements KARepository{

	@Autowired
	public KARepositoryImpl(EntityManager em) {
		super(Terminal.class, em);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
