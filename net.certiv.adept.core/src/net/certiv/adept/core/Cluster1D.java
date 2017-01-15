package net.certiv.adept.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.stat.StatUtils;

import com.google.common.primitives.Doubles;

public class Cluster1D {

	static class DoublePoint implements Clusterable {

		private double[] point;

		public DoublePoint(double value) {
			this.point = new double[] { value, 1 };
		}

		@Override
		public double[] getPoint() {
			return point;
		}
	}

	private double[] values;
	private ArrayList<DoublePoint> points;
	private List<Cluster<DoublePoint>> results;

	public Cluster1D(Set<Double> values) {
		this.values = Doubles.toArray(values);
		this.points = new ArrayList<>();
		this.results = null;
		for (Double value : this.values) {
			points.add(new DoublePoint(value));
		}
	}

	public double[] getValues() {
		return values;
	}

	public List<Cluster<DoublePoint>> getClusters() {
		if (results == null) {
			DBSCANClusterer<DoublePoint> op = new DBSCANClusterer<>(2, 3);
			results = op.cluster(points);
		}
		return results;
	}

	public int numClusters() {
		return getClusters().size();
	}

	public double getVariance() {
		return StatUtils.variance(values);
	}
}
