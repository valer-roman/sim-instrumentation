/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author val
 *
 */
public class MeasureUtil {

	public static Object sum(Object value1, Object value2) {
		if (!isNumeric(value1)) {
			return null;
		}
		if (!isNumeric(value2)) {
			return value1;
		}

		if (isInteger(value1) && isInteger(value2)) {
			return toInteger(value1) + toInteger(value2);
		}

		return toDouble(value1) + toDouble(value2);
	}

	public static Object difference(Object value1, Object value2) {
		if (!isNumeric(value1)) {
			return null;
		}
		if (!isNumeric(value2)) {
			return value1;
		}

		if (isInteger(value1) && isInteger(value2)) {
			return toInteger(value1) - toInteger(value2);
		}

		return toDouble(value1) - toDouble(value2);
	}

	public static Object divide(Object divident, Object divisor) {
		if (!isNumeric(divident)) {
			return null;
		}
		if (!isNumeric(divisor)) {
			return divident;
		}

		return (isFraction(divident) ? toDouble(divident) : toInteger(divident))
				/ (isFraction(divisor) ? toDouble(divisor) : toInteger(divisor));
	}

	public static Object multiply(Object value1, Object value2) {
		if (!isNumeric(value1)) {
			return null;
		}
		if (!isNumeric(value2)) {
			return value1;
		}

		return (isFraction(value1) ? toDouble(value1) : toInteger(value1))
				* (isFraction(value2) ? toDouble(value2) : toInteger(value2));
	}

	public static Object min(Object value1, Object value2) {
		return minOrMax(value1, value2, true);
	}

	public static Object max(Object value1, Object value2) {
		return minOrMax(value1, value2, false);
	}

	private static Object minOrMax(Object value1, Object value2, boolean min) {
		if (isNumeric(value1)) {
			if (isNumeric(value2)) {
				if ((isFraction(value1) ? toDouble(value1) : toInteger(value1)) <= (isFraction(value2) ? toDouble(value2)
						: toInteger(value2))) {
					return min ? value1 : value2;
				} else {
					return min ? value2 : value1;
				}
			} else {
				return value1;
			}
		} else {
			return value1;
		}
	}

	public static boolean isFraction(Object value) {
		if (value instanceof Float || value instanceof Double || value instanceof BigDecimal) {
			return true;
		}
		return false;
	}

	public static boolean isInteger(Object value) {
		if (value instanceof Short || value instanceof Integer || value instanceof Long || value instanceof BigInteger) {
			return true;
		}
		return false;
	}

	public static boolean isNumeric(Object value) {
		if (value instanceof Number) {
			return true;
		}
		return false;
	}

	public static Double toDouble(Object value) {
		if (value instanceof Double) {
			return ((Double) value).doubleValue();
		} else if (value instanceof Float) {
			return ((Float) value).doubleValue();
		} else if (value instanceof BigDecimal) {
			return ((BigDecimal) value).doubleValue();
		}
		return null;
	}

	public static Long toInteger(Object value) {
		if (value instanceof Short) {
			return ((Short) value).longValue();
		} else if (value instanceof Integer) {
			return ((Integer) value).longValue();
		} else if (value instanceof Long) {
			return ((Long) value).longValue();
		} else if (value instanceof BigInteger) {
			return ((BigInteger) value).longValue();
		}// FIXME BigInteger might be bigger than Long !!!
		return null;
	}

	public static boolean isGreaterOrEqualThan(Object value1, Object value2) {
		return isGreaterThan(value1, value2) || isEqual(value1, value2);
	}

	public static boolean isGreaterThan(Object value1, Object value2) {
		return !isLessThan(value1, value2) && !isEqual(value1, value2);
	}

	public static boolean isLessOrEqualThan(Object value1, Object value2) {
		return isLessThan(value1, value2) || isEqual(value1, value2);
	}

	public static boolean isLessThan(Object value1, Object value2) {
		if (!isNumeric(value1)) {
			return false;
		}
		if (!isNumeric(value2)) {
			return false;
		}

		if (isInteger(value1)) {
			if (isInteger(value2)) {
				return toInteger(value1) < toInteger(value2);
			} else {
				return toInteger(value1) < toDouble(value2);
			}
		} else {
			if (isInteger(value2)) {
				return toDouble(value1) < toInteger(value2);
			} else {
				return toDouble(value1) < toDouble(value2);
			}
		}
	}

	public static boolean isEqual(Object value1, Object value2) {
		if (!isNumeric(value1)) {
			return false;
		}
		if (!isNumeric(value2)) {
			return false;
		}

		if (isInteger(value1)) {
			if (isInteger(value2)) {
				return toInteger(value1) == toInteger(value2);
			} else {
				return toInteger(value1) == toDouble(value2).longValue();
			}
		} else {
			if (isInteger(value2)) {
				return toDouble(value1) == toInteger(value2).doubleValue();
			} else {
				return toDouble(value1) == toDouble(value2);
			}
		}
	}

}
