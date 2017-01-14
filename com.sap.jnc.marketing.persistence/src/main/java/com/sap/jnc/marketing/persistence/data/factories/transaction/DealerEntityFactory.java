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
import com.sap.jnc.marketing.persistence.enumeration.DealerStatus;
import com.sap.jnc.marketing.persistence.model.Contact;
import com.sap.jnc.marketing.persistence.model.Dealer;
import com.sap.jnc.marketing.persistence.repository.DealerRepository;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DealerEntityFactory extends GeneralTransactionalDataEntityFactory<Dealer> implements TransactionalDataEntityFactory<Dealer> {

	@Autowired
	protected DealerRepository dealerRepository;

	@Autowired
	protected ContactEntityFactory contactEntityFactory;

	@Override
	protected void fillEntitiesForInitialCreation(List<Dealer> allCreatedEntities) {
		final List<Contact> contacts = this.contactEntityFactory.createdEntities();
		for (final Contact contact : contacts) {
			allCreatedEntities.add(this.createDealerEntity(contact));
		}
	}

	protected Dealer createDealerEntity(Contact contact) {
		final Dealer dealer = new Dealer();
		// Scalar Fields - Dealer
		dealer.setName(RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(0, 10) + 5));
		dealer.setIsPlatformDealer(RandomUtils.nextInt(0, 3) != 2);
		dealer.setExternalId(String.valueOf(RandomUtils.nextInt(100000, 999999)));
		dealer.setAddress(RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(0, 20) + 20));
		dealer.setStatus(DealerStatus.values()[(RandomUtils.nextInt(0, DealerStatus.values().length))]);
		dealer.setLegalContact(contact);
		// Return
		return dealer;
	}

	@Override
	protected JpaRepository<Dealer, Long> getGeneralJpaRepository() {
		return this.dealerRepository;
	}

	@Override
	protected EntityFactory<?>[] getDependEntityFactories() {
		return new EntityFactory<?>[] { this.contactEntityFactory };
	}
}
