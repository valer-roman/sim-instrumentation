/**
 * 
 */
package sim.monitor.naming;

/**
 * @author valer
 *
 */
public class DomainKey {

	private String key;
	private String value;
	
	public DomainKey(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
