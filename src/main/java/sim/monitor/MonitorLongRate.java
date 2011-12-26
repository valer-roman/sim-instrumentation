/**
 * 
 */
package sim.monitor;

import sim.monitor.internal.RateProcessor;
import sim.monitor.internal.data.LongValueType;
import sim.monitor.mbean.MBeanManager;
import sim.monitor.naming.Domain;
import sim.monitor.naming.Name;
import sim.monitor.timing.TimePeriod;

/**
 * Rate monitor used to measure a number of events per a period of time.
 * For example it could be the number of bytes transferred per second.
 * 
 * @author val
 */
public class MonitorLongRate extends Monitor {

	/**
	 * @see sim.monitor.Monitor
	 */
	public MonitorLongRate(Domain domain, String name, String description, TimePeriod timePeriod) {
		super(domain, name, description);
		this.processor = new RateProcessor(new Name(domain, name, description), timePeriod);
		//FIXME
		this.processor.addObserver(MBeanManager.instance());
	}
	
	/**
	 * @see sim.monitor.Monitor#hit(Object value)
	 */
	public void hit(long value) {
		super.hit(new LongValueType(value));
	}
	
}
