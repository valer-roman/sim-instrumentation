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
	 * A human readable rate interval (1 sec., 2 hours ...). Empty if missing
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
