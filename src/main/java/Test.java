import sim.monitor.MonitorCounter;
import sim.monitor.MonitorDoubleDelta;
import sim.monitor.MonitorDoubleValue;
import sim.monitor.MonitorLongValue;
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
		Domain domain = new Domain("sim.monitoring", "type", "Testing");
		MonitorCounter counter = new MonitorCounter(domain, "Counter test", "counter desc");
		counter.addRate(new TimePeriod(TimeUnit.Second, 5), "Rate test", "rate description");
		counter.hit();
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {}
		counter.hit();
		
		MonitorDoubleValue mdv = new MonitorDoubleValue(domain, "Double Value Testing", "description");
		mdv.hit(2.45);

		MonitorLongValue mlv = new MonitorLongValue(domain, "Long Value Testing", "description long value testing");
		mlv.hit(13);

		MonitorDoubleDelta mdd = new MonitorDoubleDelta(domain, "Double Delta Testing", "descr. double delta testing");
		mdd.hit(3.5);
		mdd.hit(4.7);
	}

}
