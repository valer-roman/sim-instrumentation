/**
 * 
 */
package sim.monitor.internal.data;

/**
 * @author valer
 *
 */
public class IntData implements Data {

	private long timestamp;
	private long value;
	
	/* (non-Javadoc)
	 * @see sim.monitor.internal.data.Data#getTimestamp()
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.data.Data#setTimestamp(long)
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.data.Data#setValue(java.lang.Object)
	 */
	public void setValue(Object value) {
		if (value instanceof Long) {
			return;
		}
		this.value = (Long) value;
	}

	public long getValue() {
		return (Long) value;
	}
	
}
