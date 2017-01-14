package com.sap.jnc.marketing.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author I071053 Diouf Du
 */
public class ObjectErrorResponse extends ArrayList<ObjectErrorResponse.Item> implements Serializable {

	private static final long serialVersionUID = -1462618184406181073L;

	public ObjectErrorResponse() {
	}

	public ObjectErrorResponse(List<Item> items) {
		super(items);
	}

	public static class Item implements Serializable {

		private static final long serialVersionUID = 1290912396609784612L;

		private String code;

		private String objectName;

		private String message;

		public Item() {
		}

		public Item(String code, String objectName, String message) {
			this.code = code;
			this.objectName = objectName;
			this.message = message;
		}

		public String getCode() {
			return this.code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getObjectName() {
			return this.objectName;
		}

		public void setObjectName(String objectName) {
			this.objectName = objectName;
		}

		public String getMessage() {
			return this.message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
