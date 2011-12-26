/**
 * 
 */
package sim.monitor.internal;

import sim.monitor.internal.data.Data;
import sim.monitor.internal.data.LongData;
import sim.monitor.naming.Name;

/**
 * @author valer
 *
 */
public class LongValueProcessor extends AbstractMonitorProcessor {

	public LongValueProcessor(Name name) {
		super(name);
		dataClass = LongData.class;
	}
	
	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#newDataInstance()
	 */
	public Data newDataInstance() {
		try {
			return LongData.class.newInstance();
		} catch (Exception e) {
			//FIXME treat exception
		}
		//FIXME should never get here
		return null;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#input(sim.monitor.internal.data.Data)
	 */
	public void input(Data data) {
		notify(getName(), data);
	}

}
