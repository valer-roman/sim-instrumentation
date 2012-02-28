/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import sim.monitor.timing.TimePeriod;

/**
 * @author val
 *
 */
public abstract class RateNamer extends MonitorNamer {

	private TimePeriod rateTime;
	private Aggregation aggregation;

	public RateNamer(String name, String description) {
		super(name, description);
	}

	/**
	 * @return the rateTime
	 */
	public TimePeriod getRateTime() {
		return rateTime;
	}

	/**
	 * @param rateTime
	 *            the rateTime to set
	 */
	public void setRateTime(TimePeriod rateTime) {
		this.rateTime = rateTime;
	}

	/**
	 * @return the aggregation
	 */
	public Aggregation getAggregation() {
		return aggregation;
	}

	/**
	 * @param aggregation
	 *            the aggregation to set
	 */
	public void setAggregation(Aggregation aggregation) {
		this.aggregation = aggregation;
	}
}
