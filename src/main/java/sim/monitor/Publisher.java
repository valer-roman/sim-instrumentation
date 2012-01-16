/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;



/**
 * @author val
 *
 */
public abstract class Publisher {

	protected String name;
	protected String description;

	Publisher(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Publisher#getName()
	 */
	String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Publisher#setName(java.lang.String)
	 */
	void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Publisher#getDescription()
	 */
	String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.publishers.Publisher#setDescription(java.lang.String)
	 */
	void setDescription(String description) {
		this.description = description;
	}

	abstract void publish();

	abstract String getSuffix();

}
