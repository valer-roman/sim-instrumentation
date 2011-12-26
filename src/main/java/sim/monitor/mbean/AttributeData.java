/**
 * 
 */
package sim.monitor.mbean;

/**
 * @author valer
 *
 */
public class AttributeData {

	private String description;
	private String value;
	
	public AttributeData(String description, String value) {
		this.description = description;
		this.value = value;
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
		
}
