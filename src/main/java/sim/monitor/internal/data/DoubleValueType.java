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
	
}
