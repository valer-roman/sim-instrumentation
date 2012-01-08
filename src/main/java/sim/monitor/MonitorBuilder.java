/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import sim.monitor.naming.Container;
import sim.monitor.processing.HitProcessor;
import sim.monitor.publishers.Aggregate;
import sim.monitor.timing.TimeUnit;

/**
 * Factory for monitors
 * 
 * A factory instance is created once a container is given. With the factory
 * instance a monitor can be built. The name of the monitor is a must so it is
 * passed as parameter to the build method.
 * 
 * @author val
 * 
 */
public class MonitorBuilder {

	/*
	 * default container where new monitors are placed
	 */
	private Container container;

	/*
	 * instance of monitor core populated by calls to each factory methods
	 */
	private MonitorCore monitorCore;

	/*
	 * monitor factory constructor
	 * 
	 * @param container the container where monitors are placed
	 */
	private MonitorBuilder(Container container) {
		this.container = container;
		this.monitorCore = new MonitorCore(this.container);
	}

	/**
	 * Static method creating instance of this factory
	 * 
	 * @param container
	 *            the default container where monitors are placed
	 * @return instace to this factory
	 */
	public static MonitorBuilder inContainer(String container) {
		return new MonitorBuilder(Container.parse(container));
	}

	/**
	 * Configure the monitor to use a time interval smapler. A new value will be
	 * used only if the confuured time period has passed.
	 * 
	 * @param timeUnit
	 *            the time unit
	 * @param multiplier
	 *            the multiplier
	 * @return this
	 */
	public MonitorBuilder applyTimeIntervalSampler(TimeUnit timeUnit,
			int multiplier) {
		this.monitorCore.useTimeIntervalSampler(timeUnit, multiplier);
		return this;
	}

	/**
	 * Configure the monitor to compute the difference betwwen the current
	 * received value and the last received value. This difference will be the
	 * new value.
	 * 
	 * @param delta
	 *            true if delta computation is desired. False is default.
	 * @return this
	 */
	public MonitorBuilder applyDelta() {
		this.monitorCore.useDelta();
		return this;
	}

	/**
	 * Adds a statistic to monitor
	 * 
	 * @param aggregate
	 *            aggregation used for statistic
	 * @return this
	 */
	public MonitorBuilder addStatistic(Aggregate aggregate) {
		this.monitorCore.addStatistic(aggregate);
		return this;
	}

	/**
	 * Adds a statistic to the monitor and publish it using the named passed as
	 * parameter.
	 * 
	 * @param aggregate
	 *            aggregation used for statistic
	 * @param name
	 *            the name to publish the statistic
	 * @param description
	 *            the description of the statistic publication
	 * @return this
	 */
	public MonitorBuilder addStatistic(Aggregate aggregate, String name,
			String description) {
		return this;
	}

	/**
	 * Adds a rate computation to the monitor.
	 * 
	 * @param aggregate
	 *            the aggregation used for the values
	 * @param timeUnit
	 *            the time unit of the rate unit
	 * @param multiplier
	 *            the multiplier of the time unit
	 * @return this
	 */
	public MonitorBuilder addRate(Aggregate aggregate, TimeUnit timeUnit,
			int multiplier) {
		this.monitorCore.addRateStatistic(aggregate, timeUnit, multiplier);
		return this;
	}

	/**
	 * Adds a rate computation to the monitor. The rate will be published using
	 * the name and description passed as parameters.
	 * 
	 * @param aggregate
	 *            aggregation used for values
	 * @param timeUnit
	 *            the time unit of the rate unit
	 * @param multiplier
	 *            the time unit multiplier
	 * @param name
	 *            the name for publication
	 * @param description
	 *            the description for publication
	 * @return this
	 */
	public MonitorBuilder addRate(Aggregate aggregate, TimeUnit timeUnit,
			int multiplier, String name, String description) {
		return this;
	}

	public MonitorBuilder publishRawValues(boolean publishedRawValues) {
		this.monitorCore.setRawValuesPublished(publishedRawValues);
		return this;
	}

	/**
	 * Build a monitor instance with the name passed as parameter. The monitor
	 * will have all the properties which were set on the builder before calling
	 * this method.
	 * 
	 * The name and description will be used to publish the raw values or the
	 * statistics without a specific publication name.
	 * 
	 * @param name
	 *            the name of the monitor
	 * @param description
	 *            the description of the monitor
	 * @return monitor
	 */
	public Monitor build(String name, String description) {
		this.monitorCore.setName(name);
		this.monitorCore.setDescription(description);
		if (this.monitorCore.arePublishedRawValues()) {
			this.monitorCore.publishRawValues();
		}
		HitProcessor.instance().acceptMonitor(monitorCore);
		return this.monitorCore.getMonitor();
	}

	/**
	 * Like the method {@link #build(String, String)} except tha tno publication
	 * name or description is given. This means that, except if there are
	 * statistics with publication name, nothing will be published.
	 * 
	 * @return monitor
	 */
	public Monitor build() {
		HitProcessor.instance().acceptMonitor(monitorCore);
		return this.monitorCore.getMonitor();
	}

	/**
	 * Clears all the properties set on this builder except the container.
	 * 
	 * @return this
	 */
	public MonitorBuilder reset() {
		return this;
	}
}