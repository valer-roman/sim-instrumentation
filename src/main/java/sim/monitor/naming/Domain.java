/**
 * 
 */
package sim.monitor.naming;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * This is the class used to categorize monitors.
 * Monitors name is composed from a domain, a number of keys with values used to categorize monitors, and a name a description.
 * 
 * This categorization is inspired from the JMX object name.
 * 
 * An example of domain, using the JMX notation,  is "sim.monitoring.test:system=Linux,application=SIM,module=Core"
 * 
 * @author val
 */
public class Domain {

	private String domain;
	
	private List<DomainKey> categories = new ArrayList<DomainKey>();
	
	/**
	 * Constructs a new domain
	 * 
	 * @param domain the domain name
	 */
	public Domain(String domain) {
		this.domain = domain;
	}
	
	/**
	 * Constructs a new domain with a category
	 * 
	 * @param domain the domain name
	 * @param key the category type
	 * @param value the category name
	 */
	public Domain(String domain, String key, String value) {
		this.domain = domain;
		categories.add(new DomainKey(key, value));
	}

	/*
	 * Constructs the domain starting from an existing domain
	 */
	private Domain(Domain otherMonitorNaming) {
		this.domain = otherMonitorNaming.domain;
		this.categories.addAll(otherMonitorNaming.categories);
	}
	
	/**
	 * Adds a new category to domain
	 * The return is a new domain.
	 * 
	 * @param key the category type
	 * @param value the category name
	 * @return a new domain composed from this domain plus the category
	 */
	public Domain add(String key, String value) {
		Domain domain = new Domain(this);
		domain.categories.add(new DomainKey(key, value));
		return domain;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Returns the categories of the domain in a {@link Hashtable}
	 * 
	 * @return the {@link Hashtable}
	 */
	public Hashtable<String, String> getCategories() {
		Hashtable<String, String> result = new Hashtable<String, String>();
		for (DomainKey nsComp : categories) {
			result.put(nsComp.getKey(), nsComp.getValue());
		}
		return result;
	}
}
