/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import sim.monitor.naming.Container;
import sim.monitor.naming.Name;
import sim.monitor.processing.HitProcessor;
import sim.monitor.publishers.Aggregate;
import sim.monitor.publishers.Aggregation;
import sim.monitor.publishers.Rate;
import sim.monitor.publishers.RawPublisher;
import sim.monitor.publishers.Publisher;
import sim.monitor.timing.TimePeriod;
import sim.monitor.timing.TimeUnit;
import sim.monitor.transformers.DeltaTransformer;
import sim.monitor.transformers.TimeIntervalSampler;
import sim.monitor.transformers.Transformer;

/**
 * This is the core of the monitor, here are the configurations, the
 * transformers and the publishers of the monitor saved.
 * 
 * Once a hit is received from the Monitor class it is transmitted to this
 * class. Here there are two process types running on the hit : the
 * transformation and the publications. The transformation processes filter,
 * change or detail the data received with the hit. The publication processes
 * are preparing the data for visualization with external or internal
 * visualization tools.
 * 
 * @author val
 * 
 */
public class MonitorCore {

	private Monitor monitor;

	/**
	 * The container of the monitor
	 */
	protected Container container;

	/**
	 * The name of the monitor
	 */
	protected String name;

	/**
	 * The description of the monitor
	 */
	protected String description;

	private List<Transformer> transformers = new ArrayList<Transformer>();
	private boolean rawValuesPublished = true;

	private List<Publisher> statistics = new ArrayList<Publisher>();

	private BlockingQueue<Hit> hits = new LinkedBlockingQueue<Hit>();

	MonitorCore(Container container) {
		this.monitor = new Monitor(this);
		this.container = container;
	}

	public Monitor getMonitor() {
		return this.monitor;
	}

	public void acceptHit(long timestamp, Object value) {
		hits.add(new Hit(timestamp, value));
		HitProcessor.instance().signalHit(this);
	}

	void setName(String name) {
		this.name = name;
		if (this.name == null) {
			return;
		}
		for (Publisher statistic : statistics) {
			if (statistic.getName().getName() == null) {
				statistic.setName(new Name(statistic.getName().getContainer(),
						this.name + "." + statistic.getSuffix(), null));
			}
		}
	}

	/*
	 * Sets a description on the monitor
	 * 
	 * @param description the description
	 * 
	 * @return the monitor
	 */
	void setDescription(String description) {
		this.description = description;
	}

	/*
	 * Establishes a picking out interval on the monitors value. A new value is
	 * elected only after this interval has passed since the last value
	 * 
	 * @param timeUnit the time unit (seconds, minutes, hours ...)
	 * 
	 * @param timeMultiplier the time multiplier
	 * 
	 * @return this monitor
	 */
	void useTimeIntervalSampler(TimeUnit timeUnit, int timeMultiplier) {
		this.transformers.add(new TimeIntervalSampler(new TimePeriod(timeUnit,
				timeMultiplier)));
	}

	/*
	 * Set on monitor wheter to keep the received values or to perform a delta
	 * difference on current valu and last value.
	 * 
	 * @param delta boolean setting if delta is active or not
	 * 
	 * @return this monitor
	 */
	MonitorCore useDelta() {
		this.transformers.add(new DeltaTransformer());
		return this;
	}

	/**
	 * Set if an statistic on the received values is desired instead of the raw
	 * values. The statistic can be average, count, sum, min or max
	 * 
	 * @return this monitor
	 */
	MonitorCore addStatistic(Aggregate aggregate) {
		Aggregation aggregation = new Aggregation(aggregate, new Name(
				container, name, description));
		aggregation.setPublishedWithSuffix(true);
		this.statistics.add(aggregation);
		return this;
	}

	/**
	 * Sets on the received values a rate statistic. The rate is calculated as
	 * sum, average or count of the values per the interval of time passed as
	 * parameter.
	 * 
	 * @param aggregate
	 *            the aggregate of rate (sum, count or average)
	 * @param rateTimeUnit
	 *            the time unit
	 * @param multiplier
	 *            the time multiplier
	 * @return this monitor
	 */
	MonitorCore addRateStatistic(Aggregate aggregate, TimeUnit rateTimeUnit,
			int multiplier) {
		Rate rate = new Rate(new TimePeriod(rateTimeUnit, multiplier),
				aggregate, new Name(container, name, description));
		rate.setPublishedWithSuffix(true);
		this.statistics.add(rate);
		return this;
	}

	/**
	 * Adds a new statistic to the monitor. The statistic can be count, sum,
	 * average, min or max. The resulted value is recorder under a new statistic
	 * having the name passed as parameter.
	 * 
	 * @param name
	 *            the statistic name
	 * @param description
	 *            description of statistic
	 * @return this monitor
	 * 
	 * @see #setStatistic(Aggregate)
	 */
	MonitorCore addStatistic(Aggregate aggregate, String name,
			String description) {
		this.statistics.add(new Aggregation(aggregate, new Name(container,
				name, description)));
		return this;
	}

	/**
	 * Adds a rate statistic
	 * 
	 * @param type
	 * @param timeUnit
	 * @param timeMultiplier
	 * @param name
	 * @param description
	 * @return
	 * 
	 * @see #setRateStatistic(Aggregate, TimeUnit, int)
	 */
	MonitorCore addRateStatistic(Aggregate aggregate, TimeUnit timeUnit,
			int timeMultiplier, String name, String description) {
		this.statistics.add(new Rate(new TimePeriod(timeUnit, timeMultiplier),
				aggregate, new Name(container, name, description)));
		return this;
	}

	void setRawValuesPublished(boolean rawValuesPublished) {
		this.rawValuesPublished = rawValuesPublished;
	}

	boolean arePublishedRawValues() {
		return this.rawValuesPublished;
	}

	void publishRawValues() {
		this.statistics.add(new RawPublisher(new Name(container, name,
				description)));
	}

	public boolean hasMoreHits() {
		return !this.hits.isEmpty();
	}

	public void processNext() {
		processData(hits.poll());
	}

	protected void processData(Hit hit) {
		Collection<Data> datas = new ArrayList<Data>();

		Data data = new Data(new Name(container, name, description),
				hit.getTimestamp(), hit.getValue());
		datas.add(data);

		for (Transformer transformer : transformers) {
			datas = transformer.transform(datas);
		}

		// process data with publishers
		for (Publisher statistic : statistics) {
			statistic.hit(datas);
		}

	}

}
