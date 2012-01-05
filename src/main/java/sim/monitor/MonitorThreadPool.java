/**
 * 
 */
package sim.monitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author valer
 *
 */
public class MonitorThreadPool {

	private static MonitorThreadPool thiz = new MonitorThreadPool();
	
	private ExecutorService executorService;
	
	private MonitorThreadPool() {
		executorService = Executors.newCachedThreadPool();
	}
	
	public static MonitorThreadPool instance() {
		return thiz;
	}
	
	public void execute(Runnable task) {
		executorService.submit(task);
	}
}
