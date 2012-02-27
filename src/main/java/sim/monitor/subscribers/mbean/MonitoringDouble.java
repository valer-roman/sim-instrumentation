/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

/**
 * @author val
 *
 */
public class MonitoringDouble extends Monitoring implements
		MonitoringDoubleMXBean {

	/*
	 * (non-Javadoc)
	 *
	 * @see sim.monitor.subscribers.mbean.MonitoringDoubleMXBean#getValue()
	 */
	@Override
	public Double getValue() {
		return (Double) value;
	}

}
