package net.certiv.adept.nn;

/**
*
* @author Deus Jeraldy
* @Email: deusjeraldy@gmail.com
* BSD License
*/

// np.java -> https://gist.github.com/Jeraldy/7d4262db0536d27906b1e397662512bc

import java.util.Arrays;

public class NN {

	public static void main(String[] args) {

		double[][] X = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
		double[][] Y = { { 0 }, { 1 }, { 1 }, { 0 } };

		int m = 4;
		int nodes = 400;

		X = Np.T(X);
		Y = Np.T(Y);

		double[][] W1 = Np.random(nodes, 2);
		double[][] b1 = new double[nodes][m];

		double[][] W2 = Np.random(1, nodes);
		double[][] b2 = new double[1][m];

		for (int i = 0; i < 4000; i++) {
			// Foward Prop
			// LAYER 1
			double[][] Z1 = Np.add(Np.dot(W1, X), b1);
			double[][] A1 = Np.sigmoid(Z1);

			// LAYER 2
			double[][] Z2 = Np.add(Np.dot(W2, A1), b2);
			double[][] A2 = Np.sigmoid(Z2);

			double cost = Np.cross_entropy(m, Y, A2);
			// costs.getData().add(new XYChart.Data(i, cost));
			// Back Prop
			// LAYER 2
			double[][] dZ2 = Np.subtract(A2, Y);
			double[][] dW2 = Np.divide(Np.dot(dZ2, Np.T(A1)), m);
			double[][] db2 = Np.divide(dZ2, m);

			// LAYER 1
			double[][] dZ1 = Np.multiply(Np.dot(Np.T(W2), dZ2), Np.subtract(1.0, Np.power(A1, 2)));
			double[][] dW1 = Np.divide(Np.dot(dZ1, Np.T(X)), m);
			double[][] db1 = Np.divide(dZ1, m);

			// G.D
			W1 = Np.subtract(W1, Np.multiply(0.01, dW1));
			b1 = Np.subtract(b1, Np.multiply(0.01, db1));

			W2 = Np.subtract(W2, Np.multiply(0.01, dW2));
			b2 = Np.subtract(b2, Np.multiply(0.01, db2));

			if (i % 400 == 0) {
				Np.print("==============");
				Np.print("Cost = " + cost);
				Np.print("Predictions = " + Arrays.deepToString(A2));
			}
		}
	}
}
