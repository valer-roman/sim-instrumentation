/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sim.monitor.Data;

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
	 * @see sim.monitor.subscribers.Subscriber#update(java.util.Collection)
	 */
	public void update(Collection<Data> datas) {
		Data data = datas.iterator().next();
		System.out.println(data.getValue().getClass());
		values.add(data.getValue());
	}

}
