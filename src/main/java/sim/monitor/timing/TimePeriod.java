/**
 * 
 */
package sim.monitor.timing;

import java.util.ArrayList;
import java.util.List;

/**
 * @author valer
 *
 */
public class TimePeriod {

	private List<TimePeriodEntry> entries = new ArrayList<TimePeriod.TimePeriodEntry>();
	
	private class TimePeriodEntry {
		private TimeUnit timeUnit;
		private int timeMultiplier;
		
		public TimePeriodEntry(TimeUnit timeUnit, int timeMultiplier) {
			this.timeUnit = timeUnit;
			this.timeMultiplier = timeMultiplier;
		}
		
		public int getSeconds() {
			return timeUnit.getSeconds() * timeMultiplier;
		}
	}
	
	public TimePeriod(TimeUnit timeUnit, int timeMultiplier) {
		entries.add(new TimePeriodEntry(timeUnit, timeMultiplier));
	}
	
	public TimePeriod add(TimeUnit timeUnit, int timeMultiplier) {
		entries.add(new TimePeriodEntry(timeUnit, timeMultiplier));
		return this;
	}
	
	public int getSeconds() {
		int seconds = 0;
		for (TimePeriodEntry tpe : entries) {
			seconds += tpe.getSeconds();
		}
		return seconds;
	}
	
}
