/**
 * 
 */
package sim.monitor.subscribers.mbean;


/**
 * @author valer
 *
 */
public class AttributeData {

	private String description;
	private String value;
	private String type;
	
	public AttributeData(String description, String value, String type) {
		this.description = description;
		this.value = value;
		this.type = type;
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

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
