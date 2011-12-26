/**
 * 
 */
package sim.monitor.internal.data;

/**
 * @author val
 *
 */
public interface Data {

	public long getTimestamp();
	
	public void setTimestamp(long timestamp);
	
	public void setValue(Object value);
	
}
