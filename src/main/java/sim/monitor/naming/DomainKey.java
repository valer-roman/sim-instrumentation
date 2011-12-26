/**
 * 
 */
package sim.monitor.naming;

/**
 * Class used to define categories on a domain
 * A category has a type and a name, they are mapped here using the key and value properties.
 * 
 * @author val
 */
public class DomainKey {

	private String key;
	private String value;
	
	public DomainKey(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the category type
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the category name
	 */
	public String getValue() {
		return value;
	}

}
