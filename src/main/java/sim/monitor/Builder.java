/**
 * 
 */
package sim.monitor;


/**
 * @author valer
 *
 */
public class Builder {

	/**
	 * Create a domain
	 * 
	 * @param domain the domain
	 * @return the domain
	 */
	public static Domain domain(String domain) {
		return new Domain(domain);
	}
	
	/**
	 * Create a domain from an existing domain. A clone.
	 * 
	 * @param fromOther the other domain
	 * @return the domain
	 */
	public static Domain domain(Domain fromOther) {
		return new Domain(fromOther);
	}
	
	public static class MonitorA<SELF_TYPE extends MonitorA<SELF_TYPE, ELEMENT_TYPE>, ELEMENT_TYPE extends Number> {
		
		@SuppressWarnings("unchecked")   
		protected final SELF_TYPE self() {
			return (SELF_TYPE) this;
		}
			
		public SELF_TYPE hit(ELEMENT_TYPE data) {
			return self();
		}
		
		public MonitorA(Domain domain) {
			
		}
	}
	
	public static class CounterMonitorA extends MonitorA<CounterMonitorA, Long> {
		
		public CounterMonitorA(Domain domain) {
			super(domain);
		}
		
	}
	
	public static CounterMonitorA counter(Domain domain) {
		CounterMonitorA x = new CounterMonitorA(domain);
		return x;
	}
	
}
