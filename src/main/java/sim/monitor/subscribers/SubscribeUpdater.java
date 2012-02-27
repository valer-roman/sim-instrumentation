/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import sim.monitor.Aggregation;
import sim.monitor.Hit;
import sim.monitor.Tags;
import sim.monitor.timing.TimePeriod;

/**
 * Keep track of all subscribers, inform them about updates
 *
 * @author val
 *
 */
public class SubscribeUpdater {

	private static SubscribeUpdater instance = new SubscribeUpdater();

	private List<Subscriber> subscribers = new ArrayList<Subscriber>();

	public static SubscribeUpdater instance() {
		return instance;
	}

	private SubscribeUpdater() {
		reloadSubscribers();
	}

	public void updateAllSubscribers(Collection<Hit> hits, Tags tags,
			String monitorName, String monitorDescription, String name,
			String description, TimePeriod rateInterval, Aggregation aggregation) {
		for (Subscriber s : subscribers) {
			s.update(hits, tags, monitorName, monitorDescription, name,
					description, rateInterval, aggregation);
		}
	}

	public void reloadSubscribers() {
		this.subscribers.clear();
		ServiceLoader<Subscriber> subscribers = ServiceLoader
				.load(Subscriber.class);
		for (Subscriber s : subscribers) {
			this.subscribers.add(s);
		}
	}
}
