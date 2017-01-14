package com.sap.jnc.marketing.api.admin.web.exception;

/**
 * Thrown when spring security could not be configured correctly.
 *
 * @author I071053 Diouf Du
 */
public class SecurityConfigurationFailedException extends Exception {

	private static final long serialVersionUID = 1815771109200706449L;

	public SecurityConfigurationFailedException(final Exception e) {
		super(e);
	}
}
