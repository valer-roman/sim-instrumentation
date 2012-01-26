/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

/**
 * @author val
 *
 */
public class Timer {

	private Monitor monitor;

	private long startTime;

	Timer(Monitor monitor) {
		this.monitor = monitor;
	}

	public void startTimer() {
		startTime = System.currentTimeMillis();
	}

	public void stopTimerAndHit() {
		monitor.hit(System.currentTimeMillis() - startTime);
	}

}
