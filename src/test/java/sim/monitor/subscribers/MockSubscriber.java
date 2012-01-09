/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers;

import java.util.Collection;

import sim.monitor.Data;

/**
 * @author val
 *
 */
public class MockSubscriber implements Subscriber {

	public static MockSubscriber instance;

	public Object value;

	public MockSubscriber() {
		MockSubscriber.instance = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.subscribers.Subscriber#update(java.util.Collection)
	 */
	public void update(Collection<Data> datas) {
		value = datas.iterator().next().getValue();
	}

}
