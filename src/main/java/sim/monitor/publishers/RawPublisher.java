/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.publishers;

import java.util.Collection;

import sim.monitor.Data;
import sim.monitor.naming.Name;

/**
 * Publishes the hit data without any other processing.
 * 
 * @author val
 * 
 */
public class RawPublisher extends Publisher {

	public RawPublisher(Name name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see sim.monitor.Statistic#processHit(java.util.Collection)
	 */
	@Override
	protected Collection<Data> processHit(Collection<Data> datas) {
		return datas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.Statistic#isPublishedWithSuffix()
	 */
	@Override
	public boolean isPublishedWithSuffix() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.Statistic#getSuffix()
	 */
	@Override
	public String getSuffix() {
		return null;
	}

}
