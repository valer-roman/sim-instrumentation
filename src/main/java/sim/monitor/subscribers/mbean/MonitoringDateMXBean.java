/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

import java.util.Date;

import javax.management.MXBean;

/**
 * @author val
 *
 */
@MXBean(value = true)
public interface MonitoringDateMXBean extends MonitoringMXBean {

	public Date getValue();

}
