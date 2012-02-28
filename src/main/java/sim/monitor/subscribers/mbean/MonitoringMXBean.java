/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

import java.util.List;
import java.util.Map;

import javax.management.MXBean;

/**
 * @author val
 *
 */
@MXBean(value = false)
public interface MonitoringMXBean {

	/**
	 *
	 * @return
	 */
	public String getType();

	public String getDescription();

	public String getTags();

	/**
	 * RAte interval in seconds
	 *
	 *
	 * @return
	 */
	public long getRateInterval();

	/**
	 * The aggregation performed on the value
	 *
	 * @return
	 */
	public String getAggregation(); // Count, Sum, Min, Max, Average

	// NOT USED yet
	public String getUnit();

	/**
	 *
	 * @return
	 */
	public Map<String, List<ContextComposite>> getContext();

}
