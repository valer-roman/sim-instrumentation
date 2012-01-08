/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.processing;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import sim.monitor.MonitorCore;

/**
 * Task running the hit processing of the monitors
 * 
 * Through the method {@link #signalHit(MonitorCore)} the task is announced that
 * more hits were received.
 * 
 * @author val
 * 
 */
public class HitProcessorTask extends Thread {

	private BlockingQueue<MonitorCore> monitors = new LinkedBlockingQueue<MonitorCore>();

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
					for (MonitorCore monitor : monitors) {
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
		for (MonitorCore monitor : monitors) {
			if (monitor.hasMoreHits()) {
				return true;
			}
		}
		return false;
	}

	void acceptMonitor(MonitorCore monitor) {
		monitors.add(monitor);
	}

	void signalHit(MonitorCore monitor) {
		synchronized (lock) {
			if (waiting.get()) {
				lock.notify();
			} else {
				newData.set(true);
			}
		}
	}

}
