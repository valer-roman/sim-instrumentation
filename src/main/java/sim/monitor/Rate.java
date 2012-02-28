/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sim.monitor.subscribers.SubscribeUpdater;
import sim.monitor.timing.TimePeriod;

/**
 * @author val
 *
 */
public abstract class Rate extends RateNamer {

	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(Rate.class);

	private Monitor monitor;

	private TimePeriod rateTime;

	private long rateTimeInMillis = 0;

	private ContextEntryValue result = new ContextEntryValue();

	private Map<Long, ContextEntryValue> hits = new ConcurrentHashMap<Long, ContextEntryValue>();

	private long lastTimestampMark = 0;

	protected Collection<Hit> resultHits = new ArrayList<Hit>();

	abstract Object computeAggregate(Object result, Object value);

	@Override
	public abstract Aggregation getAggregation();

	public Rate(TimePeriod rateTime, String name, String description) {
		super(name, description);

		if (rateTime != null) {
			this.rateTime = rateTime;
			this.rateTimeInMillis = this.rateTime.getSeconds() * 1000;
			RateScheduler.instance().registerRate(this);
		}
	}

	/**
	 * @return the monitor
	 */
	public Monitor getMonitor() {
		return monitor;
	}

	/**
	 * @param monitor
	 *            the monitor to set
	 */
	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	/**
	 * @return the rateTime
	 */
	@Override
	public TimePeriod getRateTime() {
		return rateTime;
	}

	/**
	 * @param rateTime
	 *            the rateTime to set
	 */
	@Override
	public void setRateTime(TimePeriod rateTime) {
		this.rateTime = rateTime;
		this.rateTimeInMillis = this.rateTime.getSeconds() * 1000;
	}

	protected void resetValues() {
		result.reset();
	}

	protected void hitRate(Collection<Hit> hits) {
		for (Hit hit : hits) {
			long timestamp = 0;
			Object value = hit.getValue();
			if (lastTimestampMark == 0) {
				lastTimestampMark = hit.getTimestamp();
			}
			if ((hit.getTimestamp() - lastTimestampMark) > rateTimeInMillis) {
				System.out.println("RESET");
				lastTimestampMark = lastTimestampMark + rateTimeInMillis;
				resetValues();
			}
			timestamp = lastTimestampMark + rateTimeInMillis;
			for (ContextEntry contextEntry : hit.getContext()) {
					//.withUndefinedKey()) {
				Object aValue = result.get(contextEntry);
				result.put(contextEntry, computeAggregate(aValue, value));
				if (!this.hits.containsKey(timestamp)) {
					this.hits.put(timestamp, new ContextEntryValue());
				}
				this.hits.get(timestamp).put(contextEntry,
						result.get(contextEntry));
				logger.info("put in map : " + timestamp + ","
						+ result.get(contextEntry));
			}
		}
	}

	protected void hitAggregation(Collection<Hit> hits) {
		for (Hit hit : hits) {
			long timestamp = 0;
			Object value = hit.getValue();
			timestamp = Math.max(timestamp, hit.getTimestamp());
			for (ContextEntry contextEntry : hit.getContext()) {
					//.withUndefinedKey()) {
				Object aValue = result.get(contextEntry);
				result.put(contextEntry, computeAggregate(aValue, value));
			}
			this.resultHits.addAll(result.hitsForChanges(timestamp));
		}
	}

	public void hit(Collection<Hit> hits) {
		if (rateTimeInMillis > 0) {
			hitRate(hits);
		} else {
			hitAggregation(hits);
		}
	}

	void publish() {
		SubscribeUpdater
				.instance()
				.updateAllSubscribers(
						resultHits,
						this.monitor,
						this,
						(this.monitor.rates.size() == 1 && this.monitor.forcePublishRawValues == false));
		resultHits.clear();
	}

	void update() {
		long currentTime = System.currentTimeMillis();

		if (lastTimestampMark == 0) {
			logger.info("skipping scheduler no mark found yet in hits!");
			return;
		}

		Long hitTimestamp = null;
		ContextEntryValue resultHit = null;
		Long resultTimestamp = null;
		while (hitTimestamp == null || currentTime >= hitTimestamp) {
			if (hitTimestamp != null) {
				if (!hits.containsKey(hitTimestamp)) {
					hitTimestamp = hitTimestamp + rateTimeInMillis;
					continue;
				}
				resultHit = hits.get(hitTimestamp);
				resultTimestamp = hitTimestamp;
				hits.remove(hitTimestamp);
				logger.info("got from map values : " + resultHit
						+ " and remove timestamp : " + hitTimestamp);
				hitTimestamp = hitTimestamp + rateTimeInMillis;
			} else {
				// find oldest timestamp in map
				Long mark = lastTimestampMark + rateTimeInMillis;
				while (hits.containsKey(mark)) {
					hitTimestamp = mark;
					mark = mark - rateTimeInMillis;
				}
				logger.info("got oldest timestamp in map : " + hitTimestamp);
				if (hitTimestamp == null) {
					// FIXME should update subscribers with value 0, no
					// data
					break;
				}
			}
		}
		if (resultHit != null) {
			this.resultHits.addAll(resultHit.hitsForChanges(resultTimestamp));
			publish();
		}
	}

}
