/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.transformers;

import java.util.Collection;

import sim.monitor.Hit;

/**
 * @author val
 *
 */
public interface Filter {

	public Collection<Hit> transform(Collection<Hit> hits);

}
