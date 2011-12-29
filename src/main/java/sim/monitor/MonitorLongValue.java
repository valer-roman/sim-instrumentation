/**
 * 
 */
package sim.monitor;


/**
 * @author val
 *
 */
public class MonitorLongValue extends Monitor<MonitorLongValue, Long> {

	public MonitorLongValue(Domain domain, String name) {
		super(domain, name);
		//MonitorProcessor valueProcessor = new ValueProcessor(new Name(domain, name, description));
		//valueProcessor.addObserver(MBeanManager.instance());
		//this.processors.add(valueProcessor);
	}

	public void hit(Long value) {
		super.hit_(value);
	}
	
}
