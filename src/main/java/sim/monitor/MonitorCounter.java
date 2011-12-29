/**
 * 
 */
package sim.monitor;



/**
 * A counter monitor is a special type of monitor used by developers to count how many time an event has happened in the  monitored application.
 * For example it could be the number of accesses to a home page.
 * 
 * @author val
 *
 */
public class MonitorCounter extends Monitor<MonitorCounter, Long> {

	/**
	 * Constructs a new monitor counter.
	 * Here also the processor is initialized.
	 * 
	 * @param domain the domain
	 * @param name the name
	 */
	public MonitorCounter(Domain domain, String name) {
		super(domain, name);
		aggregate(Aggregation.Sum);
	}
	
	/**
	 * A hit on this monitor increments the counter
	 */
	public void hit() {
		super.hit_(new Long(1));
	}
	
	/*FIXME not sure if needed*/
	public void undo() {
		
	}
	
}
