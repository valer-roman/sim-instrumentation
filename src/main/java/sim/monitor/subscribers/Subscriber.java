/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers;

import java.util.Collection;

import sim.monitor.Hit;
import sim.monitor.Tags;

/**
 * @author val
 *
 */
public interface Subscriber {

	public void update(Collection<Hit> hits, Tags tags, String monitorName,
			String monitorDescription, String name, String description);

}
