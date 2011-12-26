/**
 * 
 */
package sim.monitor;

import sim.monitor.internal.data.DoubleValueType;
import sim.monitor.naming.Domain;

/**
 * @author val
 *
 */
public class MonitorDoubleDelta extends Monitor {

	public MonitorDoubleDelta(Domain domain, String name, String description) {
		super(domain, name, description);
	}
	
	public void hit(double value) {
		super.hit(new DoubleValueType(value));
	}
	
}
