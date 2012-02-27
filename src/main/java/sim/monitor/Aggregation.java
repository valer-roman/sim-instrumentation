/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

/**
 * @author val
 *
 */
public enum Aggregation {

	Min, Max, Sum, Count, Average;

	public String toShortString() {
		switch (this) {
		case Min:
			return "MIN";
		case Max:
			return "MAX";
		case Sum:
			return "SUM";
		case Count:
			return "CNT";
		case Average:
			return "AVG";
		default:
			return "";
		}
	}

}
