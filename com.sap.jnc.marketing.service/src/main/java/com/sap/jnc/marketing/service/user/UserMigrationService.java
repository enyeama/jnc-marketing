package com.sap.jnc.marketing.service.user;

import java.io.Serializable;

public interface UserMigrationService<TEntity extends Serializable> {

	void createUsers();

}
