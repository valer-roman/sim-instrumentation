/**
 * 
 */
package sim.monitor.internal;

import sim.monitor.internal.data.Data;
import sim.monitor.naming.Name;

/**
 * @author val
 *
 */
public class ValueProcessor extends AbstractMonitorProcessor {

	public ValueProcessor(Name name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#input(sim.monitor.internal.data.Data)
	 */
	public void input(Data data) {
		notify(getName(), data);
	}

}
