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
class Average extends Rate {

	private Long count = new Long(0);
	private Object sum = new Long(0);

	public Average(TimePeriod rateTime, String name, String description) {
		super(rateTime, name, description);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see sim.monitor.Rate#getAggregation()
	 */
	@Override
	public Aggregation getAggregation() {
		return Aggregation.Average;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see sim.monitor.Rate#resetValues()
	 */
	@Override
	protected void resetValues() {
		count = new Long(0);
		sum = new Long(0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see sim.monitor.Aggregation#computeAggregate(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	Object computeAggregate(Object result, Object value) {
		count = count + 1;
		sum = MeasureUtil.sum(sum, value);
		return MeasureUtil.divide(sum, count);
	}

}
