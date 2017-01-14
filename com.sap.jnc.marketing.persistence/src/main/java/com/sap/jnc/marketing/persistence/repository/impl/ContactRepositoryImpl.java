package com.sap.jnc.marketing.persistence.repository.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.model.Contact;
import com.sap.jnc.marketing.persistence.repository.ContactRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ContactRepositoryImpl extends SimpleJpaRepository<Contact, Long> implements ContactRepository {

	@Autowired
	public ContactRepositoryImpl(EntityManager em) {
		super(Contact.class, em);
	}
}