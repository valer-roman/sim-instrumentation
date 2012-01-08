/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.transformers;

import java.util.ArrayList;
import java.util.Collection;

import sim.monitor.Data;
import sim.monitor.MeasureUtil;

/**
 * @author val
 *
 */
public class DeltaTransformer implements Transformer {

	private Data lastData;

	/* (non-Javadoc)
	 * @see sim.monitor.Transformer#transform(sim.monitor.Data)
	 */
	public Collection<Data> transform(Collection<Data> datas) {
		Collection<Data> result = new ArrayList<Data>();
		for (Data data : datas) {
			if (lastData == null) {
				lastData = data;
				result.add(data);
			} else {
				Data newData = new Data(data.getName(), data.getTimestamp(),
						MeasureUtil.difference(data.getValue(),
								lastData.getValue()));
				result.add(newData);
				lastData = data;
			}
		}
		return result;
	}

}
