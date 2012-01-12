/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;



/**
 * 
 * @author val
 * 
 */
class Sum extends Aggregation {

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Aggregation#getSuffix()
	 */
	@Override
	public String getSuffix() {
		return "SUM";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.AbstractAggregation#computeAggregate(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	Object computeAggregate(Object result, Object value) {
		return MeasureUtil.sum(result, value);
	}

}
