package com.sap.jnc.marketing.persistence.data.executors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.data.factories.EntityFactory;
import com.sap.jnc.marketing.persistence.data.factories.master.MasterDataEntityFactory;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MasterDataInitialExecutor {

	@Autowired
	protected List<MasterDataEntityFactory<?>> masterDataEntityFactories;

	@Transactional
	public void execute() {
		for (EntityFactory<?> entityFactory : masterDataEntityFactories) {
			entityFactory.createdEntities();
		}
	}

	@Transactional
	public void release() throws Exception {
		for (EntityFactory<?> entityFactory : masterDataEntityFactories) {
			entityFactory.close();
		}
	}
}
