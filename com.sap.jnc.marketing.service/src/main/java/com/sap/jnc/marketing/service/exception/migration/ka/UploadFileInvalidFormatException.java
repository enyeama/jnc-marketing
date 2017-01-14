package com.sap.jnc.marketing.service.exception.migration.ka;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import com.sap.jnc.marketing.service.exception.CommonServiceException;

/**
 * Thrown when try resolving file but failed with invalid format
 *
 * @author I071053 Diouf Du
 */
public class UploadFileInvalidFormatException extends CommonServiceException {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -8357376354443619785L;

	protected static final String DEFAULT_DESCRIPTION_TEMPLATE = "Invalid file format, <%s> format is required.";

	/**
	 * Creates a new {@link UploadFileInvalidFormatException} with required format information provided.
	 *
	 * @param requiredFormat
	 *            is the requried file format (for example: csv, doc, txt, image)
	 */
	public UploadFileInvalidFormatException(@Valid @NotBlank String requiredFormat) {
		super(String.format(DEFAULT_DESCRIPTION_TEMPLATE, requiredFormat));
	}

	/**
	 * Creates a new {@link UploadFileInvalidFormatException} with required format information provided
	 *
	 * @param requiredFormat
	 *            is the requried file format (for example: csv, doc, txt, image)
	 * @param ex
	 *            occurs internally on resolve file content
	 */
	public UploadFileInvalidFormatException(@Valid @NotBlank String requiredFormat, Exception ex) {
		super(String.format(DEFAULT_DESCRIPTION_TEMPLATE, requiredFormat), ex);
	}
}
