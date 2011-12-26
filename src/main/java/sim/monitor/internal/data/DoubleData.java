/**
 * 
 */
package sim.monitor.internal.data;

/**
 * @author valer
 *
 */
public class DoubleData implements Data {

	private long timestamp;
	private double value;
	
	public DoubleData() {}
	
	public DoubleData(long timestamp, double value) {
		this.timestamp = timestamp;
		this.value = value;
	}
	
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
		if (!(value instanceof Double)) {
			return;
		}
		this.value = (Double) value;
	}

	public double getValue() {
		return (Double) value;
	}
	
}
