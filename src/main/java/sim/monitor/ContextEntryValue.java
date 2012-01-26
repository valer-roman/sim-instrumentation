/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author val
 *
 */
public class ContextEntryValue {

	private Map<ContextEntry, Object> contextEntryValues = new HashMap<ContextEntry, Object>();
	private Set<ContextEntry> changedContextEntries = new HashSet<ContextEntry>();

	void put(ContextEntry contextEntry, Object value) {
		if (value == null) {
			value = new Long(0);
		}
		contextEntryValues.put(contextEntry, value);
		changedContextEntries.add(contextEntry);
	}

	Object get(ContextEntry contextEntry) {
		Object value = contextEntryValues.get(contextEntry);
		return value == null ? new Long(0) : value;
	}

	Collection<Hit> hitsForChanges(long timestamp) {
		Map<Object, Context> valueContexts = new HashMap<Object, Context>();
		for (ContextEntry contextEntry : contextEntryValues.keySet()) {
			if (!changedContextEntries.contains(contextEntry)) {
				continue;
			}
			Object aValue = contextEntryValues.get(contextEntry);
			if (!valueContexts.containsKey(aValue)) {
				valueContexts.put(aValue, new Context());
			}
			if (contextEntry != ContextEntry.UNDEFINED) {
				valueContexts.get(aValue).add(contextEntry);
			}
		}

		Collection<Hit> hits = new ArrayList<Hit>();
		for (Object aValue : valueContexts.keySet()) {
			hits.add(new Hit(timestamp, aValue, valueContexts.get(aValue)));
		}
		changedContextEntries.clear();
		return hits;
	}

	void reset() {
		contextEntryValues.clear();
	}
}
