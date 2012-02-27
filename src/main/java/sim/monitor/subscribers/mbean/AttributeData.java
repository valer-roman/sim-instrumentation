/**
 *
 */
package sim.monitor.subscribers.mbean;

import javax.management.openmbean.SimpleType;

/**
 * @author valer
 *
 */
@SuppressWarnings("rawtypes")
public class AttributeData {

	private String description;
	private Object value;
	private Class type;

	public AttributeData(String description, Object value, Class type) {
		this.description = description;
		this.value = value;
		this.type = type;
	}

	public SimpleType<?> getSimpleType() {
		if (type.equals(Long.class)) {
			return SimpleType.LONG;
		} else if (type.equals(Integer.class)) {
			return SimpleType.INTEGER;
		} else if (type.equals(String.class)) {
			return SimpleType.STRING;
		} else if (type.equals(Double.class)) {
			return SimpleType.DOUBLE;
		} else if (type.equals(Float.class)) {
			return SimpleType.FLOAT;
		}
		return null;
	}

	public Class getOriginalType() {
		if (type.equals(Long.class)) {
			return long.class;
		} else if (type.equals(Integer.class)) {
			return int.class;
		} else if (type.equals(String.class)) {
			return String.class;
		} else if (type.equals(Double.class)) {
			return Double.class;
		} else if (type.equals(Float.class)) {
			return Float.class;
		}
		return null;
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
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public Class getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Class type) {
		this.type = type;
	}

}
