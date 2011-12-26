import sim.monitor.MonitorCounter;
import sim.monitor.naming.Domain;
import sim.monitor.timing.TimePeriod;
import sim.monitor.timing.TimeUnit;

/**
 * 
 */

/**
 * @author valer
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MonitorCounter counter = new MonitorCounter(new Domain("sim.monitoring", "type", "Testing"), "Counter test", "counter desc");
		counter.addRate(new TimePeriod(TimeUnit.Second, 5), "Rate test", "rate description");
		counter.hit();
	}

}
