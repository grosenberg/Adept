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

		public double getValue() {
			return point[0];
		}

		@Override
		public String toString() {
			return String.format("DoublePoint [point=%s]", String.valueOf(point[0]));
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
		getClusters();
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

	public double[] getClusterValues(int idx) {
		return valuesOf(getClusters().get(idx));
	}

	public double getVariance() {
		return StatUtils.variance(values);
	}

	public double getMean() {
		return StatUtils.mean(values);
	}

	public String clusterDescriptions() {
		int num = numClusters();
		List<String> descs = new ArrayList<>();
		for (int idx = 0; idx < num; idx++) {
			descs.add(clusterDescription(idx));
		}
		return Strings.EOL + "-- " + Strings.join(descs, Strings.EOL + "-- ") + Strings.EOL;
	}

	public String clusterDescription(int idx) {
		double[] v = valuesOf(getClusters().get(idx));
		return String.format("Cluster %s (%4s): min=%8.5f, max=%8.5f, mean=%8.5f, variance=%8.5f", //
				String.valueOf(idx), //
				String.valueOf(v.length), //
				StatUtils.min(v), //
				StatUtils.max(v), //
				StatUtils.mean(v), //
				StatUtils.variance(v) //
		);
	}

	private double[] valuesOf(Cluster<DoublePoint> cluster) {
		List<DoublePoint> cpts = cluster.getPoints();
		double[] v = new double[cpts.size()];
		for (int idx = 0; idx < cpts.size(); idx++) {
			v[idx] = cpts.get(idx).getValue();
		}
		return v;
	}
}
