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
public interface Subscriber {

	public void update(Collection<Data> datas);

}
