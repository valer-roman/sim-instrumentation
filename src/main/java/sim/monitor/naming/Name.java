/**
 * 
 */
package sim.monitor.naming;

import sim.monitor.Domain;

/**
 * This is the full name of a monitor, the domain plus the monitor name and description
 * 
 * @author val
 */
public class Name {

	private Domain domain;
	private String name;
	private String description;
	
	/**
	 * Constructs a new monitor name
	 * The description here is the empty string
	 * 
	 * @param domain the domain
	 * @param name the name of the monitor
	 */
	public Name(Domain domain, String name) {
		this(domain, name, "");
	}
	
	/**
	 * Constructs a monitor name
	 * 
	 * @param domain the domain
	 * @param name the name of the monitor
	 * @param description the description of the monitor
	 */
	public Name(Domain domain, String name, String description) {
		this.domain = domain;
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the domain
	 */
	public Domain getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
