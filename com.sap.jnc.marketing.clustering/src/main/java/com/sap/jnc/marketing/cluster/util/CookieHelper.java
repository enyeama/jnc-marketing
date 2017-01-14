
package com.sap.jnc.marketing.cluster.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {
	private static final String DISTRIBUTED_SESSION_ID = "JSESSIONID";
	protected static Logger log = LoggerFactory.getLogger(CookieHelper.class);

	public static Cookie writeSessionIdToNewCookie(String id,
			HttpServletResponse response, int expiry) {
		Cookie cookie = new Cookie(DISTRIBUTED_SESSION_ID, id);
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
		return cookie;
	}

	public static Cookie writeSessionIdToCookie(String id,
			HttpServletRequest request, HttpServletResponse response, int expiry) {
		Cookie cookie = findCookie(DISTRIBUTED_SESSION_ID, request);
		if (cookie == null) {
			return writeSessionIdToNewCookie(id, response, expiry);
		} else {
			cookie.setValue(id);
			cookie.setMaxAge(expiry);
			response.addCookie(cookie);
		}
		return cookie;
	}

	public static String findCookieValue(String name, HttpServletRequest request) {
		Cookie cookie = findCookie(name, request);
		if (cookie != null) {
			return cookie.getValue();
		}
		return null;
	}

	public static Cookie findCookie(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (int i = 0, n = cookies.length; i < n; i++) {
			if (cookies[i].getName().equalsIgnoreCase(name)) {
				return cookies[i];
			}
		}
		return null;
	}

	public static String findSessionId(HttpServletRequest request) {
		return findCookieValue(DISTRIBUTED_SESSION_ID, request);
	}
}
