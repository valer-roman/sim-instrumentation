/**
 * 
 */
package sim.monitor.internal;

import sim.monitor.internal.data.Data;
import sim.monitor.internal.data.DataValueType;
import sim.monitor.naming.Name;

/**
 * @author val
 *
 */
public class DeltaProcessor extends AbstractMonitorProcessor {

	private Data previousData;

	public DeltaProcessor(Name name) {
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#input(sim.monitor.internal.data.Data)
	 */
	public void input(Data data) {
		DataValueType value = data.getValue();
		DataValueType previousValue = (previousData == null) ? value.initNew() : previousData.getValue();
		Data newData = data.clone();
		newData.setValue(value.difference(previousValue));
		previousData = data;
		notify(getName(), newData);
	}
	
}
