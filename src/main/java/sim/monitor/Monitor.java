package sim.monitor;

import java.util.ArrayList;
import java.util.Collection;
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
 * Each monitor is defined inside a domains and categories. The domain and categories together form the context of the monitor.
 * A category is represented through a key-value. The key is the type of the category and the value is the name of the category.
 * For example a monitor could be part of the domain com.test.monitoring and the resource it is monitoring reside in the module "User Interface"
 * and in the component "Login". This can be expressed using JMX object name notation as "com.test.monitoring:module=User Interface,component=Login"
 * Here the categories are module=User and component=Login.
 *  
 * The monitor has a name and a description. Through this name it can be identified by external application using the instrumentation tool. For example a JMX server.
 * 
 * @author val
 *
 */
public class Monitor<S extends Monitor<S, T>, T> {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(Monitor.class);
	
	/**
	 * The domain of the monitor 
	 */
	protected Domain domain;
	
	/**
	 * The name of the monitor 
	 */
	protected String name;
	
	/**
	 * The description of the monitor
	 */
	protected String description;
	
	private List<Aggregation> aggregates = new ArrayList<Aggregation>();
	private List<Rate> rates = new ArrayList<Rate>();
	
	private long startTime = 0;
	private long lastDataTime = 0;
	private TimePeriod pickOutInterval;
	
	private Number sum = null;
	private Double average = null;
	private Long count = null;
	private Number min = null;
	private Number max= null;
	
	
	/**
	 * Constructs a new monitor
	 * 
	 * @param domain the domain of the monitor
	 * @param name the name of the monitor
	 */
	Monitor(Domain domain, String name) {
		this.domain = domain;
		this.name = name;
	}

	/**
	 * This method returns even at compile time the actual class of this object.
	 * Used to construct the fluent api.
	 * 
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected final S self() {
		return (S) this;
	}

	/**
	 * Sets a description on the monitor
	 * 
	 * @param description the description
	 * @return the monitor
	 */
	public S setDescription(String description) {
		this.description = description;
		return self();
	}
	
	/**
	 * Establishes a picking out interval on the monitors value. A new value is elected only after this interval has passed since the last value
	 * 
	 * @param timeUnit the time unit (seconds, minuts, hours ...)
	 * @param timeMultiplier the time multiplier
	 * @return this monitor
	 */
	public S pickOutInterval(TimeUnit timeUnit, int timeMultiplier) {
		this.pickOutInterval = new TimePeriod(timeUnit, timeMultiplier);
		return self();
	}

	/**
	 * Calculates the average of all the received values.
	 * The data is recorded under the name of the monitor.
	 * 
	 * @return the monitor
	 */
	public S average() {
		this.aggregates.add(new Aggregation(Aggregate.Average, new Name(domain, name, description)));
		return self();
	}

	/**
	 * Adds the average statistic to the monitor. 
	 * The resulted value is recorder under a new statistic having the name passed as parameter.
	 * 
	 * @param name the statistic name
	 * @param description description of statistic
	 * @return
	 * 
	 * @see #average()
	 */
	public S average(String name, String description) {
		this.aggregates.add(new Aggregation(Aggregate.Average, new Name(domain, name, description)));
		return self();
	}

	/**
	 * The output value value of the monitor will be the count of how many hits it had.
	 * No new statistic iscreated the result ata is registered under the monitor's name
	 * 
	 * @return the monitor
	 */
	public S count() {
		this.aggregates.add(new Aggregation(Aggregate.Count, new Name(domain, name, description)));
		return self();		
	}
	
	/**
	 * Same as {@link #count()} only that a new statistic is created with {@code name}
	 * 
	 * @param name the name of the statistic
	 * @param description the description of statistic
	 * @return the monitor
	 * 
	 * @see #count()
	 */
	public S count(String name, String description) {
		this.aggregates.add(new Aggregation(Aggregate.Count, new Name(domain, name, description)));
		return self();
	}

	public S sum() {
		this.aggregates.add(new Aggregation(Aggregate.Sum, new Name(domain, name, description)));
		return self();
	}

	public S sum(String name, String description) {
		this.aggregates.add(new Aggregation(Aggregate.Sum, new Name(domain, name, description)));
		return self();
	}

	public S max() {
		this.aggregates.add(new Aggregation(Aggregate.Maximum, new Name(domain, name, description)));
		return self();
	}

	public S max(String name, String description) {
		this.aggregates.add(new Aggregation(Aggregate.Maximum, new Name(domain, name, description)));
		return self();
	}

	public S min() {
		this.aggregates.add(new Aggregation(Aggregate.Minimum, new Name(domain, name, description)));
		return self();
	}

	public S min(String name, String description) {
		this.aggregates.add(new Aggregation(Aggregate.Minimum, new Name(domain, name, description)));
		return self();
	}

	public S sumRate(TimeUnit timeUnit, int timeMultiplier) {
		this.rates.add(new Rate(new TimePeriod(timeUnit, timeMultiplier), Type.sum, new Name(domain, name, description)));
		return self();		
	}
	
	public S sumRate(TimeUnit timeUnit, int timeMultiplier, String name, String description) {
		this.rates.add(new Rate(new TimePeriod(timeUnit, timeMultiplier), Type.sum, new Name(domain, name, description)));
		return self();
	}

	public S countRate(TimeUnit timeUnit, int timeMultiplier) {
		this.rates.add(new Rate(new TimePeriod(timeUnit, timeMultiplier), Type.count, new Name(domain, name, description)));
		return self();
	}

	public S countRate(TimeUnit timeUnit, int timeMultiplier, String name, String description) {
		this.rates.add(new Rate(new TimePeriod(timeUnit, timeMultiplier), Type.count, new Name(domain, name, description)));
		return self();
	}

	public S averageRate(TimeUnit timeUnit, int timeMultiplier) {
		this.rates.add(new Rate(new TimePeriod(timeUnit, timeMultiplier), Type.average, new Name(domain, name, description)));
		return self();
	}

	public S averageRate(TimeUnit timeUnit, int timeMultiplier, String name, String description) {
		this.rates.add(new Rate(new TimePeriod(timeUnit, timeMultiplier), Type.average, new Name(domain, name, description)));
		return self();
	}

	/**
	 * Called by instrumentation (code in monitored application that triggers monitors activation) when a new value is available for the monitor.
	 * This method attaches a timestamp to the recorded value and sends it further to processor.
	 * 
	 * @param value the value registered in monitored application. Depending on monitor type can have different types (Long, Double ...)
	 */
	protected void hit_(T value) {
		long timestamp = System.currentTimeMillis();
		Collection<Data<?>> datas = processData(timestamp, value);
		exportToMBean(datas);
	}
	
	protected Collection<Data<?>> processData(long timestamp, T value) {
		List<Data<?>> returns = new ArrayList<Data<?>>();

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
			returns.add(new Data<T>(new Name(domain, name, description), timestamp, value));
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
	
	private void exportToMBean(Collection<Data<?>> datas) {
		if (datas.isEmpty()) {
			return;
		}
		MBeanManager mBeanManager = MBeanManager.instance();
		mBeanManager.update(datas);
	}

	protected Collection<Data<?>> processAggregates(long timestamp, T value) {
		List<Data<?>> returns = new ArrayList<Data<?>>();
		
		for (Aggregation aggregation : this.aggregates) {
			switch (aggregation.getAggregate()) {
			case Sum :
				if (this.sum != null) {
					returns.add(new Data<Number>(aggregation.getName(), timestamp, this.sum));
				}
				break;
			case Count :
				if (this.count != null) {
					returns.add(new Data<Number>(aggregation.getName(), timestamp, this.count));
				}
				break;
			case Average :
				this.average = computeAverage();
				if (this.average != null) {
					returns.add(new Data<Number>(aggregation.getName(), timestamp, this.average));
				}
				break;
			case Maximum :
				max(value);
				if (this.max != null) {
					returns.add(new Data<Number>(aggregation.getName(), timestamp, this.max));
				}
				break;
			case Minimum :
				min(value);
				if (this.min != null) {
					returns.add(new Data<Number>(aggregation.getName(), timestamp, this.min));
				}
				break;
			}
		}
		
		return returns;
	}
	
	protected void sum(T value) {
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
	
	protected void count(T value) {
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
	
	protected void max(T value) {
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

	protected void min(T value) {
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

	protected Collection<Data<?>> processRates(long timestamp, T value) {
		List<Data<?>> returns = new ArrayList<Data<?>>();
		
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
				returns.add(new Data<Double>(rate.getName(), timestamp, rateValue));
				break;
			case count :
				rateValue = ((Long) count) * partialRate;
				returns.add(new Data<Double>(rate.getName(), timestamp, rateValue));
				break;
			case average :
				if (sum instanceof Long) {
					rateValue = (((Long) sum)/this.count) * partialRate;
				} else if (sum instanceof Double) {
					rateValue = (((Double) sum)/this.count) * partialRate;
				}
				returns.add(new Data<Double>(rate.getName(), timestamp, rateValue));
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
