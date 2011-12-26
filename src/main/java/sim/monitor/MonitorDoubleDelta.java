/**
 * 
 */
package sim.monitor;

import sim.monitor.internal.DeltaProcessor;
import sim.monitor.internal.MonitorProcessor;
import sim.monitor.internal.data.DoubleValueType;
import sim.monitor.mbean.MBeanManager;
import sim.monitor.naming.Domain;
import sim.monitor.naming.Name;

/**
 * @author val
 *
 */
public class MonitorDoubleDelta extends Monitor {

	public MonitorDoubleDelta(Domain domain, String name, String description) {
		super(domain, name, description);
		MonitorProcessor deltaProcessor = new DeltaProcessor(new Name(domain, name, description));
		deltaProcessor.addObserver(MBeanManager.instance());
		this.processors.add(deltaProcessor);
	}
	
	public void hit(double value) {
		super.hit(new DoubleValueType(value));
	}
	
}
