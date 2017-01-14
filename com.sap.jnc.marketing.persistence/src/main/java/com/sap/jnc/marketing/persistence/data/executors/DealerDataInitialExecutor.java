package com.sap.jnc.marketing.persistence.data.executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.data.factories.transaction.DealerEntityFactory;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DealerDataInitialExecutor {

	@Autowired
	private DealerEntityFactory dealerEntityFactory;

	@Transactional
	public void execute() {
		if (!this.dealerEntityFactory.doesDataExist()) {
			this.dealerEntityFactory.createdEntities();
		}

	}

	@Transactional
	public void release() throws Exception {
		if (!this.dealerEntityFactory.doesDataExist()) {
			this.dealerEntityFactory.close();
		}
	}
}
