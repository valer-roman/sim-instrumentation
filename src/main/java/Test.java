

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.Query;

import sim.monitor.Builder;
import sim.monitor.Monitor;
import sim.monitor.Timer;
import sim.monitor.subscribers.mbean.MonitoringMXBean;
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

		Thread.sleep(2000);

		Monitor counter = Builder
				.Monitor("Placed orders", "The number of placed orders")
				.tags()
					.add("order")
					.add("new")
				.filters()
				.rates()
					.addCount()
				.build();

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("customerid", new Long(1));
		context.put("orderid", new Long(1001));
		counter.hit(context);
		context.put("orderid", new Long(1002));
		Thread.sleep(20);
		counter.hit(context);
		context.put("orderid", new Long(1003));
		Thread.sleep(20);
		counter.hit(context);
		context.put("orderid", new Long(1004));
		Thread.sleep(20);
		counter.hit(context);
		Thread.sleep(10);
		counter.hit();

		Monitor mlv = Builder
				.Monitor("Visitors increase pace",
						"Keep track of the site's visitors increase pace")
				.tags()
					.add("visitors")
					.add("shop")
				.filters()
					.addDelta()
				.rates()
					.addAverage()
					.addAverage(TimeUnit.Second, 1)
				.publishRawValues()
				.build();

		//mlv.addRateStatistic(Type.sum, TimeUnit.Second, 1, "sum rate", "desc sum rate");
		// mlv.setRateStatistic(Type.sum, TimeUnit.Second, 1);
		//mlv.countRate(TimeUnit.Second, 1);
		//mlv.min();
		//mlv.max();
		mlv.hit(new Long(2));
		// Thread.sleep(1000);
		mlv.hit(new Long(8));
		Thread.sleep(1001);
		mlv.hit(new Long(30));
		// Thread.sleep(1000);
		mlv.hit(new Long(65));
/*
		Random rand = new Random(System.currentTimeMillis());
		for (int i = 1; i < 1000; i++) {
			Thread.sleep(1000);
			mlv.hit(new Long(rand.nextInt(1000)));
		}
*/
		Thread.sleep(1000);

		Timer timer = Builder.Monitor("Account creation duration")
				.tags()
					.add("account")
					.add("new")
				.buildTimer();
		timer.startTimer();
		Thread.sleep(20);
		timer.stopTimerAndHit();

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		MonitoringMXBean m = null;
		try {
			ObjectName on = new ObjectName(
					"sim.monitoring:tags=*,name=Placed orders,*");

			Set<ObjectInstance> so = mbs.queryMBeans(on, null);
			so.isEmpty();

			Set<ObjectName> objectNames = mbs.queryNames(
					on,
					Query.eq(Query.attr("Type"),
							Query.value("IntegerValueMonitor")));
			for (ObjectName o : objectNames) {
				m = JMX.newMXBeanProxy(mbs, o,
						MonitoringMXBean.class);

				m.getAggregation();
				m.getDescription();
			}
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


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
