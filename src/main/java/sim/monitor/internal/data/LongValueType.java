/**
 * 
 */
package sim.monitor.internal.data;

/**
 * @author valer
 *
 */
public class LongValueType implements DataValueType {

	private long value;
	
	public LongValueType(long value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see sim.monitor.internal.data.DataValueType#isLongType()
	 */
	public boolean isLongType() {
		return true;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.data.DataValueType#isDoubleType()
	 */
	public boolean isDoubleType() {
		return false;
	}

	public long getValue() {
		return this.value;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.data.DataValueType#getValueObject()
	 */
	public Object getValueObject() {
		return value;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.data.DataValueType#getValueTypeClassName()
	 */
	public String getValueTypeClassName() {
		return Long.class.getName();
	}
	
}
