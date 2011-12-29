package sim.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sim.monitor.mbean.MBeanManager;
import sim.monitor.naming.Name;
import sim.monitor.timing.TimePeriod;
import sim.monitor.timing.TimeUnit;

/**
 * 
 * Abstract class encapsulating all common properties and methods of a monitor.
 * A monitor is an entity used by developers that are monitoring their application to extract runtime information about business actions.
 * 
 * Each monitor is defined inside a domains and a number of categtories.
 * For example a monitor is part of the domain com.test.monitoring and the resource it is monitoring reside in the module "User Interface"
 * and in the component "Login". This can be expressed using JMX object name notation as "com.test.monitoring:module=User Interface,component=Login"
 * 
 * The monitor has a name and a description.
 * 
 * The data collected through the monitor is processed using a processor. Depending on desired monitor type a different processor is used.
 * For more information about monitor types and processors see implementations of this class.
 * 
 * @author val
 *
 */
public class Monitor<S extends Monitor<S, T>, T> {

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
	
	/**
	 * The processor used to transform the data
	 */
	//protected List<MonitorProcessor> processors = new ArrayList<MonitorProcessor>();
	
	private Aggregation aggregation;
	
	private long startTime = 0;
	private long lastDataTime = 0;
	private TimePeriod timePeriod;
	
	private Number sum = null;
	
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
	
	@SuppressWarnings("unchecked")
	protected final S self() {
		return (S) this;
	}
	
	public S setDescription(String description) {
		this.description = description;
		return self();
	}
	
	public S aggregate(Aggregation aggregation) {
		this.aggregation = aggregation;
		return self();
	}

	public S timeInterval(TimeUnit timeUnit, int timeMultiplier) {
		this.timePeriod = new TimePeriod(timeUnit, timeMultiplier);
		return self();
	}

	public S addStatistic(Aggregation aggregation) {
		return null;
	}

	public S addRate(TimeUnit timeUnit, int timeMultiplier) {
		/*
		if (startTime == -1) {
			startTime = data.getTimestamp();
		}
		if (data.getValue() instanceof Long) {
			valueSum = valueSum + data.getValue().longValue();
		}

		long timeDiff = data.getTimestamp() - startTime;
		double rate = (valueSum * (rateSeconds * 1000)) / (timeDiff == 0 ? timeDiff = 1 : timeDiff);
		notify(name, new Data<Double>(data.getTimestamp(), new Double(rate)));
		*/
		return null;
	}

	/**
	 * Called by instrumentation (code in monitored application that triggers monitors activation) when a new value is available for the monitor.
	 * This method attaches a timestamp to the recorded value and sends it further to processor.
	 * 
	 * @param value the value registered in monitored application. Depending on monitor type can have different types (Long, Double ...)
	 */
	protected void hit_(T value) {
		long timestamp = System.currentTimeMillis();
		Data<T> data = new Data<T>(timestamp, value);
		data.setTimestamp(timestamp);
		data.setValue(value);
		/*
		for (MonitorProcessor processor : processors) {
			processor.input(data);
		}
		*/
		Collection<Data<?>> datas = processData(data);
		exportToMBean(datas);
	}
	
	protected Collection<Data<?>> processData(Data<T> data) {
		List<Data<?>> returns = new ArrayList<Data<?>>();
		
		Data<?> outData = null;
		if (startTime == 0) {
			startTime = data.getTimestamp();
		}
		if (timePeriod != null) {
			if(((data.getTimestamp() - lastDataTime)/1000) > timePeriod.getSeconds() || lastDataTime == 0) {
				outData = new Data<T>(data.getTimestamp(), data.getValue());
				lastDataTime = data.getTimestamp();
			} else {
				return returns;
			}
		}
		
		if (this.aggregation != null) {
			switch (this.aggregation) {
			case Sum :
				outData = sum(data);
				break;
			}
		}
		
		if (timePeriod == null && this.aggregation == null) {
			outData = data;
		}
		
		if (outData != null) {
			returns.add(outData);
		}
		
		return returns;
	}
	
	private void exportToMBean(Collection<Data<?>> datas) {
		if (datas.isEmpty()) {
			return;
		}
		MBeanManager mBeanManager = MBeanManager.instance();
		mBeanManager.update(new Name(domain, name), datas);
	}

	protected Data<?> sum(Data<T> data) {
		if (data.getValue() instanceof Long) {
			if (sum == null) {
				sum = new Long(0);
			}
			sum = ((Long) sum) + ((Long) data.getValue());
		}
		Data<Number> newData = new Data<Number>();
		newData.setTimestamp(data.getTimestamp());
		newData.setValue(sum);
		return newData;
	}
}
