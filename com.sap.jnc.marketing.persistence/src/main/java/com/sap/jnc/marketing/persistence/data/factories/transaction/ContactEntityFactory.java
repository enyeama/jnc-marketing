package com.sap.jnc.marketing.persistence.data.factories.transaction;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.sap.jnc.marketing.persistence.data.factories.EntityFactory;
import com.sap.jnc.marketing.persistence.enumeration.ContactType;
import com.sap.jnc.marketing.persistence.model.Contact;
import com.sap.jnc.marketing.persistence.repository.ContactRepository;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ContactEntityFactory extends GeneralTransactionalDataEntityFactory<Contact> implements TransactionalDataEntityFactory<Contact> {

	@Autowired
	protected ContactRepository dealerRepository;

	@Override
	protected void fillEntitiesForInitialCreation(List<Contact> allCreatedEntities) {
		for (int idx = 0; idx < RandomUtils.nextInt(200, 500); idx++) {
			allCreatedEntities.add(this.createContactEntity());
		}
	}

	protected Contact createContactEntity() {
		final Contact contact = new Contact();
		// Scalar Fields - Contact
		contact.setName(RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(0, 10) + 5));
		contact.setWechat(RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(0, 10) + 5));
		contact.setPhone(String.valueOf(RandomUtils.nextLong(13000000000L, 18900000000L)));
		contact.setPosition(RandomUtils.nextInt(0, 3) == 1 ? "总经理" : "销售总监");
		contact.setType(ContactType.values()[(RandomUtils.nextInt(0, ContactType.values().length))]);
		// Return
		return contact;
	}

	@Override
	protected JpaRepository<Contact, Long> getGeneralJpaRepository() {
		return this.dealerRepository;
	}

	@Override
	protected EntityFactory<?>[] getDependEntityFactories() {
		return new EntityFactory<?>[] {};
	}
}
