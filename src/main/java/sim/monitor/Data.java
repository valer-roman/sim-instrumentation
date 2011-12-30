/**
 * 
 */
package sim.monitor;

import sim.monitor.naming.Name;

/**
 * @author val
 *
 */
public class Data<T> {

	private Name name;
	private long timestamp;
	private T value;
	
	//public Data() {}
	
	public Data(Name name, long timestamp, T value) {
		this.name = name;
		this.timestamp = timestamp;
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public Name getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(Name name) {
		this.name = name;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

}
