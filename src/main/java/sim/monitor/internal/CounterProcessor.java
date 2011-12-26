/**
 * 
 */
package sim.monitor.internal;

import java.util.concurrent.atomic.AtomicLong;

import sim.monitor.internal.data.Data;
import sim.monitor.internal.data.LongData;
import sim.monitor.naming.Name;

/**
 * @author valer
 *
 */
public class CounterProcessor extends AbstractMonitorProcessor {

	private AtomicLong counter = new AtomicLong();
	
	public CounterProcessor(Name name) {
		super(name);
		super.dataClass = LongData.class;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#newDataInstance()
	 */
	public Data newDataInstance() {
		try {
			return dataClass.newInstance();
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
		LongData longData = (LongData) data;
		longData.setValue(counter.addAndGet(longData.getValue()));
		notify(getName(), longData);
	}

}
