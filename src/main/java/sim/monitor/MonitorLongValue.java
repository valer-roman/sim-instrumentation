/**
 * 
 */
package sim.monitor;

import sim.monitor.internal.MonitorProcessor;
import sim.monitor.internal.ValueProcessor;
import sim.monitor.internal.data.LongValueType;
import sim.monitor.mbean.MBeanManager;
import sim.monitor.naming.Domain;
import sim.monitor.naming.Name;

/**
 * @author val
 *
 */
public class MonitorLongValue extends Monitor {

	public MonitorLongValue(Domain domain, String name, String description) {
		super(domain, name, description);
		MonitorProcessor valueProcessor = new ValueProcessor(new Name(domain, name, description));
		valueProcessor.addObserver(MBeanManager.instance());
		this.processors.add(valueProcessor);
	}
	
	public void hit(long value) {
		super.hit(new LongValueType(value));
	}

}
