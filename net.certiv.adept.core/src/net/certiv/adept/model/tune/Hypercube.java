package net.certiv.adept.model.tune;

public class Hypercube {

	private double[][] values;

	/** Creates a latin hypercube matrix of values. */
	public Hypercube(int dims, int points) {
		this.values = genValues(dims, points);
	}

	/** Returns the array of dimension values selected by the given <code>sample</code> value. */
	public double[] getSampleSet(int sample) {
		return values[sample];
	}

	/**
	 * Returns a matrix of lhs vals for a spatial dimension <code>dims</code> with a sample rate per
	 * dimension of <code>points</code>. Implementaton:
	 * <ul>
	 * <li>pick a random permutation of 1 to <code>points</code>
	 * <li>force the corresponding <code>dims</code>-th row's samples to lie in the interval:
	 * (PERM[x]-1, PERM[x]) / <code>points</code>
	 * </ul>
	 * <p>
	 * Organized so that each row contains the values of one set of dimensions.
	 */
	public double[][] genValues(int dims, int points) {
		double[][] matrix = new double[points][dims];
		for (int idx = 0; idx < dims; idx++) {
			double[] perms = genPermutations(points);
			for (int jdx = 0; jdx < points; jdx++) {
				matrix[jdx][idx] = perms[jdx] / points;
			}
		}
		return matrix;
	}

	private double[] genPermutations(int n) {
		double[] p = new double[n];
		for (int idx = 0; idx < n; idx++) {
			p[idx] = idx + 1;
		}
		for (int i = 0; i < n; i++) {
			int j = random(i, n - 1);
			double k = p[i];
			p[i] = p[j];
			p[j] = k;
		}
		return p;
	}

	/** Generates a random integer value between the given limit values. */
	private int random(int a, int b) {
		double r = Math.random();
		// scale r to lie in the range a-0.5 and b+0.5.
		r = (1.0 - r) * ((Math.min(a, b)) - 0.5) + r * ((Math.max(a, b)) + 0.5);
		return (int) Math.round(r);
	}
}
