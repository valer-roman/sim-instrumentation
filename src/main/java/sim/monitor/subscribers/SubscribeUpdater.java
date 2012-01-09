/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import sim.monitor.Data;

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

	public void updateAllSubscribers(Collection<Data> datas) {
		for (Subscriber s : subscribers) {
			s.update(datas);
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
