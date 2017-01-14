package com.sap.jnc.marketing.infrastructure.shared.utils;

import java.util.Date;

/**
 * Generator to order id, import from external resources.
 * 
 * @author I071053 Diouf Du
 */
public class OrderNoGenerator {

	private static Date date = new Date();

	private static int seq = 1;

	private static final int ROTATION = 9999;

	public static synchronized Long generateLong() {
		return Long.parseLong(generate());
	}

	public static String generate() {
		if (seq > ROTATION) {
			seq = 1;
		}
		date.setTime(System.currentTimeMillis());
		return String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$04d", date, seq++);
	}
}
