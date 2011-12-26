package sim.monitor;

import java.util.ArrayList;
import java.util.List;

import sim.monitor.internal.MonitorProcessor;
import sim.monitor.internal.data.Data;
import sim.monitor.internal.data.DataValueType;
import sim.monitor.naming.Domain;

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
public abstract class Monitor {

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
	protected List<MonitorProcessor> processors = new ArrayList<MonitorProcessor>();
	
	/**
	 * Constructs a new monitor
	 * 
	 * @param domain the domain of the monitor
	 * @param name the name of the monitor
	 * @param description the description of the monitor
	 */
	public Monitor(Domain domain, String name, String description) {
		this.domain = domain;
	}
	
	/**
	 * Called by instrumentation (code in monitored application that triggers monitors activation) when a new value is available for the monitor.
	 * This method attaches a timestamp to the recorded value and sends it further to processor.
	 * 
	 * @param value the value registered in monitored application. Depending on monitor type can have different types (Long, Double ...)
	 */
	protected void hit(DataValueType value) {
		long timestamp = System.currentTimeMillis();
		Data data = new Data(timestamp, value);
		data.setTimestamp(timestamp);
		data.setValue(value);
		for (MonitorProcessor processor : processors) {
			processor.input(data);
		}
	}
}
