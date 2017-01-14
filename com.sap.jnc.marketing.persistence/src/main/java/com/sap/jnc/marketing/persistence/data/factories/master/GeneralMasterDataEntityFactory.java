package com.sap.jnc.marketing.persistence.data.factories.master;

import com.sap.jnc.marketing.persistence.data.factories.EntityFactory;
import com.sap.jnc.marketing.persistence.data.factories.GeneralEntityFactory;

public abstract class GeneralMasterDataEntityFactory<TEntity> extends GeneralEntityFactory<TEntity> implements MasterDataEntityFactory<TEntity> {

	@Override
	protected EntityFactory<?>[] getDependEntityFactories() {
		return EMPTY_DEPEND_ENTITY_FACTORIES;
	}
}
