/**
 *
 */
package sim.monitor.timing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author valer
 *
 */
public class TimePeriod {

	private List<TimePeriodEntry> entries = new ArrayList<TimePeriod.TimePeriodEntry>();

	private class TimePeriodEntry implements Comparable<TimePeriodEntry> {
		private TimeUnit timeUnit;
		private int timeMultiplier;

		public TimePeriodEntry(TimeUnit timeUnit, int timeMultiplier) {
			this.timeUnit = timeUnit;
			this.timeMultiplier = timeMultiplier;
		}

		public long getSeconds() {
			return timeUnit.getSeconds() * timeMultiplier;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		public int compareTo(TimePeriodEntry o) {
			Comparator<TimeUnit> timeUnitComparator = timeUnit.new TimeUnitComparator();
			int timeUnitComparation = timeUnitComparator.compare(timeUnit, o.timeUnit);
			if (timeUnitComparation == 0) {
				return (int) Math.signum(timeMultiplier - o.timeMultiplier);
			} else {
				return timeUnitComparation;
			}
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return new HashCodeBuilder().append(timeUnit)
					.append(timeMultiplier).toHashCode();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof TimePeriodEntry)) {
				return false;
			}
			TimePeriodEntry other = (TimePeriodEntry) obj;
			return new EqualsBuilder().append(timeUnit, other.timeUnit)
					.append(timeMultiplier, other.timeMultiplier).isEquals();
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		for (TimePeriodEntry tpe : entries) {
			hcb.append(tpe.timeUnit).append(tpe.timeMultiplier);
		}
		return hcb.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof TimePeriod)) {
			return false;
		}
		TimePeriod other = (TimePeriod) obj;
		if (entries.size() != other.entries.size()) {
			return false;
		}
		for (TimePeriodEntry tpe : entries) {
			if (!other.entries.contains(tpe)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (TimePeriodEntry tpe : entries) {
			sb.append(tpe.timeMultiplier + tpe.timeUnit.toShortString());
		}
		return sb.toString();
	}

	public String toReadableString() {
		StringBuilder sb = new StringBuilder();
		List<TimePeriodEntry> sorted = new ArrayList<TimePeriodEntry>();
		sorted.addAll(entries);
		Collections.sort(sorted);
		for (TimePeriodEntry tpe : sorted) {
			if (sb.length() != 0) {
				sb.append(", ");
			}
			sb.append(tpe.timeMultiplier + " " + tpe.timeUnit.toLongString());
		}
		return sb.toString();
	}
}
