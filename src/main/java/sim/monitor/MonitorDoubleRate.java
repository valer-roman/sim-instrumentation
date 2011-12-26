/**
 * 
 */
package sim.monitor;

import sim.monitor.naming.Domain;

/**
 * @author val
 *
 */
public class MonitorDoubleRate extends Monitor {

	public MonitorDoubleRate(Domain domain, String name, String description) {
		super(domain, name, description);
	}
	
	public void hit(double value) {
		super.hit(new Double(value));
	}
	
}
