/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers;

import java.util.Collection;

import sim.monitor.Aggregation;
import sim.monitor.Hit;
import sim.monitor.Tags;
import sim.monitor.timing.TimePeriod;

/**
 * @author val
 *
 */
public interface Subscriber {

	public void update(Collection<Hit> hits, Tags tags, String monitorName,
			String monitorDescription, String name, String description,
			TimePeriod rateInterval, Aggregation aggregation);

}
