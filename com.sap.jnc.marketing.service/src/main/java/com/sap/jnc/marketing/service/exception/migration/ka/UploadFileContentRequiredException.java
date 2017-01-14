package com.sap.jnc.marketing.service.exception.migration.ka;

import com.sap.jnc.marketing.service.exception.CommonServiceException;

/**
 * Thrown when try resolving file but failed with empty content
 *
 * @author I071053 Diouf Du
 */
public class UploadFileContentRequiredException extends CommonServiceException {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 4318946162301395698L;

	protected static final String DEFAULT_DESCRIPTION = "The upload file is empty.";

	/**
	 * Creates a new {@link UploadFileInvalidFormatException} with default error description
	 */
	public UploadFileContentRequiredException() {
		super(DEFAULT_DESCRIPTION);
	}

	/**
	 * Creates a new {@link UploadFileInvalidFormatException} with default error description
	 *
	 * @param ex
	 *            occurs internally on resolve file content
	 */
	public UploadFileContentRequiredException(Exception ex) {
		super(DEFAULT_DESCRIPTION, ex);
	}
}
