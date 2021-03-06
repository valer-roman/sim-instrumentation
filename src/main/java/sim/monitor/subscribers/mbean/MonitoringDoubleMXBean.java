/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

import javax.management.MXBean;

/**
 * @author val
 *
 */
@MXBean(value = true)
public interface MonitoringDoubleMXBean extends MonitoringMXBean {

	public Double getValue();

}
