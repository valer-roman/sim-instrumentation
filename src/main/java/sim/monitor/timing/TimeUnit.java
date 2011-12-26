/**
 * 
 */
package sim.monitor.timing;

/**
 * @author val
 *
 */
public enum TimeUnit {

	None,
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
}
