package com.sap.jnc.marketing.persistence.exception.database;

import com.sap.jnc.marketing.persistence.exception.CommonPersistenceException;

/**
 * SpecifiedFieldNotExistedException can be thrown during the specified field doesn't existed in specific entity, normally it represents the fields in
 * JPA entity.
 *
 * @author I071053 Diouf Du
 */
public class SpecifiedFieldNotExistedException extends CommonPersistenceException {

	private static final long serialVersionUID = 1375012002243123067L;

	/** Default description to give meaningful information for this exception. */
	protected static final String DEFAULT_DESCRIPTION = "The specified field <%s> doesn't existed in specific entity <%s>.";

	/**
	 * Create new instance with specified information.
	 *
	 * @param entityType
	 *            the specific entity type
	 * @param field
	 *            the field name which doesn't existed
	 */
	public SpecifiedFieldNotExistedException(Class<?> entityType, String field) {
		super(String.format(DEFAULT_DESCRIPTION, field, entityType.getSimpleName()));
	}

	/**
	 * Create new instance with specified information and inner exception.
	 *
	 * @param entityType
	 *            the specific entity type
	 * @param field
	 *            the field name which doesn't existed
	 * @param ex
	 *            throws while trying to find the specific field but fail
	 */
	public SpecifiedFieldNotExistedException(Class<?> entityType, String field, Exception ex) {
		super(String.format(DEFAULT_DESCRIPTION, field, entityType.getSimpleName()), ex);
	}
}
