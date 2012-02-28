/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import java.util.Collections;
import java.util.List;

/**
 * @author val
 *
 */
public abstract class TaggedMonitorNamer extends MonitorNamer {

	/**
	 * The tags of the monitor
	 */
	protected Tags tags;

	public TaggedMonitorNamer(String name, String description, List<String> tags) {
		super(name, description);

		Collections.sort(tags);
		this.tags = new Tags(tags.toArray(new String[0]));
	}

	/**
	 * @return the tags
	 */
	public Tags getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(Tags tags) {
		this.tags = tags;
	}

}
