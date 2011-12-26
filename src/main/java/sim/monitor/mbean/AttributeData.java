/**
 * 
 */
package sim.monitor.mbean;

import sim.monitor.internal.data.DataValueType;

/**
 * @author valer
 *
 */
public class AttributeData {

	private String description;
	private DataValueType value;
	
	public AttributeData(String description, DataValueType value) {
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
	public DataValueType getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(DataValueType value) {
		this.value = value;
	}

}
