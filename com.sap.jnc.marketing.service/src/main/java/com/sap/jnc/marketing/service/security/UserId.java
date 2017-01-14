package com.sap.jnc.marketing.service.security;

public interface UserId {

	String getValue();

	static boolean isPresent(UserId id) {
		return id != null && id.getValue() != null;
	}

	static String get(UserId id) {
		if (id == null) {
			return null;
		}
		return id.getValue();
	}

}
