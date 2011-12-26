/**
 * 
 */
package sim.monitor;

import sim.monitor.internal.MonitorProcessor;
import sim.monitor.internal.ValueProcessor;
import sim.monitor.internal.data.DoubleValueType;
import sim.monitor.mbean.MBeanManager;
import sim.monitor.naming.Domain;
import sim.monitor.naming.Name;

/**
 * Monitor used to track fractional values from the application.
 * For example it could be used to register the values of orders amounts in an ordering system.
 *  
 * @author val
 *
 */
public class MonitorDoubleValue extends Monitor {

	public MonitorDoubleValue(Domain domain, String name, String description) {
		super(domain, name, description);
		MonitorProcessor valueProcessor = new ValueProcessor(new Name(domain, name, description));
		valueProcessor.addObserver(MBeanManager.instance());
		processors.add(valueProcessor);
	}
	
	public void hit(double value) {
		super.hit(new DoubleValueType(value));
	}

}
