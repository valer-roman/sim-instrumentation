/**
 * 
 */
package sim.monitor.internal.observer;

import sim.monitor.internal.data.Data;
import sim.monitor.naming.Name;

/**
 * @author valer
 *
 */
public interface MonitorProcessorObserver {

	public void update(Name name, Data data);
	
}
