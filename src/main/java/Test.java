

import sim.monitor.Monitor;
import sim.monitor.MonitorBuilder;
import sim.monitor.publishers.Aggregate;
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
		String container = "sim.monitoring:type=Testing";

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

		Monitor counter = MonitorBuilder
		.inContainer(container)
		.addStatistic(Aggregate.Count)
		.publishRawValues(false)
		.build("Counter test", "counter desc");

		counter.hit();
		Thread.sleep(20);
		counter.hit();

		Monitor mlv = MonitorBuilder.inContainer(container)
				.addRate(Aggregate.Sum, TimeUnit.Second, 1)
				.applyDelta()
				.build("Value Long Test", "long value test descr");
		//mlv.addRateStatistic(Type.sum, TimeUnit.Second, 1, "sum rate", "desc sum rate");
		// mlv.setRateStatistic(Type.sum, TimeUnit.Second, 1);
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
