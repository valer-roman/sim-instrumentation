/**
 * 
 */
package sim.monitor;

import sim.monitor.internal.LongValueProcessor;
import sim.monitor.naming.Domain;
import sim.monitor.naming.Name;

/**
 * @author val
 *
 */
public class MonitorLongValue extends Monitor {

	public MonitorLongValue(Domain domain, String name, String description) {
		super(domain, name, description);
		this.processor = new LongValueProcessor(new Name(domain, name, description));
	}
	
	public void hit(long value) {
		super.hit(new Long(value));
	}

}
