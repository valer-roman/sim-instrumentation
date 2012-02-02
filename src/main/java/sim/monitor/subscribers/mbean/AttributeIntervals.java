/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.subscribers.mbean;

import java.util.HashMap;

import sim.monitor.Aggregation;

/**
 * @author val
 *
 */
public class AttributeIntervals extends HashMap<String, Intervals> {
	private static final long serialVersionUID = 1L;

	private Aggregation aggregation;

	public AttributeIntervals(Aggregation aggregation) {
		this.aggregation = aggregation;
	}

	/**
	 * @return the aggregation
	 */
	public Aggregation getAggregation() {
		return aggregation;
	}

}
