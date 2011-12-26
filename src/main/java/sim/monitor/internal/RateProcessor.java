/**
 * 
 */
package sim.monitor.internal;

import sim.monitor.internal.data.Data;
import sim.monitor.internal.data.DoubleData;
import sim.monitor.internal.data.LongData;
import sim.monitor.naming.Name;
import sim.monitor.timing.TimePeriod;

/**
 * @author val
 *
 */
public class RateProcessor extends AbstractMonitorProcessor {

	private int rateSeconds;
	
	private long startTime = -1;
	private double valueSum = 0;
	
	public RateProcessor(Name name, TimePeriod timePeriod) {
		super(name);
		this.rateSeconds = timePeriod.getSeconds();
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#newDataInstance()
	 */
	public Data newDataInstance() {
		return new LongData();
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#input(sim.monitor.internal.data.Data)
	 */
	public void input(Data data) {
		long value = ((LongData) data).getValue();
		
		if (startTime == -1) {
			startTime = data.getTimestamp();
		}
		valueSum += value;

		long timeDiff = data.getTimestamp() - startTime;
		double rate = (valueSum * (rateSeconds * 1000)) / (timeDiff == 0 ? timeDiff = 1 : timeDiff);
		notify(name, new DoubleData(data.getTimestamp(), rate));

	}

}
