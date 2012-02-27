/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

import java.util.ArrayList;
import java.util.List;

import sim.monitor.ContextEntry;

/**
 * @author val
 *
 */
public class AttributeChanges {

	private String name;

	private List<Attribute> added = new ArrayList<Attribute>();
	private List<Attribute> modified = new ArrayList<Attribute>();
	private List<String> removed = new ArrayList<String>();

	public AttributeChanges(String name) {
		this.name = name;
	}

	List<String> getRemovedAttributes() {
		return removed;
	}

	List<Attribute> getAddedAttributes() {
		return added;
	}

	List<Attribute> getModifiedAttributes() {
		return modified;
	}

	List<Attribute> getAddedAndModifiedAttributes() {
		List<Attribute> list = new ArrayList<Attribute>(added);
		list.addAll(modified);
		return list;
	}

	private String getAttributeName(ContextEntry contextEntry) {
		String name = this.name;
		if (!contextEntry.equals(ContextEntry.UNDEFINED)) {
			name = this.name + "." + contextEntry.getKey();
			// + "." + entry.getValue());
		}
		return name;
	}

	void addNewAttributes(ContextEntry contextEntry, List<Interval> intervals) {
		String aName = getAttributeName(contextEntry);
		for (Interval interval : intervals) {
			String attrName = aName + interval.toString();
			Attribute attribute = new Attribute(attrName,
					interval.getValue());
			added.add(attribute);
		}
	}

	void addModifiedAttributes(ContextEntry contextEntry,
			List<Interval> intervals) {
		String aName = getAttributeName(contextEntry);
		for (Interval interval : intervals) {
			String attrName = aName + interval.toString();
			Attribute attribute = new Attribute(attrName, interval.getValue());
			modified.add(attribute);
		}
	}

	void addRemovedAttributes(ContextEntry contextEntry,
			List<Interval> intervals) {
		String aName = getAttributeName(contextEntry);
		for (Interval interval : intervals) {
			String attrName = aName + interval.toString();
			removed.add(attrName);
		}
	}

	public boolean hasNewOrRemovedAttributes() {
		return !added.isEmpty() || !removed.isEmpty();
	}
}
