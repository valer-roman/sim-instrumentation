/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author val
 *
 */
public class IntervalChanges {

	private List<Interval> added = new ArrayList<Interval>();
	private List<Interval> modified = new ArrayList<Interval>();
	private List<Interval> removed = new ArrayList<Interval>();

	void add(IntervalChanges intervalChanges) {
		added.addAll(intervalChanges.added);
		modified.addAll(intervalChanges.modified);
		removed.addAll(intervalChanges.removed);
	}

	void added(Interval interval) {
		added.add(interval);
	}

	void modified(Interval interval) {
		modified.add(interval);
	}

	void removed(Interval interval) {
		removed.add(interval);
	}

	public List<Interval> getAdded() {
		return added;
	}

	public List<Interval> getModified() {
		return modified;
	}

	public List<Interval> getRemoved() {
		return removed;
	}

}
