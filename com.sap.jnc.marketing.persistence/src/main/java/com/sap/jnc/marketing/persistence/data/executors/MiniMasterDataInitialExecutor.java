package com.sap.jnc.marketing.persistence.data.executors;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.data.factories.EntityFactory;
import com.sap.jnc.marketing.persistence.data.factories.master.MasterDataEntityFactory;
import com.sap.jnc.marketing.persistence.data.factories.master.RoleAndPrivilegeEntityFactory;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MiniMasterDataInitialExecutor {

	@Autowired
	private RoleAndPrivilegeEntityFactory rolePrivilegeEntityFactory;

	protected List<MasterDataEntityFactory<?>> masterDataEntityFactories;

	@Transactional
	@PostConstruct
	public void execute() {
		this.initialEntityFactories();
		for (final EntityFactory<?> entityFactory : this.masterDataEntityFactories) {
			if (!entityFactory.doesDataExist()) {
				entityFactory.createdEntities();
			}
		}
	}

	@Transactional
	public void release() throws Exception {
		for (final EntityFactory<?> entityFactory : this.masterDataEntityFactories) {
			if (!entityFactory.doesDataExist()) {
				entityFactory.close();
			}
		}
	}

	private void initialEntityFactories() {
		this.masterDataEntityFactories = new ArrayList<MasterDataEntityFactory<?>>(3);
		this.masterDataEntityFactories.add(this.rolePrivilegeEntityFactory);
	}
}
