/**
 *
 */
package sim.monitor.timing;

import java.util.Comparator;

/**
 * @author val
 *
 */
public enum TimeUnit {

	Second,
	Minute,
	Hour,
	Day;

	public class TimeUnitComparator implements Comparator<TimeUnit> {

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(TimeUnit o1, TimeUnit o2) {
			if (o1 == null && o2 == null) {
				return 0;
			} else if (o1 == null) {
				return -1;
			} else if (o2 == null) {
				return 1;
			}
			if (o1 == o2) {
				return 0;
			}
			if (o1 == Day) {
				return 1;
			} else if (o2 == Day) {
				return -1;
			}

			if (o1 == Hour) {
				return 1;
			} else if (o2 == Hour) {
				return -1;
			}

			if (o1 == Minute) {
				return 1;
			} else if (o2 == Minute) {
				return -1;
			}

			if (o1 == Second) {
				return 1;
			} else if (o2 == Second) {
				return -1;
			}

			return 0;
		}

	}

	public int getSeconds() {
		switch (this) {
		case Second:
			return 1;
		case Minute:
			return 60;
		case Hour:
			return 60*60;
		case Day:
			return 3600*24;
		default:
			return 0;
		}
	}

	public String toShortString() {
		switch (this) {
		case Second:
			return "S";
		case Minute:
			return "M";
		case Hour:
			return "H";
		case Day:
			return "D";
		default:
			return "";
		}
	}

	public String toLongString() {
		switch (this) {
		case Second:
			return "Second(s)";
		case Minute:
			return "Month(s)";
		case Hour:
			return "Hour(s)";
		case Day:
			return "Day(s)";
		default:
			return "";
		}
	}

}
