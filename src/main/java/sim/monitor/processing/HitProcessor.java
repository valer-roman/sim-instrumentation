/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor.processing;

import sim.monitor.Monitor;

/**
 * 
 * Dispatches hits to threads running the hit processing on the monitor.
 * 
 * @author val
 * 
 */
public class HitProcessor {

	private static HitProcessor instance = new HitProcessor();

	private int availableProcessors;
	private HitProcessorTask[] hitTasks;

	public static HitProcessor instance() {
		return instance;
	}

	private HitProcessor() {
		this.availableProcessors = Runtime.getRuntime().availableProcessors();
		this.hitTasks = new HitProcessorTask[this.availableProcessors];
		for (int i = 0; i < availableProcessors; i++) {
			this.hitTasks[i] = new HitProcessorTask();
			this.hitTasks[i].start();
		}
	}

	public void acceptMonitor(Monitor monitor) {
		HitProcessorTask hitTask = this.hitTasks[monitor.hashCode()
		                                         % this.availableProcessors];
		hitTask.acceptMonitor(monitor);
	}

	public void signalHit(Monitor monitor) {
		HitProcessorTask hitTask = this.hitTasks[monitor.hashCode()
		                                         % this.availableProcessors];
		hitTask.signalHit(monitor);
	}

	/**
	 * This is for tests, to check that threads are in waiting state
	 * 
	 * @return true if all threads are waiting
	 */
	public boolean allThreadsWaiting() {
		for (HitProcessorTask hpt : hitTasks) {
			if (!hpt.isWaiting()) {
				return false;
			}
		}
		return true;
	}

	void shutdown() {
		for (int i = 0; i < hitTasks.length; i++) {
			hitTasks[i].interrupt();
		}
	}
}
