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
public class Delta extends AbstractMonitorProcessor {

	private Name name;

	private Data previousData;

	public Delta(Name name) {
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#newDataInstance()
	 */
	public Data newDataInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#input(sim.monitor.internal.data.Data)
	 */
	public void input(Data data) {
		// TODO Auto-generated method stub
		
	}
	
}
