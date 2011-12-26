/**
 * 
 */
package sim.monitor;

import sim.monitor.internal.CounterProcessor;
import sim.monitor.mbean.MBeanManager;
import sim.monitor.naming.Domain;
import sim.monitor.naming.Name;
import sim.monitor.timing.TimePeriod;

/**
 * A counter monitor is a special type of monitor used by developers to count how many time an event has happened in the  monitored application.
 * For example it could be the number of accesses to a home page.
 * 
 * @author val
 *
 */
public class MonitorCounter extends Monitor {

	//FIXME should be in a list of monitors if many. each one then it is hit when this mmonitor is hit
	private MonitorLongRate rate;
	
	/**
	 * Constructs a new monitor counter.
	 * Here also the processor is initialized.
	 * 
	 * @param domain the domain
	 * @param name the name
	 * @param description the description
	 */
	public MonitorCounter(Domain domain, String name, String description) {
		super(domain, name, description);
		this.processor = new CounterProcessor(new Name(domain, name, description));
		//FIXME move this code somewhere else, maybe ...
		this.processor.addObserver(MBeanManager.instance());
	}
	
	/**
	 * Through this method a rate monitor can be added on the values of this counter.
	 * A rate monitor is used to compute the number of evnts per a period of time.
	 * 
	 * @param timePeriod the time period (1 second, 1 hour, 1 day, 10 seconds ...)
	 * @param name the name of the new monitor
	 * @param description the description of the new monitor
	 */
	/*FIXME maybe the new monitor should operate on the output values of the processor of this monitor*/
	public void addRate(TimePeriod timePeriod, String name, String description) {
		rate = new MonitorLongRate(domain, name, description, timePeriod);
	}
	
	/**
	 * A hit on this monitor increments the counter
	 */
	public void hit() {
		super.hit(new Long(1));
		rate.hit(new Long(1)); //FIXME iterate though all the rate monitors and hit them
	}
	
	/*FIXME not sure if needed*/
	public void undo() {
		
	}
	
}
