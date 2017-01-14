package com.sap.jnc.marketing.persistence.exception.database;

import com.sap.jnc.marketing.persistence.exception.CommonPersistenceException;

public class DataSourceConnectionException extends CommonPersistenceException {

	private static final long serialVersionUID = 1502404259606162165L;

	protected static final String DEFAULT_DESCRIPTION = "Can't connect specific data source, please refer to inner exception.";

	public DataSourceConnectionException() {
		super(DEFAULT_DESCRIPTION);
	}

	public DataSourceConnectionException(Exception ex) {
		super(DEFAULT_DESCRIPTION, ex);
	}
}
