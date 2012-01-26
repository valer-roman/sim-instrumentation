/**
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package sim.monitor;

import java.util.ArrayList;
import java.util.List;

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

		public Rates addSum();

		public Rates addSum(String name);

		public Rates addSum(String name, String description);

		public Rates addSum(TimeUnit timeUnit, int multiplier);

		public Rates addSum(TimeUnit timeUnit, int multiplier, String name);

		public Rates addSum(TimeUnit timeUnit, int multiplier, String name,
				String description);


		public Rates addCount();

		public Rates addCount(String name);

		public Rates addCount(String name, String description);

		public Rates addCount(TimeUnit timeUnit, int multiplier);

		public Rates addCount(TimeUnit timeUnit, int multiplier, String name);

		public Rates addCount(TimeUnit timeUnit, int multiplier, String name,
				String description);


		public Rates addAverage();

		public Rates addAverage(String name);

		public Rates addAverage(String name, String description);

		public Rates addAverage(TimeUnit timeUnit, int multiplier);

		public Rates addAverage(TimeUnit timeUnit, int multiplier, String name);

		public Rates addAverage(TimeUnit timeUnit, int multiplier, String name,
				String description);


		public Rates addMin();

		public Rates addMin(String name);

		public Rates addMin(String name, String description);

		public Rates addMin(TimeUnit timeUnit, int multiplier);

		public Rates addMin(TimeUnit timeUnit, int multiplier, String name);

		public Rates addMin(TimeUnit timeUnit, int multiplier, String name,
				String description);


		public Rates addMax();

		public Rates addMax(String name);

		public Rates addMax(String name, String description);

		public Rates addMax(TimeUnit timeUnit, int multiplier);

		public Rates addMax(TimeUnit timeUnit, int multiplier, String name);

		public Rates addMax(TimeUnit timeUnit, int multiplier, String name,
				String description);

	}

	public interface PublishRawValues {

		public Build publishRawValues();

	}

	public interface Build {
		public sim.monitor.Monitor build();

		public sim.monitor.Timer buildTimer();
	}


	// ATTRIBUTES

	private Builder builder = this;
	private String name;
	private String description;
	private boolean publishRawValues = false;
	private List<String> tagValues = new ArrayList<String>();
	private List<Filter> filterInstances = new ArrayList<Filter>();
	private List<sim.monitor.Rate> rateInstances = new ArrayList<sim.monitor.Rate>();

	private Monitor monitor = new Monitor() {

		public sim.monitor.Monitor build() {
			return builder.build();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Build#buildTimer()
		 */
		public Timer buildTimer() {
			return builder.buildTimer();
		}

		public Tags tags() {
			return tags;
		}
	};

	private Tags tags = new Tags() {

		public sim.monitor.Monitor build() {
			return builder.build();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Build#buildTimer()
		 */
		public Timer buildTimer() {
			return builder.buildTimer();
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

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Build#buildTimer()
		 */
		public Timer buildTimer() {
			return builder.buildTimer();
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see sim.monitor.Builder.Build#buildTimer()
		 */
		public Timer buildTimer() {
			return builder.buildTimer();
		}

		public Build publishRawValues() {
			builder.publishRawValues = true;
			return build;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addSum()
		 */
		public Rates addSum() {
			builder.addSumRate(null, 0, null, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addSum(java.lang.String)
		 */
		public Rates addSum(String name) {
			builder.addSumRate(null, 0, name, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addSum(java.lang.String,
		 * java.lang.String)
		 */
		public Rates addSum(String name, String description) {
			builder.addSumRate(null, 0, name, description);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addSum(sim.monitor.timing.TimeUnit,
		 * int)
		 */
		public Rates addSum(TimeUnit timeUnit, int multiplier) {
			builder.addSumRate(timeUnit, multiplier, null, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addSum(sim.monitor.timing.TimeUnit,
		 * int, java.lang.String)
		 */
		public Rates addSum(TimeUnit timeUnit, int multiplier, String name) {
			builder.addSumRate(timeUnit, multiplier, name, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addSum(sim.monitor.timing.TimeUnit,
		 * int, java.lang.String, java.lang.String)
		 */
		public Rates addSum(TimeUnit timeUnit, int multiplier, String name,
				String description) {
			builder.addSumRate(timeUnit, multiplier, name, description);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addCount()
		 */
		public Rates addCount() {
			builder.addCountRate(null, 0, null, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addCount(java.lang.String)
		 */
		public Rates addCount(String name) {
			builder.addCountRate(null, 0, name, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addCount(java.lang.String,
		 * java.lang.String)
		 */
		public Rates addCount(String name, String description) {
			builder.addCountRate(null, 0, name, description);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addCount(sim.monitor.timing.TimeUnit,
		 * int)
		 */
		public Rates addCount(TimeUnit timeUnit, int multiplier) {
			builder.addCountRate(timeUnit, multiplier, null, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addCount(sim.monitor.timing.TimeUnit,
		 * int, java.lang.String)
		 */
		public Rates addCount(TimeUnit timeUnit, int multiplier, String name) {
			builder.addCountRate(timeUnit, multiplier, name, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addCount(sim.monitor.timing.TimeUnit,
		 * int, java.lang.String, java.lang.String)
		 */
		public Rates addCount(TimeUnit timeUnit, int multiplier, String name,
				String description) {
			builder.addCountRate(timeUnit, multiplier, name, description);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addAverage()
		 */
		public Rates addAverage() {
			builder.addAverageRate(null, 0, null, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addAverage(java.lang.String)
		 */
		public Rates addAverage(String name) {
			builder.addAverageRate(null, 0, name, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addAverage(java.lang.String,
		 * java.lang.String)
		 */
		public Rates addAverage(String name, String description) {
			builder.addAverageRate(null, 0, name, description);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * sim.monitor.Builder.Rates#addAverage(sim.monitor.timing.TimeUnit,
		 * int)
		 */
		public Rates addAverage(TimeUnit timeUnit, int multiplier) {
			builder.addAverageRate(timeUnit, multiplier, null, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * sim.monitor.Builder.Rates#addAverage(sim.monitor.timing.TimeUnit,
		 * int, java.lang.String)
		 */
		public Rates addAverage(TimeUnit timeUnit, int multiplier, String name) {
			builder.addAverageRate(timeUnit, multiplier, name, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * sim.monitor.Builder.Rates#addAverage(sim.monitor.timing.TimeUnit,
		 * int, java.lang.String, java.lang.String)
		 */
		public Rates addAverage(TimeUnit timeUnit, int multiplier, String name,
				String description) {
			builder.addAverageRate(timeUnit, multiplier, name, description);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addMin()
		 */
		public Rates addMin() {
			builder.addMinRate(null, 0, null, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addMin(java.lang.String)
		 */
		public Rates addMin(String name) {
			builder.addMinRate(null, 0, name, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addMin(java.lang.String,
		 * java.lang.String)
		 */
		public Rates addMin(String name, String description) {
			builder.addMinRate(null, 0, name, description);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addMin(sim.monitor.timing.TimeUnit,
		 * int)
		 */
		public Rates addMin(TimeUnit timeUnit, int multiplier) {
			builder.addMinRate(timeUnit, multiplier, null, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addMin(sim.monitor.timing.TimeUnit,
		 * int, java.lang.String)
		 */
		public Rates addMin(TimeUnit timeUnit, int multiplier, String name) {
			builder.addMinRate(timeUnit, multiplier, name, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addMin(sim.monitor.timing.TimeUnit,
		 * int, java.lang.String, java.lang.String)
		 */
		public Rates addMin(TimeUnit timeUnit, int multiplier, String name,
				String description) {
			builder.addMinRate(timeUnit, multiplier, name, description);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addMax()
		 */
		public Rates addMax() {
			builder.addMaxRate(null, 0, null, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addMax(java.lang.String)
		 */
		public Rates addMax(String name) {
			builder.addMaxRate(null, 0, name, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addMax(java.lang.String,
		 * java.lang.String)
		 */
		public Rates addMax(String name, String description) {
			builder.addMaxRate(null, 0, name, description);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addMax(sim.monitor.timing.TimeUnit,
		 * int)
		 */
		public Rates addMax(TimeUnit timeUnit, int multiplier) {
			builder.addMaxRate(timeUnit, multiplier, null, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addMax(sim.monitor.timing.TimeUnit,
		 * int, java.lang.String)
		 */
		public Rates addMax(TimeUnit timeUnit, int multiplier, String name) {
			builder.addMaxRate(timeUnit, multiplier, name, null);
			return this;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see sim.monitor.Builder.Rates#addMax(sim.monitor.timing.TimeUnit,
		 * int, java.lang.String, java.lang.String)
		 */
		public Rates addMax(TimeUnit timeUnit, int multiplier, String name,
				String description) {
			builder.addMaxRate(timeUnit, multiplier, name, description);
			return this;
		}

	};

	private Build build = new Build() {

		public sim.monitor.Monitor build() {
			return builder.build();
		}

		public sim.monitor.Timer buildTimer() {
			return new Timer(builder.build());
		}

	};

	// BODY

	private Builder() {
	}

	public static Monitor Monitor(String name) {
		Builder builder = new Builder();
		builder.name = name;
		return builder.monitor;
	}

	public static Monitor Monitor(String name, String description) {
		Builder builder = new Builder();
		builder.name = name;
		builder.description = description;
		return builder.monitor;
	}

	private sim.monitor.Monitor build() {
		sim.monitor.Monitor monitor = new sim.monitor.Monitor(name,
				description, publishRawValues, tagValues, filterInstances,
				rateInstances);
		HitProcessor.instance().acceptMonitor(monitor);
		return monitor;
	}

	private sim.monitor.Timer buildTimer() {
		return new Timer(builder.build());
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

	private TimePeriod makeTimePeriod(TimeUnit timeUnit, int multiplier) {
		TimePeriod timePeriod = null;
		if (timeUnit != null && multiplier > 0) {
			timePeriod = new TimePeriod(timeUnit, multiplier);
		}
		return timePeriod;
	}

	private void addSumRate(TimeUnit timeUnit, int multiplier,
			String name, String description) {
		addRate(new sim.monitor.Sum(makeTimePeriod(timeUnit, multiplier), name,
				description));
	}

	private void addCountRate(TimeUnit timeUnit, int multiplier, String name,
			String description) {
		addRate(new sim.monitor.Count(makeTimePeriod(timeUnit, multiplier),
				name, description));
	}

	private void addAverageRate(TimeUnit timeUnit, int multiplier, String name,
			String description) {
		addRate(new sim.monitor.Average(makeTimePeriod(timeUnit, multiplier),
				name, description));
	}

	private void addMinRate(TimeUnit timeUnit, int multiplier, String name,
			String description) {
		addRate(new sim.monitor.Min(makeTimePeriod(timeUnit, multiplier), name,
				description));
	}

	private void addMaxRate(TimeUnit timeUnit, int multiplier, String name,
			String description) {
		addRate(new sim.monitor.Max(makeTimePeriod(timeUnit, multiplier), name,
				description));
	}

	private void addRate(Rate rate) {
		if (rateInstances.contains(rate)) {
			return;
		}
		rateInstances.add(rate);
	}

}
