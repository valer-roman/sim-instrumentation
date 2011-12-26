/**
 * 
 */
package sim.monitor.internal;

import java.util.concurrent.atomic.AtomicLong;

import sim.monitor.internal.data.Data;
import sim.monitor.internal.data.LongValueType;
import sim.monitor.naming.Name;

/**
 * @author val
 *
 */
public class AggregationProcessor extends AbstractMonitorProcessor {

	private Aggregation aggregation;
	
	private AtomicLong longSum = new AtomicLong();
	
	public AggregationProcessor(Name name, Aggregation aggregation) {
		super(name);
		this.aggregation = aggregation;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#input(sim.monitor.internal.data.Data)
	 */
	public void input(Data data) {
		switch(aggregation) {
		case Sum:
			processSum(data);
		}

	}

	protected void processSum(Data data) {
		if(data.getValue().isLongType()) {
			LongValueType ltv = (LongValueType) data.getValue();
			longSum.addAndGet(ltv.getValue());
		}
		Data newData = data.clone();
		newData.setValue(new LongValueType(longSum.longValue()));
		notify(name, newData);
	}
}
