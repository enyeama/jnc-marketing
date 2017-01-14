package com.sap.jnc.marketing.persistence.exception;

import com.sap.jnc.marketing.infrastructure.exception.CommonInfrastructureException;

public class CommonPersistenceException extends CommonInfrastructureException {
	private static final long serialVersionUID = 1L;

	public CommonPersistenceException(String errorMessage) {
		super(errorMessage);
	}

	public CommonPersistenceException(String errorMessage, Exception ex) {
		super(errorMessage, ex);
	}

	/**
	 * @param e
	 *            the cause of this exception
	 */
	public CommonPersistenceException(final Exception e) {
		super(e);
	}
}
