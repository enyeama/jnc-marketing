package com.sap.jnc.marketing.infrastructure.shared.utils;

import java.math.BigDecimal;

/**
 * Created by ying.dong on 16/3/24.
 */
public class RMBUtils {

	public enum RMBUnit {
		FEN
	}

	public static Integer valueOf(Float rmb, RMBUnit rmbUnit) {
		Integer parsedRmb = 0;
		switch (rmbUnit) {
		case FEN:
			parsedRmb = new BigDecimal(rmb * 100).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			break;
		}
		return parsedRmb;
	}
}
