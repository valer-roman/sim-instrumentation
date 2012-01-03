package sim.monitor.test;
import sim.monitor.Aggregate;
import sim.monitor.Monitor;
import sim.monitor.Rate.Type;
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
		Monitor.Creator creator = Monitor.Creator.useContext("sim.monitoring:type=Testing");

		Monitor counter = creator.create("Counter test")
			.setDescription("counter desc").setStatistic(Aggregate.Count);

		counter.hit();
		Thread.sleep(20);
		counter.hit();
		
		Monitor mlv = creator.create("Value Long Test")
			.setDescription("long value test descr");
		mlv.addRateStatistic(Type.sum, TimeUnit.Second, 1, "sum rate", "desc sum rate");
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
