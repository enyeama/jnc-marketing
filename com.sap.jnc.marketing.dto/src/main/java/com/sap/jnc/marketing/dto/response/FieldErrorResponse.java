package com.sap.jnc.marketing.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FieldErrorResponse implements Serializable {

	private static final long serialVersionUID = 91202497558259454L;

	private List<FieldErrorBodyItems> error;

	public List<FieldErrorBodyItems> getError() {
		return error;
	}

	public void setError(List<FieldErrorBodyItems> error) {
		this.error = error;
	}

	public class FieldErrorBodyItems implements Serializable {
		private static final long serialVersionUID = 4486795409832623805L;

		@JsonProperty("item")
		protected int itemId;

		@JsonProperty("field")
		protected String field;

		@JsonProperty("value")
		protected Object rejectedValue;

		@JsonProperty("message")
		protected String defaultMessage;

		public FieldErrorBodyItems(String field, Object rejectedValue, String defaultMessage, int itemId) {
			this.field = field;
			this.rejectedValue = rejectedValue;
			this.defaultMessage = defaultMessage;
			this.itemId = itemId;
		}

		public String getField() {
			return this.field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public Object getRejectedValue() {
			return this.rejectedValue;
		}

		public void setRejectedValue(Object rejectedValue) {
			this.rejectedValue = rejectedValue;
		}

		public String getDefaultMessage() {
			return this.defaultMessage;
		}

		public void setDefaultMessage(String defaultMessage) {
			this.defaultMessage = defaultMessage;
		}

	}
}
