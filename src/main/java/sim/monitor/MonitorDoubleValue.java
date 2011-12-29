/**
 * 
 */
package sim.monitor;


/**
 * Monitor used to track fractional values from the application.
 * For example it could be used to register the values of orders amounts in an ordering system.
 *  
 * @author val
 *
 */
public class MonitorDoubleValue extends Monitor<MonitorDoubleValue, Double> {

	public MonitorDoubleValue(Domain domain, String name) {
		super(domain, name);
	}
	
}
