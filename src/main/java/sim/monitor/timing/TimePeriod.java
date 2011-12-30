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
		
		public long getSeconds() {
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
	
	public long getSeconds() {
		long seconds = 0;
		for (TimePeriodEntry tpe : entries) {
			seconds += tpe.getSeconds();
		}
		return seconds;
	}

	public boolean lessThan(long millis) {
		long seconds = getSeconds();
		if (seconds < millis/1000) {
			return true;
		}
		return false;
	}
}
