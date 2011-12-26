/**
 * 
 */
package sim.monitor.internal;

import sim.monitor.internal.data.Data;
import sim.monitor.internal.observer.MonitorProcessorObserver;
import sim.monitor.internal.observer.ObservableUtil;
import sim.monitor.naming.Name;
import sim.monitor.timing.TimeUnit;

/**
 * @author val
 *
 */
public abstract class AbstractMonitorProcessor implements MonitorProcessor {

	protected Name name;
	
	private Aggregation aggregation = Aggregation.None;
	private TimeUnit timeUnit = TimeUnit.None;
	private int timeUnitMultiplier = 0;
	
	private ObservableUtil observableUtil = new ObservableUtil();
	
	public AbstractMonitorProcessor(Name name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#getName()
	 */
	public Name getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#setProcessingOptions(sim.monitor.internal.Aggregation, sim.monitor.timing.TimeUnit, int)
	 */
	public void setProcessingOptions(Aggregation agg, TimeUnit timeUnit,
			int timeUnitMultiplier) {
		this.aggregation = agg;
		this.timeUnit = timeUnit;
		this.timeUnitMultiplier = timeUnitMultiplier;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#addObserver(sim.monitor.internal.MonitorProcessorObserver)
	 */
	public void addObserver(MonitorProcessorObserver observer) {
		observableUtil.addObserver(observer);
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#removeObserver(sim.monitor.internal.MonitorProcessorObserver)
	 */
	public void removeObserver(MonitorProcessorObserver observer) {
		observableUtil.removeObserver(observer);
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.MonitorProcessor#notify(sim.monitor.naming.Name, sim.monitor.internal.data.Data)
	 */
	public void notify(Name name, Data data) {
		observableUtil.notify(name, data);
	}
	
}
