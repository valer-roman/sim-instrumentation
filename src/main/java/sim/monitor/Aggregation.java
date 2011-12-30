/**
 * 
 */
package sim.monitor;

import sim.monitor.naming.Name;

/**
 * @author valer
 *
 */
public class Aggregation extends Statistic {

	private Aggregate aggregate;
	
	public Aggregation(Aggregate aggregate, Name name) {
		super(name);
		this.aggregate = aggregate;
	}

	/**
	 * @return the aggregate
	 */
	public Aggregate getAggregate() {
		return aggregate;
	}

	/**
	 * @param aggregate the aggregate to set
	 */
	public void setAggregate(Aggregate aggregate) {
		this.aggregate = aggregate;
	}

}
