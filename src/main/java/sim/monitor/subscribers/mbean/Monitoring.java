/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author val
 *
 */
public abstract class Monitoring implements MonitoringMXBean {

	protected Object value;
	private String description;
	private String tags;
	private long rateInterval;
	private String aggregation;
	private String unit;
	private Map<String, List<ContextComposite>> context = new HashMap<String, List<ContextComposite>>();
	private String type;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param rateInterval
	 *            the rateInterval to set
	 */
	public void setRateInterval(long rateInterval) {
		this.rateInterval = rateInterval;
	}

	/**
	 * @param aggregation
	 *            the aggregation to set
	 */
	public void setAggregation(String aggregation) {
		this.aggregation = aggregation;
	}

	/**
	 * @param unit
	 *            the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(Map<String, List<ContextComposite>> context) {
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.subscribers.mbean.MonitoringMXBean#getDescription()
	 */
	public String getDescription() {
		return this.description;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.subscribers.mbean.MonitoringMXBean#getRateInterval()
	 */
	public long getRateInterval() {
		return this.rateInterval;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.subscribers.mbean.MonitoringMXBean#getAggregation()
	 */
	public String getAggregation() {
		return this.aggregation;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.subscribers.mbean.MonitoringMXBean#getUnit()
	 */
	public String getUnit() {
		return this.unit;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see sim.monitor.subscribers.mbean.MonitoringMXBean#getContext()
	 */
	public Map<String, List<ContextComposite>> getContext() {
		return this.context;
	}

}
