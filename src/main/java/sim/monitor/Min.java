/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import sim.monitor.timing.TimePeriod;



/**
 * 
 * @author val
 * 
 */
class Min extends Rate {

	public Min(TimePeriod rateTime, String name, String description) {
		super(rateTime, name, description);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Aggregation#getSuffix()
	 */
	@Override
	String getSuffix() {
		String suffix = super.getSuffix();
		if (suffix == null) {
			return "MIN";
		} else {
			return "MIN/" + suffix;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.AbstractAggregation#computeAggregate(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	Object computeAggregate(Object result, Object value) {
		return MeasureUtil.min(result, value);
	}

}
