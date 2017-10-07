package net.certiv.adept.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.stat.StatUtils;

import com.google.common.primitives.Doubles;

import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

public class Partitioner {

	private final double[] values;
	private final ArrayList<DoublePoint> points = new ArrayList<>();

	private List<Cluster<DoublePoint>> partitions;

	public Partitioner(Set<Double> values) {
		this.values = Doubles.toArray(values);
		for (Double value : this.values) {
			points.add(new DoublePoint(value));
		}
		DBSCANClusterer<DoublePoint> op = new DBSCANClusterer<>(0.3, 3);
		this.partitions = op.cluster(points);
	}

	/** Returns the values provided to this partitioner. */
	public double[] getValues() {
		return values;
	}

	/** Returns the subset of values as clustered in the partition of the given index. */
	public double[] getValues(int idx) {
		if (partitions.isEmpty()) {
			return values;
		} else if (idx >= partitions.size()) {
			Log.error(this, String.format("No partition for %s (%s)", idx, partitions.size()));
			return values;
		}
		return valuesOf(partitions.get(idx));
	}

	private double[] valuesOf(Cluster<DoublePoint> cluster) {
		List<DoublePoint> cpts = cluster.getPoints();
		double[] v = new double[cpts.size()];
		for (int idx = 0; idx < cpts.size(); idx++) {
			v[idx] = cpts.get(idx).getValue();
		}
		return v;
	}

	/** Return the index of the max valued cluster. */
	public int getMaxValuedPartitionIndex() {
		if (partitions.isEmpty()) return -1;
		if (partitions.size() == 1) return 0;

		int index = 0;
		double max = -1;
		for (int idx = 0; idx < size(); idx++) {
			DoublePoint point = partitions.get(idx).getPoints().get(0);
			if (max == -1 || max < point.getValue()) {
				max = point.getValue();
				index = idx;
			}
		}
		return index;
	}

	public int size() {
		return partitions.size();
	}

	public double getVariance() {
		return StatUtils.variance(values);
	}

	public double getMean() {
		return StatUtils.mean(values);
	}

	public String partitionDescriptions() {
		int num = size();
		List<String> descs = new ArrayList<>();
		for (int idx = 0; idx < num; idx++) {
			descs.add(partitionDescription(idx));
		}
		return Strings.EOL + "-- " + Strings.join(descs, Strings.EOL + "-- ") + Strings.EOL;
	}

	public String partitionDescription(int idx) {
		double[] v = valuesOf(partitions.get(idx));
		return String.format("Cluster %s (%4s): min=%8.5f, max=%8.5f, mean=%8.5f, variance=%8.5f", //
				String.valueOf(idx), //
				String.valueOf(v.length), //
				StatUtils.min(v), //
				StatUtils.max(v), //
				StatUtils.mean(v), //
				StatUtils.variance(v) //
		);
	}

	private class DoublePoint implements Clusterable {

		private double[] point;

		public DoublePoint(double value) {
			this.point = new double[] { value, value };
		}

		@Override
		public double[] getPoint() {
			return point;
		}

		public double getValue() {
			return point[0];
		}

		@Override
		public String toString() {
			return String.format("DoublePoint %s", String.valueOf(getValue()));
		}
	}
}
