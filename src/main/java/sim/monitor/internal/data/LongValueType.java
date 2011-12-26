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

	/* (non-Javadoc)
	 * @see sim.monitor.internal.data.DataValueType#initNew()
	 */
	public DataValueType initNew() {
		return new LongValueType(0);
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.data.DataValueType#difference(sim.monitor.internal.data.DataValueType)
	 */
	public DataValueType difference(DataValueType dataValueType) {
		if (!(dataValueType instanceof LongValueType)) {
			return this;
		}
		return new LongValueType(value - ((LongValueType) dataValueType).getValue());
	}
	
}
