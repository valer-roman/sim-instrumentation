/**
 * 
 */
package sim.monitor.publishers;

import java.util.ArrayList;
import java.util.Collection;

import sim.monitor.Data;
import sim.monitor.MeasureUtil;
import sim.monitor.naming.Name;

/**
 * @author valer
 *
 */
public class Aggregation extends Publisher {

	private Aggregate aggregate;
	private Object result = new Long(0);

	private Long count;
	private Object sum;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see sim.monitor.Statistic#getSuffix()
	 */
	@Override
	public String getSuffix() {
		switch (aggregate) {
		case Sum:
			return "SUM";
		case Count:
			return "COUNT";
		case Average:
			return "AVG";
		case Minimum:
			return "MIN";
		case Maximum:
			return "MAX";
		}
		return null;
	}

	@Override
	protected Collection<Data> processHit(Collection<Data> datas) {
		Collection<Data> results = new ArrayList<Data>();
		for (Data data : datas) {
			Object value = data.getValue();
			switch (aggregate) {
			case Sum:
				result = MeasureUtil.sum(result, value);
				break;
			case Count:
				result = MeasureUtil.sum(result, 1);
				break;
			case Average:
				count = count + 1;
				sum = MeasureUtil.sum(sum, value);
				result = MeasureUtil.divide(sum, count);
				break;
			case Minimum:
				result = MeasureUtil.min(result, value);
				break;
			case Maximum:
				result = MeasureUtil.max(result, value);
				break;
			}
			results.add(new Data(getName(), data.getTimestamp(), result));
		}
		return results;
	}

}
