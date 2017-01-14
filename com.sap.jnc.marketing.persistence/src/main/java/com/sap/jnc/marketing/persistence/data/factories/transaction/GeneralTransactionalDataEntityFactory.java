package com.sap.jnc.marketing.persistence.data.factories.transaction;

import com.sap.jnc.marketing.persistence.data.factories.GeneralEntityFactory;

public abstract class GeneralTransactionalDataEntityFactory<TEntity> extends GeneralEntityFactory<TEntity> implements TransactionalDataEntityFactory<TEntity> {

}
