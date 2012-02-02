/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

import sim.monitor.Aggregation;
import sim.monitor.ContextEntry;
import sim.monitor.MeasureUtil;

/**
 * @author val
 *
 */
public class Interval {

	private Intervals intervals;

	private Object from;
	private Object to;

	private Object value = null;

	Interval(Intervals intervals) {
		this.intervals = intervals;
	}

	public Aggregation getAggregation() {
		return intervals.getAttributeIntervals().getAggregation();
	}

	Interval(Intervals intervals, Interval interval1, Interval interval2) {
		this(intervals);
		this.from = interval1.from;
		this.to = interval2.to;

		computeValue(interval1.value, interval2.value);
	}

	Interval(Intervals intervals, Object contextValue, Object value) {
		this(intervals, contextValue, contextValue, value);
	}

	Interval(Intervals intervals, Object from, Object to, Object value) {
		this(intervals);
		this.from = from;
		this.to = to;

		hit(value);
	}

	boolean isAggregation() {
		return !from.equals(to);
	}

	boolean contains(Object value) {
		if (value == from) {
			return true; // for undefined
		}
		if (MeasureUtil.isGreaterOrEqualThan(value, from)
				&& MeasureUtil.isLessOrEqualThan(value, to)) {
			return true;
		}
		return false;
	}

	Object differenceBetween(Interval interval) {
		if (MeasureUtil.isLessOrEqualThan(to, interval.from)) {
			return MeasureUtil.difference(interval.to, from);
		} else {
			return MeasureUtil.difference(to, interval.from);
		}
	}

	private void computeValue(Object value1, Object value2) {
		switch (getAggregation()) {
		case Min:
			this.value = MeasureUtil.isLessThan(value1, value2);
			break;
		case Max:
			this.value = MeasureUtil.isGreaterThan(value1, value2);
			break;
		case Sum:
			this.value = MeasureUtil.sum(value1, value2);
			break;
		case Count:
			this.value = MeasureUtil.sum(value1, value2);
			break;
		case Average:
			this.value = MeasureUtil.divide(MeasureUtil.sum(value1, value2),
					new Long(2));
		}
	}

	void hit(Object value) {
		if (value != null && isAggregation()) {
			computeValue(this.value, value);
		} else {
			this.value = value;
		}
	}

	@Override
	public String toString() {
		if (!isAggregation()) {
			if (from.equals(ContextEntry.UNDEFINED_VALUE)) {
				return "";
			} else {
				return "." + from.toString();
			}
		} else {
			return "." + from.toString() + "-" + to.toString();
		}
	}

	public Object getValue() {
		return value;
	}

	/**
	 * @return the from
	 */
	public Object getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(Object from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public Object getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(Object to) {
		this.to = to;
	}

}
