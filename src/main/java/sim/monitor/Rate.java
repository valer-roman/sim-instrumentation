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

	public enum Type {
		sum, count, average;
	}
	
	private TimePeriod rateTime;
	private Type type;
	
	public Rate(TimePeriod rateTime, Type type, Name name) {
		super(name);
		this.rateTime = rateTime;
		this.type = type;
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
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

}
