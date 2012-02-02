/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sim.monitor.Aggregation;
import sim.monitor.Hit;
import sim.monitor.Tags;

/**
 * @author val
 *
 */
public class MockSubscriber implements Subscriber {

	public static MockSubscriber instance;

	public List<Object> values = new ArrayList<Object>();

	public MockSubscriber() {
		MockSubscriber.instance = this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see sim.monitor.subscribers.Subscriber#update(java.util.Collection,
	 * sim.monitor.Tags, java.lang.String, java.lang.String)
	 */
	public void update(Collection<Hit> hits, Tags tags, String monitorName,
			String monitorDescription, String name, String description,
			Aggregation aggregation) {
		Hit hit = hits.iterator().next();
		System.out.println(hit.getValue());
		values.add(hit.getValue());
	}

}
