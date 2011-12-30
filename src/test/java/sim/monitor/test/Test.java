package sim.monitor.test;
import sim.monitor.Builder;
import sim.monitor.Domain;
import sim.monitor.MonitorCounter;
import sim.monitor.MonitorLongValue;
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
	public static void main(String[] args) throws InterruptedException {
		Domain domain = Builder.domain("sim.monitoring")
			.add("type", "Testing");

		MonitorCounter counter = domain.counter("Counter test")
			.setDescription("counter desc");

		counter.hit();
		Thread.sleep(20);
		counter.hit();
		
		MonitorLongValue mlv = domain.mLongValue("Value Long Test")
			.setDescription("long value test descr");
		mlv.sumRate(TimeUnit.Second, 1, "sum rate", "desc sum rate");
		//mlv.sumRate(TimeUnit.Second, 1);
		//mlv.countRate(TimeUnit.Second, 1);
		//mlv.min();
		//mlv.max();
		mlv.hit(new Long(2));
		Thread.sleep(1000);
		mlv.hit(new Long(20));
		Thread.sleep(1000);
		mlv.hit(new Long(30));
		Thread.sleep(1000);
		mlv.hit(new Long(1));
		
		Thread.sleep(1000);
		
		/*
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
		*/
	}

}
