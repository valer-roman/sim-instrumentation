/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

/**
 * @author val
 *
 */
public class MonitoringLong extends Monitoring implements MonitoringLongMXBean {

	/*
	 * (non-Javadoc)
	 *
	 * @see sim.monitor.subscribers.mbean.MonitoringLongMXBean#getValue()
	 */
	@Override
	public Long getValue() {
		return (Long) value;
	}

}
