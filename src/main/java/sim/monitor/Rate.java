/**
 * 
 */
package sim.monitor;

import sim.monitor.naming.Name;
import sim.monitor.timing.TimePeriod;

/**
 * @author val
 *
 */
public class Rate extends Statistic {

	private TimePeriod rateTime;
	private Aggregate aggregate;

	public Rate(TimePeriod rateTime, Aggregate aggregate, Name name) {
		super(name);
		this.rateTime = rateTime;
		this.aggregate = aggregate;
	}

	/**
	 * @return the rateTime
	 */
	public TimePeriod getRateTime() {
		return rateTime;
	}

	/**
	 * @param rateTime the rateTime to set
	 */
	public void setRateTime(TimePeriod rateTime) {
		this.rateTime = rateTime;
	}

	/**
	 * @return the aggregate
	 */
	public Aggregate getAggregate() {
		return aggregate;
	}

	/**
	 * @param aggregate
	 *            the aggregate to set
	 */
	public void setAggregate(Aggregate aggregate) {
		this.aggregate = aggregate;
	}

}
