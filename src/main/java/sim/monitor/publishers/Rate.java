/**
 * 
 */
package sim.monitor.publishers;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.LoggerFactory;

import sim.monitor.Data;
import sim.monitor.MeasureUtil;
import sim.monitor.naming.Name;
import sim.monitor.timing.TimePeriod;

/**
 * @author val
 *
 */
public class Rate extends Publisher {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(Rate.class);

	private TimePeriod rateTime;
	private Aggregate aggregate;

	private Object result;

	private long startTime = 0;
	private Object sum = new Long(0);
	private Long count = new Long(0);

	public Rate(TimePeriod rateTime, Aggregate aggregate, Name name) {
		super(name);
		this.rateTime = rateTime;
		this.aggregate = aggregate;
	}

	/**
	 * @return the rateTime
	 */
	public TimePeriod getRateTime() {
		return rateTime;
	}

	/**
	 * @param rateTime the rateTime to set
	 */
	public void setRateTime(TimePeriod rateTime) {
		this.rateTime = rateTime;
	}

	/**
	 * @return the aggregate
	 */
	public Aggregate getAggregate() {
		return aggregate;
	}

	/**
	 * @param aggregate
	 *            the aggregate to set
	 */
	public void setAggregate(Aggregate aggregate) {
		this.aggregate = aggregate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.Statistic#getSuffix()
	 */
	@Override
	public String getSuffix() {
		return "RATE";
	}

	@Override
	protected Collection<Data> processHit(Collection<Data> datas) {
		Collection<Data> results = new ArrayList<Data>();

		for (Data data : datas) {
			long timestamp = data.getTimestamp();
			if (startTime == 0) {
				startTime = timestamp;
			}

			long timeDiff = timestamp - startTime;

			double partialRate = 1.0;
			if (timeDiff != 0) {
				partialRate = (getRateTime().getSeconds() * 1000.0)
						/ timeDiff;
			}
			logger.debug("timeDiff:" + (timeDiff / 1000.0));
			logger.debug("patialRate:" + partialRate);
			switch (getAggregate()) {
			case Sum:
				logger.debug("sum:" + sum);
				sum = MeasureUtil.sum(sum, data.getValue());
				result = MeasureUtil.multiply(sum, partialRate);
				logger.debug("rate:" + result);
				break;
			case Count:
				result = count * partialRate;
				break;
			case Average:
				sum = MeasureUtil.sum(sum, data.getValue());
				count = count + 1;
				result = MeasureUtil.multiply(MeasureUtil.divide(sum, count),
						partialRate);
				break;
			}
			results.add(new Data(getName(), data.getTimestamp(), result));
		}
		return results;
	}

}
