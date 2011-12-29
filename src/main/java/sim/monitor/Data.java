/**
 * 
 */
package sim.monitor;

/**
 * @author val
 *
 */
public class Data<T> {

	private long timestamp;
	private T value;
	
	public Data() {}
	
	public Data(long timestamp, T value) {
		this.timestamp = timestamp;
		this.value = value;
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
