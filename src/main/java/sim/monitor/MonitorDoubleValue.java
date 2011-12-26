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
public class MonitorDoubleValue extends Monitor {

	public MonitorDoubleValue(Domain domain, String name, String description) {
		super(domain, name, description);
		processor = new LongValueProcessor(new Name(domain, name, description));
	}
	
	public void hit(double value) {
		super.hit(new Double(value));
	}

}
