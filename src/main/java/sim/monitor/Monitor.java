package sim.monitor;

import java.util.Date;

import org.slf4j.LoggerFactory;

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
 * @author val
 * 
 */
public class Monitor {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(Monitor.class);

	private MonitorCore monitorCore;

	Monitor(MonitorCore monitorCore) {
		this.monitorCore = monitorCore;
	}

	/**
	 * Called by instrumentation (code in monitored application that triggers monitors activation) when a new value is available for the monitor.
	 * This method attaches a timestamp to the recorded value and sends it further to processor.
	 * 
	 * @param value the value registered in monitored application. Depending on monitor type can have different types (Long, Double ...)
	 */
	public void hit(Long value) {
		long timestamp = System.currentTimeMillis();
		this.monitorCore.acceptHit(timestamp, value);
		// threadPool.execute(new Processor(timestamp, value));
	}

	public void hit(Double value) {
		long timestamp = System.currentTimeMillis();
		this.monitorCore.acceptHit(timestamp, value);
		// threadPool.execute(new Processor(timestamp, value));
	}

	public void hit(Integer value) {
		long timestamp = System.currentTimeMillis();
		this.monitorCore.acceptHit(timestamp, value);
		// threadPool.execute(new Processor(timestamp, value));
	}

	public void hit(String value) {
		long timestamp = System.currentTimeMillis();
		this.monitorCore.acceptHit(timestamp, value);
		// threadPool.execute(new Processor(timestamp, value));
	}

	public void hit(Date value) {
		long timestamp = System.currentTimeMillis();
		this.monitorCore.acceptHit(timestamp, value);
		// threadPool.execute(new Processor(timestamp, value));
	}

	public void hit() {
		long timestamp = System.currentTimeMillis();
		this.monitorCore.acceptHit(timestamp, new Long(1));
		// threadPool.execute(new Processor(timestamp, new Long(1)));
	}

}
