/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.transformers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import sim.monitor.Hit;
import sim.monitor.timing.TimePeriod;

/**
 * @author val
 *
 */
public class TimeIntervalSampler implements Filter {

	private TimePeriod timePeriod;

	private long lastDataTime = 0;

	public TimeIntervalSampler(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.Transformer#transform(sim.monitor.Hit)
	 */
	public Collection<Hit> transform(Collection<Hit> hits) {
		List<Hit> result = new ArrayList<Hit>();

		for (Hit hit : hits) {
			long timestamp = hit.getTimestamp();
			if (timePeriod.lessThan(timestamp - lastDataTime)
					|| lastDataTime == 0) {
				// value = data.getValue(); value stays with the same data
				result.add(hit);
				lastDataTime = timestamp;
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(timePeriod).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof TimeIntervalSampler)) {
			return false;
		}
		TimeIntervalSampler other = (TimeIntervalSampler) obj;
		return new EqualsBuilder().append(timePeriod, other.timePeriod)
				.isEquals();
	}

}
