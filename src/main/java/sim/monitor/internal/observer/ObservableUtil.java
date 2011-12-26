/**
 * 
 */
package sim.monitor.internal.observer;

import java.util.ArrayList;
import java.util.List;

import sim.monitor.internal.data.Data;
import sim.monitor.naming.Name;

/**
 * @author valer
 *
 */
public class ObservableUtil {

	private List<MonitorProcessorObserver> observers = new ArrayList<MonitorProcessorObserver>();
	
	public void addObserver(MonitorProcessorObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(MonitorProcessorObserver observer) {
		observers.remove(observer);
	}
	
	public void notify(Name name, Data data) {
		for (MonitorProcessorObserver observer : observers) {
			observer.update(name, data);
		}
	}

}
