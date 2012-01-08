/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.transformers;

import java.util.Collection;

import sim.monitor.Data;

/**
 * @author val
 *
 */
public interface Transformer {

	public Collection<Data> transform(Collection<Data> datas);

}
