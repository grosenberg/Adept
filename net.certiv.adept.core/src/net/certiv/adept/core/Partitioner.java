package net.certiv.adept.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.stat.StatUtils;

import com.google.common.primitives.Doubles;

import net.certiv.adept.util.Strings;

public class Partitioner {

	static class DoublePoint implements Clusterable {

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

	private double[] values;
	private ArrayList<DoublePoint> points;
	private List<Cluster<DoublePoint>> results;

	public Partitioner(Set<Double> values) {
		this.values = Doubles.toArray(values);
		this.points = new ArrayList<>();
		this.results = null;
		for (Double value : this.values) {
			points.add(new DoublePoint(value));
		}
		getPartitions();
	}

	/** Returns the values of this partitioner. */
	public double[] getValues() {
		return values;
	}

	/** Returns the values clustered into the partition of the given index. */
	public double[] getValues(int idx) {
		return valuesOf(getPartitions().get(idx));
	}

	private double[] valuesOf(Cluster<DoublePoint> cluster) {
		List<DoublePoint> cpts = cluster.getPoints();
		double[] v = new double[cpts.size()];
		for (int idx = 0; idx < cpts.size(); idx++) {
			v[idx] = cpts.get(idx).getValue();
		}
		return v;
	}

	public List<Cluster<DoublePoint>> getPartitions() {
		if (results == null) {
			DBSCANClusterer<DoublePoint> op = new DBSCANClusterer<>(3, 1);
			results = op.cluster(points);
		}
		return results;
	}

	public int size() {
		return getPartitions().size();
	}

	public double getVariance() {
		return StatUtils.variance(values);
	}

	public double getMean() {
		return StatUtils.mean(values);
	}

	/** Return the index of the minimum valued cluster. */
	public int getMinPartition() {
		int num = size();
		int mc = 0;
		double min = 0;
		for (int idx = 0; idx < num; idx++) {
			double[] v = valuesOf(getPartitions().get(idx));
			double m = StatUtils.min(v);
			if (m == 0 || m < min) {
				min = m;
				mc = idx;
			}
		}
		return mc;
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
		double[] v = valuesOf(getPartitions().get(idx));
		return String.format("Cluster %s (%4s): min=%8.5f, max=%8.5f, mean=%8.5f, variance=%8.5f", //
				String.valueOf(idx), //
				String.valueOf(v.length), //
				StatUtils.min(v), //
				StatUtils.max(v), //
				StatUtils.mean(v), //
				StatUtils.variance(v) //
		);
	}
}
