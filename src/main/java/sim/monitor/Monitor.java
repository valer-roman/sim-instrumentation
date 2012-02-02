package sim.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import sim.monitor.processing.HitProcessor;
import sim.monitor.subscribers.SubscribeUpdater;
import sim.monitor.timing.TimePeriod;
import sim.monitor.timing.TimeUnit;
import sim.monitor.transformers.DeltaTransformer;
import sim.monitor.transformers.Filter;
import sim.monitor.transformers.TimeIntervalSampler;

/**
 *
 * A monitor is an entity used by developers that are monitoring their
 * application to extract runtime information about business actions.
 *
 * Each monitor is defined inside a container. For example a container could be
 * "com.test.monitoring:module=User Interface,component=Login"
 *
 * Once the monitored application has new relevant data for a monitor it should
 * call one of the hit methods of this class.
 *
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
public class Monitor extends Publisher {

	// private org.slf4j.Logger logger = LoggerFactory.getLogger(Monitor.class);

	/**
	 * The tags of the monitor
	 */
	protected Tags tags;

	private List<Filter> filters = new ArrayList<Filter>();
	private List<Rate> rates = new ArrayList<Rate>();
	private boolean forcePublishRawValues = false;

	private BlockingQueue<Hit> hits = new LinkedBlockingQueue<Hit>();

	private Collection<Hit> tmpHits = new ArrayList<Hit>();

	Monitor(String name, String description, boolean publishRawValues,
			List<String> tags, List<Filter> filters,
			List<sim.monitor.Rate> rates) {
		super(name, description);
		this.forcePublishRawValues = publishRawValues;
		Collections.sort(tags);
		this.tags = new Tags(tags.toArray(new String[0]));
		this.filters = filters;
		this.rates = rates;
		for (Rate rate : this.rates) {
			rate.setMonitor(this);
		}
	}

	/**
	 * @return the tags
	 */
	Tags getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	void setTags(Tags tags) {
		this.tags = tags;
	}

	void acceptHit(long timestamp, Object value, Context context) {
		hits.add(new Hit(timestamp, value, context));
		HitProcessor.instance().signalHit(this);
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
		this.filters.add(new TimeIntervalSampler(new TimePeriod(timeUnit,
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
	Monitor useDelta() {
		this.filters.add(new DeltaTransformer());
		return this;
	}

	void setRawValuesPublished(boolean rawValuesPublished) {
		this.forcePublishRawValues = rawValuesPublished;
	}

	boolean arePublishedRawValues() {
		return this.forcePublishRawValues;
	}

	public boolean hasMoreHits() {
		return !this.hits.isEmpty();
	}

	public void processNext() {
		processHits(hits.poll());
	}

	protected void processHits(Hit hit) {
		Collection<Hit> hits = new ArrayList<Hit>();

		hits.add(hit);

		for (Filter filter : filters) {
			hits = filter.transform(hits);
		}

		if (this.rates.isEmpty() || forcePublishRawValues) {
			tmpHits.addAll(hits);
			publish();
		}

		// process data with publishers
		for (Rate rate : rates) {
			rate.hit(hits);
			rate.publish();
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see sim.monitor.Publisher#publish()
	 */
	@Override
	public void publish() {
		SubscribeUpdater.instance().updateAllSubscribers(tmpHits, getTags(),
				name, getDescription(), "value", "raw value",
				Aggregation.Average);
		tmpHits.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see sim.monitor.Publisher#getSuffix()
	 */
	@Override
	public String getSuffix() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Called by instrumentation (code in monitored application that triggers monitors activation) when a new value is available for the monitor.
	 * This method attaches a timestamp to the recorded value and sends it further to processor.
	 *
	 * @param value the value registered in monitored application. Depending on monitor type can have different types (Long, Double ...)
	 */
	public void hit(Long value) {
		long timestamp = System.currentTimeMillis();
		this.acceptHit(timestamp, value, new Context());
		// threadPool.execute(new Processor(timestamp, value));
	}

	public void hit(Double value) {
		long timestamp = System.currentTimeMillis();
		this.acceptHit(timestamp, value, new Context());
		// threadPool.execute(new Processor(timestamp, value));
	}

	public void hit(Integer value) {
		long timestamp = System.currentTimeMillis();
		this.acceptHit(timestamp, value, new Context());
		// threadPool.execute(new Processor(timestamp, value));
	}

	public void hit(String value) {
		long timestamp = System.currentTimeMillis();
		this.acceptHit(timestamp, value, new Context());
		// threadPool.execute(new Processor(timestamp, value));
	}

	public void hit(Date value) {
		long timestamp = System.currentTimeMillis();
		this.acceptHit(timestamp, value, new Context());
		// threadPool.execute(new Processor(timestamp, value));
	}

	public void hit() {
		long timestamp = System.currentTimeMillis();
		this.acceptHit(timestamp, new Long(1), new Context());
		// threadPool.execute(new Processor(timestamp, new Long(1)));
	}

	public void hit(Map<String, Object> context) {
		long timestamp = System.currentTimeMillis();
		this.acceptHit(timestamp, new Long(1), new Context(context));
	}

}
