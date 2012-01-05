/**
 * 
 */
package sim.monitor;

import sim.monitor.naming.Container;
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
	 * instance of monitor populated by calls to each factory methods
	 */
	private Monitor monitor;

	/*
	 * monitor factory constructor
	 * 
	 * @param container the container where monitors are placed
	 */
	private MonitorBuilder(Container container) {
		this.container = container;
		this.monitor = new Monitor(this.container);
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
		this.monitor.setTimeIntervalSampler(timeUnit, multiplier);
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
	public MonitorBuilder applyDelta(boolean delta) {
		this.monitor.useDelta(delta);
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
		this.monitor.addStatistic(aggregate);
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
		this.monitor.addRateStatistic(aggregate, timeUnit, multiplier);
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

	/**
	 * Build a monitor instance with the name passed as parameter. The monitor
	 * will have all the properties which were set on the builder before calling
	 * this method.
	 * 
	 * @param name
	 *            the name of the monitor
	 * @param description
	 *            the description of the monitor
	 * @return this
	 */
	public Monitor build(String name, String description) {
		this.monitor.setName(name);
		this.monitor.setDescription(description);
		return this.monitor;
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