/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sim.monitor.processing.HitProcessor;
import sim.monitor.timing.TimePeriod;
import sim.monitor.timing.TimeUnit;
import sim.monitor.transformers.DeltaTransformer;
import sim.monitor.transformers.Filter;
import sim.monitor.transformers.TimeIntervalSampler;

/**
 * @author val
 *
 */
public class Builder {

	// STRUCTURE

	public interface Monitor extends Build {

		public Tags tags();

	}

	public interface MonitorDescribe extends Monitor {

		public Monitor description(String description);

	}

	public interface Tags extends Build {

		public Tags add(String tag);

		public Filters filters();

	}

	public interface Filters extends Build {

		public Filters addSampler(TimeUnit timeUnit, int multiplier);

		public Filters addDelta();

		public Filters add(Filter filter);

		public Rates rates();

	}

	public interface Rates extends PublishRawValues, Build {
		public AggregateDescribe addSum();

		public AggregateDescribe addSum(String name);

		public AggregateDescribe addCount();

		public AggregateDescribe addCount(String name);

		public AggregateDescribe addAverage();

		public AggregateDescribe addAverage(String name);

		public AggregateDescribe addMinimum();

		public AggregateDescribe addMinimum(String name);

		public AggregateDescribe addMaximum();

		public AggregateDescribe addMaximum(String name);

		public AggregateDescribe add(Aggregation aggregation);

		public AggregateDescribe add(Aggregation aggregation, String name);
	}

	public interface Aggregate extends Rates {

		public RateDescribe add(TimeUnit timeUnit, int multiplier);

		public RateDescribe add(TimeUnit timeUnit, int multiplier, String name);

	}

	public interface AggregateDescribe extends Aggregate {

		public Aggregate description(String description);

	}

	public interface RateDescribe extends Rate {
		public Rate description(String description);
	}

	public interface Rate extends Rates {
		public Rates publishAggregate();

		public RateDescribe add(TimeUnit timeUnit, int multiplier);

		public RateDescribe add(TimeUnit timeUnit, int multiplier, String name);
	}

	public interface PublishRawValues {

		public Build publishRawValues();

	}

	public interface Build {
		public sim.monitor.Monitor build();
	}


	// ATTRIBUTES

	private Builder builder = this;
	private String name;
	private String description;
	private boolean publishRawValues = false;
	private List<String> tagValues = new ArrayList<String>();
	private List<Filter> filterInstances = new ArrayList<Filter>();
	private List<Aggregation> aggregationInstances = new ArrayList<Aggregation>();
	private Map<Aggregation, List<sim.monitor.Rate>> rateInstances = new HashMap<Aggregation, List<sim.monitor.Rate>>();

	private Aggregation lastAggregation;
	private sim.monitor.Rate lastRate;

	private MonitorDescribe monitorDescribe = new MonitorDescribe() {

		public sim.monitor.Monitor build() {
			return builder.build();
		}

		public Tags tags() {
			return tags;
		}

		public Monitor description(String description) {
			builder.description = description;
			return monitor;
		}
	};

	private Monitor monitor = new Monitor() {

		public sim.monitor.Monitor build() {
			return builder.build();
		}

		public Tags tags() {
			return tags;
		}
	};

	private Tags tags = new Tags() {

		public sim.monitor.Monitor build() {
			return builder.build();
		}

		public Filters filters() {
			return filters;
		}

		public Tags add(String tag) {
			builder.addTag(tag);
			return tags;
		}
	};

	private Filters filters = new Filters() {

		public sim.monitor.Monitor build() {
			return builder.build();
		}

		public Rates rates() {
			return rates;
		}

		public Filters addSampler(TimeUnit timeUnit, int multiplier) {
			return add(new TimeIntervalSampler(new TimePeriod(timeUnit,
					multiplier)));
		}

		public Filters addDelta() {
			return add(new DeltaTransformer());
		}

		public Filters add(Filter filter) {
			builder.addFilter(filter);
			return filters;
		}
	};

	private Rates rates = new Rates() {

		public sim.monitor.Monitor build() {
			return builder.build();
		}

		public Build publishRawValues() {
			builder.publishRawValues = true;
			return build;
		}

		public AggregateDescribe addSum(String name) {
			builder.addAggregation(new Sum(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addSum() {
			builder.addAggregation(new Sum(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addMinimum(String name) {
			builder.addAggregation(new Min(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addMinimum() {
			builder.addAggregation(new Min(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addMaximum(String name) {
			builder.addAggregation(new Max(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addMaximum() {
			builder.addAggregation(new Min(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addCount(String name) {
			builder.addAggregation(new Count(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addCount() {
			builder.addAggregation(new Count(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addAverage(String name) {
			builder.addAggregation(new Average(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addAverage() {
			builder.addAggregation(new Average(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe add(Aggregation aggregation,
				String name) {
			builder.addAggregation(aggregation, name);
			return aggregateDescribe;
		}

		public AggregateDescribe add(Aggregation aggregation) {
			builder.addAggregation(aggregation, null);
			return aggregateDescribe;
		}
	};

	private AggregateDescribe aggregateDescribe = new AggregateDescribe() {

		public sim.monitor.Monitor build() {
			return builder.build();
		}

		public Build publishRawValues() {
			builder.publishRawValues = true;
			return builder.build;
		}

		public AggregateDescribe addSum(String name) {
			builder.addAggregation(new Sum(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addSum() {
			builder.addAggregation(new Sum(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addMinimum(String name) {
			builder.addAggregation(new Min(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addMinimum() {
			builder.addAggregation(new Min(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addMaximum(String name) {
			builder.addAggregation(new Max(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addMaximum() {
			builder.addAggregation(new Min(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addCount(String name) {
			builder.addAggregation(new Count(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addCount() {
			builder.addAggregation(new Count(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addAverage(String name) {
			builder.addAggregation(new Average(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addAverage() {
			builder.addAggregation(new Average(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe add(Aggregation aggregation, String name) {
			builder.addAggregation(aggregation, name);
			return aggregateDescribe;
		}

		public AggregateDescribe add(Aggregation aggregation) {
			builder.addAggregation(aggregation, null);
			return aggregateDescribe;
		}

		public Aggregate description(String description) {
			aggregationInstances.get(aggregationInstances.size() - 1)
			.setDescription(description);
			return builder.aggregate;
		}

		public RateDescribe add(TimeUnit timeUnit, int multiplier, String name) {
			builder.addRate(timeUnit, multiplier, name);
			return rateDescribe;
		}

		public RateDescribe add(TimeUnit timeUnit, int multiplier) {
			builder.addRate(timeUnit, multiplier, null);
			return rateDescribe;
		}

	};

	private Aggregate aggregate = new Aggregate() {

		public sim.monitor.Monitor build() {
			return builder.build();
		}

		public Build publishRawValues() {
			builder.publishRawValues = true;
			return builder.build;
		}

		public AggregateDescribe addSum(String name) {
			builder.addAggregation(new Sum(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addSum() {
			builder.addAggregation(new Sum(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addMinimum(String name) {
			builder.addAggregation(new Min(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addMinimum() {
			builder.addAggregation(new Min(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addMaximum(String name) {
			builder.addAggregation(new Max(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addMaximum() {
			builder.addAggregation(new Min(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addCount(String name) {
			builder.addAggregation(new Count(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addCount() {
			builder.addAggregation(new Count(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addAverage(String name) {
			builder.addAggregation(new Average(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addAverage() {
			builder.addAggregation(new Average(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe add(Aggregation aggregation, String name) {
			builder.addAggregation(aggregation, name);
			return aggregateDescribe;
		}

		public AggregateDescribe add(Aggregation aggregation) {
			builder.addAggregation(aggregation, null);
			return aggregateDescribe;
		}

		public RateDescribe add(TimeUnit timeUnit, int multiplier, String name) {
			builder.addRate(timeUnit, multiplier, name);
			return rateDescribe;
		}

		public RateDescribe add(TimeUnit timeUnit, int multiplier) {
			builder.addRate(timeUnit, multiplier, null);
			return rateDescribe;
		}

	};

	private RateDescribe rateDescribe = new RateDescribe() {

		public AggregateDescribe addSum(String name) {
			builder.addAggregation(new Sum(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addSum() {
			builder.addAggregation(new Sum(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addMinimum(String name) {
			builder.addAggregation(new Min(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addMinimum() {
			builder.addAggregation(new Min(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addMaximum(String name) {
			builder.addAggregation(new Max(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addMaximum() {
			builder.addAggregation(new Min(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addCount(String name) {
			builder.addAggregation(new Count(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addCount() {
			builder.addAggregation(new Count(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addAverage(String name) {
			builder.addAggregation(new Average(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addAverage() {
			builder.addAggregation(new Average(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe add(Aggregation aggregation, String name) {
			builder.addAggregation(aggregation, name);
			return aggregateDescribe;
		}

		public AggregateDescribe add(Aggregation aggregation) {
			builder.addAggregation(aggregation, null);
			return aggregateDescribe;
		}

		public Build publishRawValues() {
			builder.publishRawValues = true;
			return builder.build;
		}

		public sim.monitor.Monitor build() {
			return builder.build();
		}

		public RateDescribe add(TimeUnit timeUnit, int multiplier, String name) {
			builder.addRate(timeUnit, multiplier, name);
			return rateDescribe;
		}

		public RateDescribe add(TimeUnit timeUnit, int multiplier) {
			builder.addRate(timeUnit, multiplier, null);
			return rateDescribe;
		}

		public Rates publishAggregate() {
			lastAggregation.setForcePublication(true);
			return rates;
		}

		public Rate description(String description) {
			lastRate.setDescription(description);
			return builder.rate;
		}
	};

	private Rate rate = new Rate() {

		public AggregateDescribe addSum(String name) {
			builder.addAggregation(new Sum(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addSum() {
			builder.addAggregation(new Sum(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addMinimum(String name) {
			builder.addAggregation(new Min(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addMinimum() {
			builder.addAggregation(new Min(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addMaximum(String name) {
			builder.addAggregation(new Max(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addMaximum() {
			builder.addAggregation(new Min(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addCount(String name) {
			builder.addAggregation(new Count(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addCount() {
			builder.addAggregation(new Count(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe addAverage(String name) {
			builder.addAggregation(new Average(), name);
			return aggregateDescribe;
		}

		public AggregateDescribe addAverage() {
			builder.addAggregation(new Average(), null);
			return aggregateDescribe;
		}

		public AggregateDescribe add(Aggregation aggregation, String name) {
			builder.addAggregation(aggregation, name);
			return aggregateDescribe;
		}

		public AggregateDescribe add(Aggregation aggregation) {
			builder.addAggregation(aggregation, null);
			return aggregateDescribe;
		}

		public Build publishRawValues() {
			builder.publishRawValues = true;
			return builder.build;
		}

		public sim.monitor.Monitor build() {
			return builder.build();
		}

		public RateDescribe add(TimeUnit timeUnit, int multiplier, String name) {
			builder.addRate(timeUnit, multiplier, name);
			return rateDescribe;
		}

		public RateDescribe add(TimeUnit timeUnit, int multiplier) {
			builder.addRate(timeUnit, multiplier, null);
			return rateDescribe;
		}

		public Rates publishAggregate() {
			lastAggregation.setForcePublication(true);
			return rates;
		}
	};

	private Build build = new Build() {

		public sim.monitor.Monitor build() {
			return builder.build();
		}

	};

	// BODY

	private Builder() {
	}

	public static MonitorDescribe Monitor(String name) {
		Builder builder = new Builder();
		builder.name = name;
		return builder.monitorDescribe;
	}

	private sim.monitor.Monitor build() {
		sim.monitor.Monitor monitor = new sim.monitor.Monitor(name,
				description, publishRawValues,
				tagValues, filterInstances, aggregationInstances, rateInstances);
		HitProcessor.instance().acceptMonitor(monitor);
		return monitor;
	}

	private void addTag(String tag) {
		if (tagValues.contains(tag)) {
			return;
		}
		tagValues.add(tag);
	}

	private void addFilter(Filter filter) {
		if (filterInstances.contains(filter)) {
			return;
		}
		filterInstances.add(filter);
	}

	private void addAggregation(Aggregation aggregation, String name) {
		if (aggregationInstances.contains(aggregation)) {
			lastAggregation = aggregationInstances.get(aggregationInstances.lastIndexOf(aggregation));
			lastAggregation.setName(name);
			return;
		}
		aggregation.setName(name);
		aggregationInstances.add(aggregation);
		lastAggregation = aggregation;
	}

	private void addRate(TimeUnit timeUnit, int multiplier, String name) {
		if (!rateInstances.containsKey(lastAggregation)) {
			rateInstances.put(lastAggregation,
					new ArrayList<sim.monitor.Rate>());
		}
		List<sim.monitor.Rate> rates = rateInstances
				.get(lastAggregation);
		sim.monitor.Rate rate = new sim.monitor.Rate(lastAggregation,
				new TimePeriod(timeUnit, multiplier), name,
				null);
		if (rates.contains(rate)) {
			rate = rates.get(rates.lastIndexOf(rate));
			rate.setName(name);
			lastRate = rate;
			return;
		}
		rates.add(rate);
		lastRate = rate;
	}
}
