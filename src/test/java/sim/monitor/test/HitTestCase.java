/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.test;

import junit.framework.TestCase;
import sim.monitor.Builder;
import sim.monitor.Monitor;
import sim.monitor.processing.HitProcessor;
import sim.monitor.subscribers.MockSubscriber;
import sim.monitor.subscribers.SubscribeUpdater;

/**
 * @author val
 *
 */
public class HitTestCase extends TestCase {

	public HitTestCase() {
		super("Hit tests");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		SubscribeUpdater.instance().reloadSubscribers();
		super.setUp();
	}

	private void checkHitThreadsDone() {
		sleep(100);
		String failMessage = "Was waiting for all threads to finish in a reasonable amount of time. Failing task because of overtime!";
		int waitingTurns = 0;
		while (true) {
			if (!HitProcessor.instance().allThreadsWaiting()) {
				if (waitingTurns == 10) {
					fail(failMessage);
				}
				if (!sleep(100)) {
					break;
				}
				waitingTurns++;
			} else {
				sleep(100);
				// check again after a little sleep that threads are still
				// waiting (we make sure that all hits were processed)
				if (HitProcessor.instance().allThreadsWaiting()) {
					break;
				}
			}
		}
	}

	private boolean sleep(long amount) {
		try {
			Thread.sleep(amount);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	public void testSimpleValueMonitor() {
		Monitor valueMonitor = Builder.Monitor("Value Monitor", "Just publish the value received in hits")
				.tags()
					.add("testing")
				.build();

		for (int i = 0; i < 100; i++) {
			valueMonitor.hit(new Integer(i));
		}

		checkHitThreadsDone();

		assertNotNull(MockSubscriber.instance);
		assertTrue(MockSubscriber.instance.values.size() > 0);
		for (int i = 0; i < 100; i++) {
			assertEquals(Integer.class, MockSubscriber.instance.values.get(i)
					.getClass());
			assertEquals(i,
					((Integer) MockSubscriber.instance.values.get(i))
					.intValue());
		}

	}

	public void testDeltaTransformerMonitor() {
		Monitor valueMonitor = Builder.Monitor("Value Monitor", "Make a delta out of values and publish")
				.tags()
					.add("testing")
				.filters()
					.addDelta()
				.build();

		for (int i = 0; i < 100; i++) {
			valueMonitor.hit(new Integer(i));
		}

		checkHitThreadsDone();

		assertNotNull(MockSubscriber.instance);
		assertTrue(MockSubscriber.instance.values.size() > 0);
		for (int i = 0; i < 100; i++) {
			assertEquals(Long.class, MockSubscriber.instance.values.get(i)
					.getClass());
			if (i == 0) {
				assertEquals(0,
						((Long) MockSubscriber.instance.values.get(i))
						.intValue());
			} else {
				assertEquals(1,
						((Long) MockSubscriber.instance.values.get(i))
						.intValue());
			}
		}

	}

	public void testCounterHit() {
		Monitor monitor = Builder.Monitor("Counter", "Counts the hits on this monitor")
				.tags()
					.add("testing")
				.filters()
				.rates()
					.addCount()
				.build();

		for (int i = 0; i < 10; i++) {
			monitor.hit();
		}

		checkHitThreadsDone();
//		try {
//			Thread.sleep(100000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		assertNotNull(MockSubscriber.instance);
		assertTrue(MockSubscriber.instance.values.size() > 0);
		for (int i = 0; i < 10; i++) {
			assertEquals(Long.class, MockSubscriber.instance.values.get(i)
					.getClass());
			assertEquals((i + 1),
					((Long) MockSubscriber.instance.values.get(i)).longValue());
		}
	}

}
