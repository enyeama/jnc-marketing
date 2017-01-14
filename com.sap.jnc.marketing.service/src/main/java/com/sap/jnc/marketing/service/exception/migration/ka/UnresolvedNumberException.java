package com.sap.jnc.marketing.service.exception.migration.ka;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.apache.commons.lang3.StringUtils;

import com.sap.jnc.marketing.service.exception.CommonServiceException;

/**
 * Thrown when try resolving specific row within Geo Location CSV file
 *
 * @author I071053 Diouf Du
 */
public class UnresolvedNumberException extends CommonServiceException {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -2118339163915049084L;

	protected static final String DEFAULT_DESCRIPTION = "Error on resolving file";

	protected List<Reason> reasons;

	/**
	 * Creates a new {@link UnresolvedNumberException} carrying the unresolved row information.
	 *
	 * @param line
	 *            must not be greater than 0
	 * @param reason
	 *            is the cause which field failed to resolve
	 */
	public UnresolvedNumberException(Reason... reasons) {
		super(DEFAULT_DESCRIPTION);
		this.reasons = Arrays.asList(reasons);
	}

	/**
	 * Creates a new {@link UnresolvedNumberException} carrying the unresolved row information.
	 *
	 * @param ex
	 *            occurs internally on resolve particular fields
	 * @param reason
	 *            is the cause which field failed to resolve
	 * @param line
	 *            must not be greater than 0
	 */
	public UnresolvedNumberException(Exception ex, Reason... reasons) {
		super(DEFAULT_DESCRIPTION, ex);
		this.reasons = Arrays.asList(reasons);
	}

	public List<Reason> getReasons() {
		return this.reasons;
	}

	public static interface Reason {

		/**
		 * @return which line failed to resolve
		 */
		int getLine();

		/**
		 * @return the column name failed to resolve
		 */
		String getColumn();

		/**
		 * @return the code why column failed to resolve
		 */
		String getCode();

		/**
		 * @return the name which failed to resolve
		 */
		String getDescription();
	}

	public static class FieldCountNotMatchReason implements Reason {
		private static final String CODE = "FIELD_COUNT_NOT_MATCH";

		private static final String DESCRIPTION = "Field count doesn't match";

		protected int line;

		public FieldCountNotMatchReason(@Valid @Min(1) int line) {
			this.line = line;
		}

		@Override
		public int getLine() {
			return this.line;
		}

		@Override
		public String getColumn() {
			return StringUtils.EMPTY;
		}

		@Override
		public String getCode() {
			return CODE;
		}

		@Override
		public String getDescription() {
			return FieldCountNotMatchReason.DESCRIPTION;
		}

	}

	public static class FieldMissingReason implements Reason {
		private static final String CODE = "FIELD_MISSING";
		private static final String DESCRIPTION_TEMPLATE = "Field <%s> is missing";

		protected int line;
		protected String column;

		public FieldMissingReason(@Valid @Min(1) int line, String column) {
			this.line = line;
			this.column = column;
		}

		@Override
		public int getLine() {
			return this.line;
		}

		@Override
		public String getColumn() {
			return this.column;
		}

		@Override
		public String getCode() {
			return CODE;
		}

		@Override
		public String getDescription() {
			return String.format(FieldMissingReason.DESCRIPTION_TEMPLATE, this.column);
		}
	}

	public static class InvalidNumberFormatReason implements Reason {
		private static final String CODE = "INVALID_NUMBER_FORMAT";
		private static final String DESCRIPTION_TEMPLATE = "Field <%s> requires number format";

		protected int line;
		protected String column;

		public InvalidNumberFormatReason(@Valid @Min(1) int line, String column) {
			this.line = line;
			this.column = column;
		}

		@Override
		public int getLine() {
			return this.line;
		}

		@Override
		public String getColumn() {
			return this.column;
		}

		@Override
		public String getCode() {
			return CODE;
		}

		@Override
		public String getDescription() {
			return String.format(InvalidNumberFormatReason.DESCRIPTION_TEMPLATE, this.column);
		}
	}
}
