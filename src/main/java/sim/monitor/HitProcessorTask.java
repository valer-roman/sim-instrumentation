/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Task running the hit processing of the monitors
 * 
 * Through the method {@link #signalHit(Monitor)} the task is announced that
 * more hits were received.
 * 
 * @author val
 * 
 */
public class HitProcessorTask extends Thread {

	private BlockingQueue<Monitor> monitors = new LinkedBlockingQueue<Monitor>();

	private final Object lock = new Object();

	private AtomicBoolean newData = new AtomicBoolean(true);
	private AtomicBoolean waiting = new AtomicBoolean(false);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (true) {
			try {
				synchronized (lock) {
					if (!newData.get()) {
						waiting.set(true);
						lock.wait();
						waiting.set(false);
					} else {
						newData.set(false);
					}
				}
				while (monitorsHaveHits()) {
					for (Monitor monitor : monitors) {
						while (monitor.hasMoreHits()) {
							monitor.processNext();
						}
					}
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	private boolean monitorsHaveHits() {
		for (Monitor monitor : monitors) {
			if (monitor.hasMoreHits()) {
				return true;
			}
		}
		return false;
	}

	void acceptMonitor(Monitor monitor) {
		monitors.add(monitor);
	}

	void signalHit(Monitor monitor) {
		synchronized (lock) {
			if (waiting.get()) {
				lock.notify();
			} else {
				newData.set(true);
			}
		}
	}

	boolean isWaiting() {
		return waiting.get();
	}
}
