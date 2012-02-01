

import java.util.HashMap;
import java.util.Map;

import sim.monitor.Builder;
import sim.monitor.Monitor;
import sim.monitor.Timer;
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

		/*
		 * Monitor monitor =
		 * MonitorBuilder.useCategory("sim.monitoring:type=Testing")
		 * .setPickoutInterval(TimeUnit, int) .addDefaultStatistic(Sum)
		 * //.addDefaultRateStatistic(Sum, TimeUnit, int) . .build(name,
		 * description);
		 */

		/*
		Builder.Monitor("Counter")
			.description("This is a counter")
			.tags()
				.add("Backend")
				.add("Ordering")
			.transformers()
				.addDelta()
				.add(Class<? extends Transformer>)
			.statistics()
				.addCount()
				.addAverage("Average")
					.description("The average")
				.add(Class)
			.build();

		T instance = Builder.tag("sadsad").tag("sasda");

		c.hit();
		 */

		Monitor counter = Builder.Monitor("Counter test", "counter desc")
				.tags()
					.add("testing")
					.add("counter")
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
		Thread.sleep(10);
		counter.hit();

		Monitor mlv = Builder
				.Monitor("Value Long Test", "long value test descr")
				.tags()
					.add("testing")
					.add("counter")
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
		mlv.hit(new Long(5));

		Thread.sleep(1000);


		Timer timer = Builder.Monitor("sadsa")
				.tags()
					.add("counter")
				.add("testing")
				.buildTimer();
		timer.startTimer();
		Thread.sleep(20);
		timer.stopTimerAndHit();

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
