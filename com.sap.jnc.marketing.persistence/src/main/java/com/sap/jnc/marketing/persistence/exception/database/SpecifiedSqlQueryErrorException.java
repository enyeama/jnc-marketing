package com.sap.jnc.marketing.persistence.exception.database;

import java.util.ArrayList;

import com.sap.jnc.marketing.persistence.exception.CommonPersistenceException;

public class SpecifiedSqlQueryErrorException extends CommonPersistenceException {

	private static final long serialVersionUID = -3735124005103586234L;

	protected static final String DEFAULT_DESCRIPTION = "Specified SQL statement can't be executed on connected database.";

	protected ArrayList<Exception> innerExceptions = new ArrayList<Exception>(1);

	public SpecifiedSqlQueryErrorException() {
		super(DEFAULT_DESCRIPTION);
	}

	public SpecifiedSqlQueryErrorException(Exception ex) {
		super(DEFAULT_DESCRIPTION, ex);
		innerExceptions.add(ex);
	}

	public SpecifiedSqlQueryErrorException(Exception... exceptions) {
		super(DEFAULT_DESCRIPTION);
		for (Exception exception : exceptions) {
			innerExceptions.add(exception);
		}
	}
}
