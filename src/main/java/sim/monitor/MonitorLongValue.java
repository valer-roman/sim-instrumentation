/**
 * 
 */
package sim.monitor;

import sim.monitor.internal.DoubleValueProcessor;
import sim.monitor.internal.data.LongValueType;
import sim.monitor.naming.Domain;
import sim.monitor.naming.Name;

/**
 * @author val
 *
 */
public class MonitorLongValue extends Monitor {

	public MonitorLongValue(Domain domain, String name, String description) {
		super(domain, name, description);
		this.processor = new DoubleValueProcessor(new Name(domain, name, description));
	}
	
	public void hit(long value) {
		super.hit(new LongValueType(value));
	}

}
