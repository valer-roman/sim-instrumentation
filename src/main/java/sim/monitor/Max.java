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
class Max extends Rate {

	public Max(TimePeriod rateTime, String name, String description) {
		super(rateTime, name, description);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.Rate#getAggregation()
	 */
	@Override
	Aggregation getAggregation() {
		return Aggregation.Max;
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
			return "MAX";
		} else {
			return "MAX/" + suffix;
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
		return MeasureUtil.max(result, value);
	}

}
