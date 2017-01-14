package com.sap.jnc.marketing.persistence.exception.database;

import java.util.ArrayList;

import com.sap.jnc.marketing.persistence.exception.CommonPersistenceException;

public class QueryStatementNotExistedException extends CommonPersistenceException {

	private static final long serialVersionUID = -8138195488411520436L;

	protected static final String DEFAULT_DESCRIPTION = "Corresponding SQL statements resource can't be found in specified folder.";

	protected ArrayList<Exception> innerExceptions = new ArrayList<Exception>(1);

	public QueryStatementNotExistedException() {
		super(DEFAULT_DESCRIPTION);
	}

	public QueryStatementNotExistedException(Exception ex) {
		super(DEFAULT_DESCRIPTION, ex);
		innerExceptions.add(ex);
	}

	public QueryStatementNotExistedException(Exception... exceptions) {
		super(DEFAULT_DESCRIPTION);
		for (Exception exception : exceptions) {
			innerExceptions.add(exception);
		}
	}
}
