/**
 * 
 */
package sim.monitor.internal;

import sim.monitor.internal.data.Data;
import sim.monitor.naming.Name;

/**
 * @author valer
 *
 */
public class DoubleValueProcessor extends AbstractMonitorProcessor {

	public DoubleValueProcessor(Name name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#input(sim.monitor.internal.data.Data)
	 */
	public void input(Data data) {
		notify(getName(), data);
	}

}
