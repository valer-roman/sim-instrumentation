/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.transformers;

import java.util.ArrayList;
import java.util.Collection;

import sim.monitor.Hit;
import sim.monitor.MeasureUtil;

/**
 * @author val
 *
 */
public class DeltaTransformer implements Filter {

	private Hit lastData = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.Transformer#transform(sim.monitor.Hit)
	 */
	public Collection<Hit> transform(Collection<Hit> hits) {
		Collection<Hit> result = new ArrayList<Hit>();
		for (Hit hit : hits) {
			Object value = null;
			if (lastData == null) {
				value = new Long(0);
			} else {
				value = lastData.getValue();
			}
			Hit newHit = new Hit(hit.getTimestamp(), MeasureUtil.difference(
					hit.getValue(), value));
			result.add(newHit);
			lastData = hit;
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
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof DeltaTransformer)) {
			return false;
		}
		return true;
	}

}
