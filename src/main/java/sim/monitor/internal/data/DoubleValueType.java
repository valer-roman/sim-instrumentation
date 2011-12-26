/**
 * 
 */
package sim.monitor.internal.data;

/**
 * @author valer
 *
 */
public class DoubleValueType implements DataValueType {

	private double value;
	
	public DoubleValueType(double value) {
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see sim.monitor.internal.data.DataValueType#isLongType()
	 */
	public boolean isLongType() {
		return false;
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.data.DataValueType#isDoubleType()
	 */
	public boolean isDoubleType() {
		return true;
	}

	public double getValue() {
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
		return Double.class.getName();
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.data.DataValueType#initNew()
	 */
	public DataValueType initNew() {
		return new DoubleValueType(0.0);
	}

	/* (non-Javadoc)
	 * @see sim.monitor.internal.data.DataValueType#difference(sim.monitor.internal.data.DataValueType)
	 */
	public DataValueType difference(DataValueType dataValueType) {
		if (!(dataValueType instanceof DoubleValueType)) {
			return this;
		}
		return new DoubleValueType(value - ((DoubleValueType) dataValueType).getValue());
	}
	
}
