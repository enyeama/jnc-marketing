package com.sap.jnc.marketing.service.exception.validation;

import com.sap.jnc.marketing.service.exception.CommonServiceException;

/**
 * Once the entity haven't been found in repository, this exception would be thrown.
 *
 * @author I071053 Diouf Du
 */
public class SpecifiedEntityNotFoundException extends CommonServiceException {

	private static final long serialVersionUID = -7151446830410619459L;

	protected static final String DEFAULT_DESCRIPTION = "Can't find specified entity <%s> by %s %s";

	protected String entityName;

	protected String entityRef;

	public SpecifiedEntityNotFoundException(Class<?> entityClass, int id) {
		this(entityClass, String.valueOf(id));
	}

	public SpecifiedEntityNotFoundException(Class<?> entityClass, long id) {
		this(entityClass, String.valueOf(id));
	}

	public SpecifiedEntityNotFoundException(Class<?> entityClass, String entityRef) {
		this(entityClass, "id", entityRef);
	}

	public SpecifiedEntityNotFoundException(Class<?> entityClass, String missingCriteria, String entityRef) {
		super(String.format(DEFAULT_DESCRIPTION, entityClass.getSimpleName(), missingCriteria, entityRef));
		this.entityName = entityClass.getSimpleName();
		this.entityRef = entityRef;
	}

	public String getEntityName() {
		return this.entityName;
	}

	public String getEntityRef() {
		return this.entityRef;
	}
}
