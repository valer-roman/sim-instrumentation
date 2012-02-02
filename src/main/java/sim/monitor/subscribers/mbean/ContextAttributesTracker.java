/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

import java.util.HashMap;
import java.util.Map;

import sim.monitor.Aggregation;
import sim.monitor.ContextEntry;
import sim.monitor.Hit;

/**
 * @author val
 *
 */
public class ContextAttributesTracker {

	private Map<String, AttributeIntervals> attributeTrackedIntervals = new HashMap<String, AttributeIntervals>();

	/**
	 * Track a key-value pair
	 *
	 * @param key
	 * @param value
	 */
	AttributeChanges add(Hit hit, String attributeName, Aggregation aggregation) {
		AttributeChanges attributeChanges = new AttributeChanges(attributeName);
		for (ContextEntry contextEntry : hit.getContext()) {
			String key = contextEntry.getKey();
			Object contextValue = contextEntry.getValue();
			if (!attributeTrackedIntervals.containsKey(attributeName)) {
				attributeTrackedIntervals.put(attributeName,
						new AttributeIntervals(aggregation));
			}
			AttributeIntervals trackedIntervals = attributeTrackedIntervals
					.get(attributeName);
			if (!trackedIntervals.containsKey(key)) {
				trackedIntervals.put(key, new Intervals(trackedIntervals));
			}
			Intervals intervals = trackedIntervals.get(key);
			IntervalChanges intervalChanges = intervals.put(contextValue,
					hit.getValue());
			attributeChanges.addNewAttributes(contextEntry,
					intervalChanges.getAdded());
			attributeChanges.addModifiedAttributes(contextEntry,
					intervalChanges.getModified());
			attributeChanges.addRemovedAttributes(contextEntry,
					intervalChanges.getRemoved());
		}
		return attributeChanges;
	}
}
