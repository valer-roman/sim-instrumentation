/**
 * 
 */
package sim.monitor.internal.data;

/**
 * @author val
 *
 */
public class Data {

	private long timestamp;
	private DataValueType value;
	
	public Data(long timestamp, DataValueType value) {
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
	public DataValueType getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(DataValueType value) {
		this.value = value;
	}

	public Data clone() {
		return new Data(timestamp, value);
	}
}
