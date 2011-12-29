/**
 * 
 */
package sim.monitor;

import sim.monitor.timing.TimePeriod;

/**
 * Rate monitor used to measure a number of events per a period of time.
 * For example it could be the number of bytes transferred per second.
 * 
 * @author val
 */
public class MonitorLongRate extends Monitor<MonitorLongRate, Long> {

	/**
	 * @see sim.monitor.Monitor
	 */
	public MonitorLongRate(Domain domain, String name, TimePeriod timePeriod) {
		super(domain, name);
	}
	
}
