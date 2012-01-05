package sim.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;

import sim.monitor.mbean.MBeanManager;
import sim.monitor.naming.Container;
import sim.monitor.naming.Name;
import sim.monitor.timing.TimePeriod;
import sim.monitor.timing.TimeUnit;

/**
 * 
 * A monitor is an entity used by developers that are monitoring their
 * application to extract runtime information about business actions.
 * 
 * Each monitor is defined inside a context. For example a context could be
 * "com.test.monitoring:module=User Interface,component=Login"
 * 
 * The monitor has a name and a description. Through this name it can be
 * identified by external application using the instrumentation tool. For
 * example a JMX server.
 * 
 * @author val
 * 
 */
public class Monitor {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(Monitor.class);

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

	private MonitorThreadPool threadPool = MonitorThreadPool.instance();

	private TimePeriod timeIntervalSampler;
	private boolean delta = false;

	private List<Aggregation> aggregates = new ArrayList<Aggregation>();
	private List<Rate> rates = new ArrayList<Rate>();

	private long startTime = 0;
	private long lastDataTime = 0;

	private Number sum = null;
	private Double average = null;
	private Long count = null;
	private Number min = null;
	private Number max= null;

	Monitor(Container container) {
		this.container = container;
	}

	void setName(String name) {
		this.name = name;
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
	void setTimeIntervalSampler(TimeUnit timeUnit, int timeMultiplier) {
		this.timeIntervalSampler = new TimePeriod(timeUnit, timeMultiplier);
	}

	/*
	 * Set on monitor wheter to keep the received values or to perform a delta
	 * difference on current valu and last value.
	 * 
	 * @param delta boolean setting if delta is active or not
	 * 
	 * @return this monitor
	 */
	Monitor useDelta(boolean delta) {
		this.delta = delta;
		return this;
	}

	/**
	 * Set if an statistic on the received values is desired instead of the raw values.
	 * The statistic can be average, count, sum, min or max
	 * 
	 * @return this monitor
	 */
	Monitor addStatistic(Aggregate aggregate) {
		this.aggregates.add(new Aggregation(aggregate, new Name(container, name, description)));
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
	Monitor addRateStatistic(Aggregate aggregate, TimeUnit rateTimeUnit,
			int multiplier) {
		this.rates.add(new Rate(new TimePeriod(rateTimeUnit, multiplier),
				aggregate, new Name(container, name, description)));
		return this;
	}

	/**
	 * Adds a new statistic to the monitor.
	 * The statistic can be count, sum, average, min or max.
	 * The resulted value is recorder under a new statistic having the name passed as parameter.
	 * 
	 * @param name the statistic name
	 * @param description description of statistic
	 * @return this monitor
	 * 
	 * @see #setStatistic(Aggregate)
	 */
	Monitor addStatistic(Aggregate aggregate, String name, String description) {
		this.aggregates.add(new Aggregation(aggregate, new Name(container, name, description)));
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
	Monitor addRateStatistic(Aggregate aggregate, TimeUnit timeUnit,
			int timeMultiplier, String name, String description) {
		this.rates.add(new Rate(new TimePeriod(timeUnit, timeMultiplier),
				aggregate, new Name(container, name, description)));
		return this;
	}

	/**
	 * Called by instrumentation (code in monitored application that triggers monitors activation) when a new value is available for the monitor.
	 * This method attaches a timestamp to the recorded value and sends it further to processor.
	 * 
	 * @param value the value registered in monitored application. Depending on monitor type can have different types (Long, Double ...)
	 */
	public void hit(Long value) {
		long timestamp = System.currentTimeMillis();
		threadPool.execute(new Processor(timestamp, value));
	}

	public void hit(Double value) {
		long timestamp = System.currentTimeMillis();
		threadPool.execute(new Processor(timestamp, value));
	}

	public void hit(Integer value) {
		long timestamp = System.currentTimeMillis();
		threadPool.execute(new Processor(timestamp, value));
	}

	public void hit(String value) {
		long timestamp = System.currentTimeMillis();
		threadPool.execute(new Processor(timestamp, value));
	}

	public void hit(Date value) {
		long timestamp = System.currentTimeMillis();
		threadPool.execute(new Processor(timestamp, value));
	}

	public void hit() {
		long timestamp = System.currentTimeMillis();
		threadPool.execute(new Processor(timestamp, new Long(1)));
		/*
		Collection<Data> datas = processData(timestamp, new Long(1));
		exportToMBean(datas);
		 */
	}

	class Processor implements Runnable {

		private long timestamp;
		private Object value;

		Processor(long timestamp, Object value) {
			this.timestamp = timestamp;
			this.value = value;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			Collection<Data> datas = processData(timestamp, value);
			synchronized (container) {
				exportToMBean(datas);
			}
		}

	}

	protected Collection<Data> processData(long timestamp, Object value) {
		List<Data> returns = new ArrayList<Data>();

		if (startTime == 0) {
			startTime = timestamp;
		}
		if (timeIntervalSampler != null) {
			if(timeIntervalSampler.lessThan(timestamp - lastDataTime) || lastDataTime == 0) {
				//value = data.getValue(); value stays with the same data
				lastDataTime = timestamp;
			} else {
				return returns;
			}
		}

		if (!namedContains(this.name)) {
			returns.add(new Data(new Name(container, name, description), timestamp, value));
		}

		if (aggregatesContainsType(Aggregate.Sum) || aggregatesContainsType(Aggregate.Average) ||
				ratesContainsType(Aggregate.Sum)
				|| ratesContainsType(Aggregate.Average)) {
			sum(value);
		}
		if (aggregatesContainsType(Aggregate.Count) || aggregatesContainsType(Aggregate.Average) ||
				ratesContainsType(Aggregate.Count)
				|| ratesContainsType(Aggregate.Average)) {
			count(value);
		}

		returns.addAll(processAggregates(timestamp, value));

		returns.addAll(processRates(timestamp, value));

		return returns;
	}

	private void exportToMBean(Collection<Data> datas) {
		if (datas.isEmpty()) {
			return;
		}
		MBeanManager mBeanManager = MBeanManager.instance();
		mBeanManager.update(datas);
	}

	protected Collection<Data> processAggregates(long timestamp, Object value) {
		List<Data> returns = new ArrayList<Data>();

		for (Aggregation aggregation : this.aggregates) {
			switch (aggregation.getAggregate()) {
			case Sum :
				if (this.sum != null) {
					returns.add(new Data(aggregation.getName(), timestamp, this.sum));
				}
				break;
			case Count :
				if (this.count != null) {
					returns.add(new Data(aggregation.getName(), timestamp, this.count));
				}
				break;
			case Average :
				this.average = computeAverage();
				if (this.average != null) {
					returns.add(new Data(aggregation.getName(), timestamp, this.average));
				}
				break;
			case Maximum :
				max(value);
				if (this.max != null) {
					returns.add(new Data(aggregation.getName(), timestamp, this.max));
				}
				break;
			case Minimum :
				min(value);
				if (this.min != null) {
					returns.add(new Data(aggregation.getName(), timestamp, this.min));
				}
				break;
			}
		}

		return returns;
	}

	protected void sum(Object value) {
		if (!(value instanceof Number)) {
			return;
		}
		if (value instanceof Long) {
			if (sum == null) {
				sum = new Long(0);
			}
			sum = ((Long) sum) + ((Long) value);
		}
	}

	protected void count(Object value) {
		if (count == null) {
			count = new Long(0);
		}
		count++;
	}

	protected Double computeAverage() {
		if (sum instanceof Long) {
			return (((Long) sum * 1.0) / count);
		}
		return null;
	}

	protected void max(Object value) {
		if (!(value instanceof Number)) {
			return;
		}
		if (value instanceof Long) {
			if (max == null) {
				max = (Long) value;
			}
			if ((Long) value > (Long) max) {
				max = (Long) value;
			}
		}
	}

	protected void min(Object value) {
		if (!(value instanceof Number)) {
			return;
		}
		if (value instanceof Long) {
			if (min == null) {
				min = (Long) value;
			}
			if ((Long) value < (Long) min) {
				min = (Long) value;
			}
		}
	}

	protected Collection<Data> processRates(long timestamp, Object value) {
		List<Data> returns = new ArrayList<Data>();

		long timeDiff = timestamp - startTime;
		for (Rate rate : rates) {
			double partialRate = 1.0;
			if (timeDiff != 0) {
				partialRate = (rate.getRateTime().getSeconds() * 1000.0) / timeDiff;
			}
			logger.debug("timeDiff:" + (timeDiff / 1000.0));
			logger.debug("patialRate:" + partialRate);
			double rateValue = 0.0;
			switch (rate.getAggregate()) {
			case Sum:
				logger.debug("sum:" + sum);
				if (sum instanceof Long) {
					rateValue = ((Long) sum) * partialRate;
				} else if (sum instanceof Double) {
					rateValue = ((Double) sum) * partialRate;
				}
				logger.debug("rate:" + rateValue);
				returns.add(new Data(rate.getName(), timestamp, rateValue));
				break;
			case Count:
				rateValue = count * partialRate;
				returns.add(new Data(rate.getName(), timestamp, rateValue));
				break;
			case Average:
				if (sum instanceof Long) {
					rateValue = (((Long) sum)/this.count) * partialRate;
				} else if (sum instanceof Double) {
					rateValue = (((Double) sum)/this.count) * partialRate;
				}
				returns.add(new Data(rate.getName(), timestamp, rateValue));
				break;
			}
		}

		return returns;
	}

	private boolean ratesContainsType(Aggregate aggregate) {
		for (Rate rate : this.rates) {
			if (rate.getAggregate().equals(aggregate)) {
				return true;
			}
		}
		return false;
	}

	private boolean aggregatesContainsType(Aggregate aggregate) {
		for (Aggregation aggregation : this.aggregates) {
			if (aggregation.getAggregate().equals(aggregate)) {
				return true;
			}
		}
		return false;
	}

	private boolean namedContains(String name) {
		for (Statistic statistic : aggregates) {
			if (statistic.getName().getName().equals(name)) {
				return true;
			}
		}
		for (Statistic statistic : rates) {
			if (statistic.getName().getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

}
