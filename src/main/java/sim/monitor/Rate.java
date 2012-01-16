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
public abstract class Rate extends Publisher {

	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(Rate.class);

	private Monitor monitor;

	private TimePeriod rateTime;

	private long rateTimeInMillis = 0;

	private Object result = new Long(0);

	private Map<Long, Hit> hits = new ConcurrentHashMap<Long, Hit>();

	private long lastTimestampMark = 0;

	protected Collection<Hit> resultHits = new ArrayList<Hit>();

	abstract Object computeAggregate(Object result, Object value);

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
	public TimePeriod getRateTime() {
		return rateTime;
	}

	/**
	 * @param rateTime
	 *            the rateTime to set
	 */
	public void setRateTime(TimePeriod rateTime) {
		this.rateTime = rateTime;
		this.rateTimeInMillis = this.rateTime.getSeconds() * 1000;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.Statistic#getSuffix()
	 */
	@Override
	String getSuffix() {
		if (rateTime != null) {
			return rateTime.toString();
		}
		return null;
	}

	protected void resetValues() {
		result = new Long(0);
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
			result = computeAggregate(result, value);
			this.hits.put(timestamp, new Hit(timestamp, result));
			logger.info("put in map : " + timestamp + "," + result);
		}
	}

	protected void hitAggregation(Collection<Hit> hits) {
		for (Hit hit : hits) {
			long timestamp = 0;
			Object value = hit.getValue();
			timestamp = Math.max(timestamp, hit.getTimestamp());
			result = computeAggregate(result, value);
			this.resultHits.add(new Hit(timestamp, result));
		}
	}

	public void hit(Collection<Hit> hits) {
		if (rateTimeInMillis > 0) {
			hitRate(hits);
		} else {
			hitAggregation(hits);
		}
		/*
		for (Hit hit : hits) {
			long timestamp = 0;
			Object value = hit.getValue();
			if (rateTimeInMillis > 0) {
				if (lastTimestampMark == 0) {
					lastTimestampMark = hit.getTimestamp();
				}
				if ((hit.getTimestamp() - lastTimestampMark) > rateTimeInMillis) {
					System.out.println("RESET");
					lastTimestampMark = lastTimestampMark + rateTimeInMillis;
					resetValues();
				}
				timestamp = lastTimestampMark + rateTimeInMillis;
			} else {
				timestamp = Math.max(timestamp, hit.getTimestamp());
			}
			result = computeAggregate(result, value);
			if (rateTimeInMillis > 0) {
				this.hits.put(timestamp, new Hit(timestamp, result));
				logger.info("put in map : " + timestamp + "," + result);
			} else {
				this.resultHits.add(new Hit(timestamp, result));
			}
		}
		 */
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
		SubscribeUpdater.instance().updateAllSubscribers(resultHits,
				monitor.getTags(), name, getDescription());
		resultHits.clear();
	}

	void update() {
		long currentTime = System.currentTimeMillis();

		if (lastTimestampMark == 0) {
			logger.info("skipping scheduler no mark found yet in hits!");
			return;
		}

		Long hitTimestamp = null;
		Hit resultHit = null;
		while (hitTimestamp == null || currentTime >= hitTimestamp) {
			if (hitTimestamp != null) {
				if (!hits.containsKey(hitTimestamp)) {
					hitTimestamp = hitTimestamp + rateTimeInMillis;
					continue;
				}
				resultHit = hits.get(hitTimestamp);
				hits.remove(hitTimestamp);
				logger.info("got from map value : " + resultHit.getValue()
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
					// FIXME should update sbuscribers with value 0, no
					// data
					break;
				}
			}
		}
		if (resultHit != null) {
			resultHits.add(resultHit);
			publish();
		}
	}

}
