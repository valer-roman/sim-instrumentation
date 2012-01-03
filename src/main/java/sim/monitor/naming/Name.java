/**
 * 
 */
package sim.monitor.naming;


/**
 * This is the full name of a monitor, the domain plus the monitor name and description
 * 
 * @author val
 */
public class Name {

	private String context;
	private String name;
	private String description;
	
	/**
	 * Constructs a new monitor name
	 * The description here is the empty string
	 * 
	 * @param domain the domain
	 * @param name the name of the monitor
	 */
	public Name(String context, String name) {
		this(context, name, "");
	}
	
	/**
	 * Constructs a monitor name
	 * 
	 * @param domain the domain
	 * @param name the name of the monitor
	 * @param description the description of the monitor
	 */
	public Name(String context, String name, String description) {
		this.context = context;
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setContext(String context) {
		this.context = context;
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
