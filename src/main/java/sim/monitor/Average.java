/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;



/**
 * 
 * @author val
 * 
 */
class Average extends Aggregation {

	private Long count = new Long(0);
	private Object sum = new Long(0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Aggregation#getSuffix()
	 */
	@Override
	public String getSuffix() {
		return "AVG";
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
