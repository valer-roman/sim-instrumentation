/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

import java.math.BigDecimal;

import javax.management.MXBean;

/**
 * @author val
 *
 */
@MXBean(value = true)
public interface MonitoringBigDecimalMXBean extends MonitoringMXBean {

	public BigDecimal getValue();

}
