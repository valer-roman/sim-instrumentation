/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

import java.util.ArrayList;

import sim.monitor.MeasureUtil;

/**
 * @author val
 *
 */
public class Intervals extends ArrayList<Interval> {

	private static final long serialVersionUID = 1L;

	private static final int MAX_VALUES_PER_KEY = 3;

	private AttributeIntervals attributeIntervals;

	public Intervals(AttributeIntervals attributeIntervals) {
		this.attributeIntervals = attributeIntervals;
	}

	public AttributeIntervals getAttributeIntervals() {
		return attributeIntervals;
	}

	private Interval addIntervalAtBegin(Object contextValue, Object value) {
		return addInterval(contextValue, this.isEmpty() ? null : this.get(0),
				true, value);
	}

	private Interval addIntervalAtEnd(Object contextValue, Object value) {
		return addInterval(contextValue, this.get(this.size() - 1), false,
				value);
	}

	private Interval addInterval(Object contextValue,
			Interval neighbourInterval, boolean begin, Object value) {
		Interval interval = null;
		if (neighbourInterval != null && neighbourInterval.isAggregation()) {
			if (begin) {
				interval = new Interval(this, contextValue,
						neighbourInterval.getFrom(), value);
			} else {
				interval = new Interval(this, neighbourInterval.getTo(),
						contextValue, value);
			}
		} else {
			interval = new Interval(this, contextValue, value);
		}
		if (begin) {
			this.add(0, interval);
		} else {
			this.add(interval);
		}
		return interval;
	}

	IntervalChanges createIntervalSmallestDifferenceValue() {
		IntervalChanges intervalChanges = new IntervalChanges();
		Interval lastInterval = null;
		Object smallestDiff = null;
		int index = -1;
		for (int i = 0; i < this.size(); i++) {
			Interval interval = this.get(i);
			if (lastInterval == null) {
				lastInterval = interval;
				continue;
			}
			Object diff = lastInterval.differenceBetween(interval);
			if (smallestDiff == null
					|| MeasureUtil.isLessThan(diff, smallestDiff)) {
				smallestDiff = diff;
				index = i - 1;
			}
			lastInterval = interval;
		}
		if (index == -1) {
			// FIXME EXCEPTION
			return null;
		}
		Interval interval = new Interval(this, this.get(index),
				this.get(index + 1));
		intervalChanges.added(interval);
		intervalChanges.removed(this.get(index));
		intervalChanges.removed(this.get(index + 1));
		this.remove(index + 1);
		this.remove(index);
		this.add(index, interval);
		return intervalChanges;
	}

	IntervalChanges put(Object contextValue, Object value) {
		IntervalChanges intervalChanges = new IntervalChanges();
		for (Interval interval : this) {
			if (interval.contains(contextValue)) {
				interval.hit(value);
				intervalChanges.modified(interval);
				return intervalChanges;
			}
		}
		if (this.size() == MAX_VALUES_PER_KEY) {
			intervalChanges.add(createIntervalSmallestDifferenceValue());
		}
		// now the size should be MAX_VALUES_PER_KEY - 1
		if (this.isEmpty()
				|| MeasureUtil.isLessThan(contextValue, this.get(0).getFrom())) {
			intervalChanges.added(addIntervalAtBegin(contextValue, value));
		} else {
			intervalChanges.added(addIntervalAtEnd(contextValue, value));
		}
		return intervalChanges;
	}

}
