package sim.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;

import sim.monitor.Rate.Type;
import sim.monitor.mbean.MBeanManager;
import sim.monitor.naming.Name;
import sim.monitor.timing.TimePeriod;
import sim.monitor.timing.TimeUnit;

/**
 * 
 * A monitor is an entity used by developers that are monitoring their application to extract runtime information about business actions.
 * 
 * Each monitor is defined inside a context.
 * For example a context could be "com.test.monitoring:module=User Interface,component=Login"
 *  
 * The monitor has a name and a description. Through this name it can be identified by external application using the instrumentation tool. For example a JMX server.
 * 
 * @author val
 *
 */
public class Monitor {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(Monitor.class);
	
	/**
	 * The context of the monitor 
	 */
	protected String context;
	
	/**
	 * The name of the monitor 
	 */
	protected String name;
	
	/**
	 * The description of the monitor
	 */
	protected String description;
	
	private TimePeriod pickOutInterval;
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
	
	/**
	 * Class used for as factory for Monitor creation.
	 * 
	 * @author val
	 *
	 */
	public static class Creator {
		/**
		 * Default context to use when creating monitors
		 */
		private String defaultContext;
		
		private Creator(String defaultContext) {
			this.defaultContext = defaultContext;
		}
		
		/**
		 * Using the context passed as parameter create a new instance of this class
		 * 
		 * @param defaultContext the default context to use whn creating monitors
		 * @return new instance
		 */
		public static Creator useContext(String defaultContext) {
			return new Creator(defaultContext);
		}

		/**
		 * Create a new monitor having the name <code>name</code>
		 * 
		 * @param name the monitor name
		 * @return
		 */
		public Monitor create(String name) {
			return new Monitor(defaultContext, name); 
		}

		/**
		 * Create a new monitor under context <code>context</code>, ignoring the default context of this creator, and with the name <code>name</code>
		 * 
		 * @param context the context
		 * @param name the monitor name
		 * @return instance of monitor
		 */
		public Monitor create(String context, String name) {
			return new Monitor(context, name); 
		}

	}

	/*
	 * Constructs a new monitor
	 * 
	 * @param context the domain of the monitor
	 * @param name the name of the monitor
	 */
	private Monitor(String context, String name) {
		this.context = context;
		this.name = name;
	}

	/**
	 * Sets a description on the monitor
	 * 
	 * @param description the description
	 * @return the monitor
	 */
	public Monitor setDescription(String description) {
		this.description = description;
		return this;
	}
	
	/**
	 * Establishes a picking out interval on the monitors value. A new value is elected only after this interval has passed since the last value
	 * 
	 * @param timeUnit the time unit (seconds, minutes, hours ...)
	 * @param timeMultiplier the time multiplier
	 * @return this monitor
	 */
	public Monitor pickOutInterval(TimeUnit timeUnit, int timeMultiplier) {
		this.pickOutInterval = new TimePeriod(timeUnit, timeMultiplier);
		return this;
	}

	/**
	 * Seton monitor wheter to keep the received values or to perform a delta difference on current valu and last value.
	 * 
	 * @param delta boolean setting if delta is active or not
	 * @return this monitor
	 */
	public Monitor setDelta(boolean delta) {
		this.delta = delta;
		return this;
	}
	
	/**
	 * Set if an statistic on the received values is desired instead of the raw values.
	 * The statistic can be average, count, sum, min or max
	 * 
	 * @return this monitor
	 */
	public Monitor setStatistic(Aggregate aggregate) {
		this.aggregates.add(new Aggregation(aggregate, new Name(context, name, description)));
		return this;
	}

	/**
	 * Sets on the received values a rate statistic.
	 * The rate is calculated as sum, average or count of the values per the interval of time passed as parameter.
	 *  
	 * @param type the type of rate (sum, count or average)
	 * @param rateTimeUnit the time unit
	 * @param multiplier the time multiplier
	 * @return this monitor
	 */
	public Monitor setRateStatistic(Type type, TimeUnit rateTimeUnit, int multiplier) {
		this.rates.add(new Rate(new TimePeriod(rateTimeUnit, multiplier), type, new Name(context, name, description)));
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
	public Monitor addStatistic(Aggregate aggregate, String name, String description) {
		this.aggregates.add(new Aggregation(aggregate, new Name(context, name, description)));
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
	 * @see #setRateStatistic(Type, TimeUnit, int)
	 */
	public Monitor addRateStatistic(Type type, TimeUnit timeUnit, int timeMultiplier, String name, String description) {
		this.rates.add(new Rate(new TimePeriod(timeUnit, timeMultiplier), type, new Name(context, name, description)));
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
		Collection<Data> datas = processData(timestamp, value);
		exportToMBean(datas);
	}

	public void hit(Double value) {
		long timestamp = System.currentTimeMillis();
		Collection<Data> datas = processData(timestamp, value);
		exportToMBean(datas);
	}

	public void hit(Integer value) {
		long timestamp = System.currentTimeMillis();
		Collection<Data> datas = processData(timestamp, value);
		exportToMBean(datas);
	}

	public void hit(String value) {
		long timestamp = System.currentTimeMillis();
		Collection<Data> datas = processData(timestamp, value);
		exportToMBean(datas);
	}

	public void hit(Date value) {
		long timestamp = System.currentTimeMillis();
		Collection<Data> datas = processData(timestamp, value);
		exportToMBean(datas);
	}

	public void hit() {
		long timestamp = System.currentTimeMillis();
		Collection<Data> datas = processData(timestamp, new Long(1));
		exportToMBean(datas);
	}

	protected Collection<Data> processData(long timestamp, Object value) {
		List<Data> returns = new ArrayList<Data>();

		if (startTime == 0) {
			startTime = timestamp;
		}
		if (pickOutInterval != null) {
			if(pickOutInterval.lessThan(timestamp - lastDataTime) || lastDataTime == 0) {
				//value = data.getValue(); value stays with the same data
				lastDataTime = timestamp;
			} else {
				return returns;
			}
		}
		
		if (!namedContains(this.name)) {
			returns.add(new Data(new Name(context, name, description), timestamp, value));
		}
		
		if (aggregatesContainsType(Aggregate.Sum) || aggregatesContainsType(Aggregate.Average) || 
				ratesContainsType(Type.sum) || ratesContainsType(Type.average)) {
			sum(value);
		}
		if (aggregatesContainsType(Aggregate.Count) || aggregatesContainsType(Aggregate.Average) ||
				ratesContainsType(Type.count) || ratesContainsType(Type.average)) {
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
			switch (rate.getType()) {
			case sum :
				logger.debug("sum:" + sum);
				if (sum instanceof Long) {
					rateValue = ((Long) sum) * partialRate;
				} else if (sum instanceof Double) {
					rateValue = ((Double) sum) * partialRate;
				}
				logger.debug("rate:" + rateValue);
				returns.add(new Data(rate.getName(), timestamp, rateValue));
				break;
			case count :
				rateValue = ((Long) count) * partialRate;
				returns.add(new Data(rate.getName(), timestamp, rateValue));
				break;
			case average :
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
	
	private boolean ratesContainsType(Type type) {
		for (Rate rate : this.rates) {
			if (rate.getType().equals(type)) {
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
