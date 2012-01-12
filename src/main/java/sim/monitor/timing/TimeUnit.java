/**
 * 
 */
package sim.monitor.timing;

/**
 * @author val
 *
 */
public enum TimeUnit {

	Second,
	Minute,
	Hour,
	Day;

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
}
