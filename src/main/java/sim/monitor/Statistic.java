/**
 * 
 */
package sim.monitor;

import sim.monitor.naming.Name;

/**
 * @author valer
 *
 */
public class Statistic {

	private Name name;
	
	public Statistic(Name name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public Name getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(Name name) {
		this.name = name;
	}
	
	
}
