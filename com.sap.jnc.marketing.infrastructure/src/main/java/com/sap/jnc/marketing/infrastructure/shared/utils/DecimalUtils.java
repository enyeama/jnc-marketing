package com.sap.jnc.marketing.infrastructure.shared.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalUtils {
	private DecimalUtils() {
	}

	public static final int DEFAULT_SCALE_MAX = 20;
	private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;
	public static final BigDecimal PERCENT_100 = BigDecimal.valueOf(100);

	public static void checkDivisorNotZero(BigDecimal divisor) {
		if (IsZero(divisor)) {
			throw new IllegalArgumentException("It's illegal if dividing by zero");
		}
	}

	public static Boolean IsZero(BigDecimal amount) {
		return amount == null ? true : amount.compareTo(BigDecimal.ZERO) == 0;
	}

	public static Boolean LessThanZero(BigDecimal amount) {
		return amount == null ? false : amount.compareTo(BigDecimal.ZERO) < 0;
	}

	public static Boolean MoreThanZero(BigDecimal amount) {
		return amount == null ? false : amount.compareTo(BigDecimal.ZERO) > 0;
	}

	public static int Compare(BigDecimal m1, BigDecimal m2) {
		BigDecimal m1NotNull = m1 == null ? BigDecimal.ZERO : m1;
		BigDecimal m2NotNull = m2 == null ? BigDecimal.ZERO : m2;
		return m1NotNull.compareTo(m2NotNull);
	}

	public static BigDecimal Add(BigDecimal m1, BigDecimal m2) {
		BigDecimal result = IsZero(m1) ? BigDecimal.ZERO : m1;
		return IsZero(m2) ? result : result.add(m2);
	}

	public static BigDecimal Subtract(BigDecimal m1, BigDecimal m2) {
		BigDecimal result = IsZero(m1) ? BigDecimal.ZERO : m1;
		return IsZero(m2) ? result : result.subtract(m2);
	}

	public static BigDecimal Multiply(BigDecimal m1, BigDecimal m2) {
		if (IsZero(m1) || IsZero(m2)) {
			return BigDecimal.ZERO;
		}
		return m1.multiply(m2);
	}

	public static BigDecimal Divide(BigDecimal m1, BigDecimal m2) {
		if (IsZero(m1)) {
			return BigDecimal.ZERO;
		}
		return m1.divide(m2, DEFAULT_SCALE_MAX, DEFAULT_ROUNDING_MODE);
	}

	public static BigDecimal MulAndDiv(BigDecimal amount, BigDecimal mult, BigDecimal div) {
		checkDivisorNotZero(div);

		if (IsZero(amount) || IsZero(mult)) {
			return BigDecimal.ZERO;
		}
		return Divide(Multiply(amount, mult), div);
	}

}
