/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sim.monitor.subscribers.SubscribeUpdater;

/**
 * 
 * @author val
 * 
 */
public abstract class Aggregation extends Publisher {

	private Object result = new Long(0);

	private Monitor monitor;

	private boolean forcePublication;

	private List<Rate> rates = new ArrayList<Rate>();

	protected Collection<Hit> hits = new ArrayList<Hit>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Aggregation#getMonitor()
	 */
	public Monitor getMonitor() {
		return this.monitor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Aggregation#setMonitor(sim.monitor.Monitor)
	 */
	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Publisher#publish()
	 */
	@Override
	public void publish() {
		String name = null;
		if (this.getName() == null) {
			name = monitor.getName() + " " + this.getSuffix();
		} else {
			name = this.getName();
		}
		SubscribeUpdater.instance().updateAllSubscribers(hits,
				monitor.getTags(), name,
				getDescription());
		hits.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Aggregation#getRates()
	 */
	public List<Rate> getRates() {
		return rates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Aggregation#setRates(java.util.List)
	 */
	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Aggregation#isForcePublication()
	 */
	public boolean isForcePublication() {
		return this.forcePublication;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Aggregation#forcePublication()
	 */
	public void setForcePublication(boolean forcePublication) {
		this.forcePublication = forcePublication;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Aggregation#hit(java.util.Collection)
	 */
	public void hit(Collection<Hit> hits) {
		long timestamp = 0;
		for (Hit hit : hits) {
			Object value = hit.getValue();
			result = computeAggregate(result, value);
			timestamp = Math.max(timestamp, hit.getTimestamp());
		}
		Hit hit = new Hit(timestamp, result);
		this.hits.add(hit);
	}

	abstract Object computeAggregate(Object result, Object value);
	// {
	// return MeasureUtil.sum(result, value);
	// }
}
