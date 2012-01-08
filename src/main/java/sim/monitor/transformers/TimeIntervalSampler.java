/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.transformers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sim.monitor.Data;
import sim.monitor.timing.TimePeriod;

/**
 * @author val
 *
 */
public class TimeIntervalSampler implements Transformer {

	private TimePeriod timePeriod;

	private long lastDataTime = 0;

	public TimeIntervalSampler(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.Transformer#transform(sim.monitor.Data)
	 */
	public Collection<Data> transform(Collection<Data> datas) {
		List<Data> result = new ArrayList<Data>();

		for (Data data : datas) {
			long timestamp = data.getTimestamp();
			if (timePeriod.lessThan(timestamp - lastDataTime)
					|| lastDataTime == 0) {
				// value = data.getValue(); value stays with the same data
				result.add(data);
				lastDataTime = timestamp;
			}
		}

		return result;
	}
}
