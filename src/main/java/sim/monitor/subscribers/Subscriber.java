/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers;

import java.util.Collection;

import sim.monitor.Hit;
import sim.monitor.RateNamer;
import sim.monitor.TaggedMonitorNamer;

/**
 * @author val
 *
 */
public interface Subscriber {

	public void update(Collection<Hit> hits, TaggedMonitorNamer namer,
			RateNamer rateNamer, boolean oneMeasure);

}
